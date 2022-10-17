package com.has.elastic.core.client;

import com.has.elastic.core.config.ElasticConfig;
import com.has.elastic.core.config.ElasticConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.client.SecurityClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * <p>Elasticsearch Transport客户端</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/11
 */
@Slf4j
public class TransportApiClient {

    /**
     * Elasticsearch客户端工厂 （Transport）
     */
    public static final ApiClientFactory<ElasticConfig, TransportClient> FACTORY = new ApiClientFactory<ElasticConfig, TransportClient>() {
        @Override
        public TransportClient create(ElasticConfig elasticConfig) {
            return TransportApiClient.getInstance(elasticConfig);
        }
    };

    /**
     * Elasticsearch 客户端
     */
    private static TransportClient client;

    /**
     * 单例模式
     *
     * @param config
     * @return
     */
    public static TransportClient getInstance(ElasticConfig config) {
        if (client != null) {
            return client;
        }
        // 验证参数必填
        TransportApiClient.validatorConfig(config);
        synchronized (SecurityClient.class) {
            // 解决初始化错误： availableProcessors is already set to [4], rejecting [4]
            System.setProperty(ElasticConstant.NETTY_RUNTIME_AVAILABLE, config.getNettyRuntimeAvailableProcessors());
            // 构建配置信息
            Settings settings = TransportApiClient.buildSettings(config);
            // 构建客户端工具
            client = TransportApiClient.buildClient(settings, config);
            return client;
        }
    }


    /**
     * 客户端验证
     *
     * @param config
     */
    public static void validatorConfig(ElasticConfig config) {
        log.debug("elasticConfig={}", config);
        if (StringUtils.isBlank(config.getClusterName()) || StringUtils.isBlank(config.getGatewayIpPorts())) {
            throw new ElasticsearchException("elasticsearch config error: cluster is null or ip port is null");
        }
    }

    /**
     * 设置客户端配置
     *
     * @return
     */
    public static Settings buildSettings(ElasticConfig config) {
        Settings.Builder builder = Settings.builder();
        builder.put(ElasticConstant.CLUSTER_NAME, config.getClusterName());
        builder.put(ElasticConstant.CLUSTER_TRANSPORT_SNIFF, config.getClusterTransportSniff());
        if (StringUtils.isNoneBlank(config.getSecurityUser())
                && StringUtils.isNoneBlank(config.getSecurityPassword())) {
            builder.put(ElasticConstant.CLUSTER_TRANSPORT_SECURITY_KEY, basicAuthHeaderValue(config));
        }
        return builder.build();
    }

    /**
     * 创建客户端
     *
     * @param settings
     * @param config
     * @return
     */
    public static TransportClient buildClient(Settings settings, ElasticConfig config) {
        TransportClient client = new PreBuiltTransportClient(settings);
        String[] gatewayIpPorts = config.getGatewayIpPorts().split(ElasticConstant.SYMBOL_COMMA);
        InetAddress address = null;
        String[] ipPort = null;
        for (String gatewayIpPort : gatewayIpPorts) {
            ipPort = gatewayIpPort.split(ElasticConstant.SYMBOL_COLON);
            try {
                address = InetAddress.getByName(ipPort[0]);
            } catch (Exception ex) {
                log.error("elastic transport client address = {}", ipPort[0]);
                throw new ElasticsearchException("TransportClient address config error");
            }
            client.addTransportAddresses(new TransportAddress(address, Integer.parseInt(ipPort[1])));
        }
        return client;
    }

    /**
     * 基础的base64生成
     *
     * @param config 用户名
     * @return
     */
    public static String basicAuthHeaderValue(ElasticConfig config) {
        int allocate = config.getSecurityUser().length() + config.getSecurityPassword().length() + 1;
        CharBuffer chars = CharBuffer.allocate(allocate);
        byte[] charBytes = null;
        try {
            chars.put(config.getSecurityUser())
                    .put(ElasticConstant.SYMBOL_COLON)
                    .put(config.getSecurityPassword().toCharArray());
            charBytes = toUtf8Bytes(chars.array());
            String basicToken = Base64.getEncoder().encodeToString(charBytes);
            return StringUtils.join(ElasticConstant.CLUSTER_TRANSPORT_SECURITY_PREFIX, basicToken);
        } finally {
            Arrays.fill(chars.array(), (char) 0);
            if (charBytes != null) {
                Arrays.fill(charBytes, (byte) 0);
            }
        }
    }

    /**
     * to utf8 bytes
     *
     * @param chars
     * @return
     */
    public static byte[] toUtf8Bytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0);
        return bytes;
    }
}
