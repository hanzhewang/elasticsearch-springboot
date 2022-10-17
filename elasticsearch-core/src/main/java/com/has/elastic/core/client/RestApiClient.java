package com.has.elastic.core.client;

import com.has.elastic.core.config.ElasticConfig;
import com.has.elastic.core.config.ElasticConstant;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.SecurityClient;

/**
 * <p>Elasticsearch Rest客户端</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/11
 * @since jdk1.8 and elasticsearch6、7
 */
public class RestApiClient {

    /**
     * Elasticsearch客户端工厂 （Rest）
     */
    public static final ApiClientFactory<ElasticConfig, RestHighLevelClient> FACTORY = new ApiClientFactory() {
        @Override
        public RestHighLevelClient create(ElasticConfig elasticConfig) {
            return RestApiClient.getInstance(elasticConfig);
        }
    };

    /**
     * Elasticsearch Rest 客户端
     */
    private static RestHighLevelClient client;

    /**
     * 单例模式
     *
     * @param elasticConfig
     * @return
     */
    public static RestHighLevelClient getInstance(ElasticConfig elasticConfig) {
        if (client != null) {
            return client;
        }
        synchronized (SecurityClient.class) {
            // 登录授权
            final CredentialsProvider credentialsProvider = authorize(elasticConfig);
            HttpHost[] httpHosts = buildHttpHost(elasticConfig);
            // 创建客户端
            RestClientBuilder builder = RestClient.builder(httpHosts)
                    .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        @Override
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                        }
                    });
            client = new RestHighLevelClient(builder);
            return client;
        }
    }

    /**
     * 登录权限设置
     *
     * @param config
     * @return
     */
    private static CredentialsProvider authorize(ElasticConfig config) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(config.getSecurityUser(), config.getSecurityPassword());
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);
        return credentialsProvider;
    }

    /**
     * Elasticsearch 集群地址
     *
     * @param config
     * @return
     */
    private static HttpHost[] buildHttpHost(ElasticConfig config) {
        String[] gatewayIpPorts = config.getGatewayIpPorts().split(ElasticConstant.SYMBOL_COMMA);
        HttpHost[] httpHosts = new HttpHost[gatewayIpPorts.length];
        String[] ipPort = null;
        HttpHost httpHost = null;
        String gatewayIpPort = null;
        for (int i = 0; i < gatewayIpPorts.length; i++) {
            gatewayIpPort = gatewayIpPorts[i];
            ipPort = gatewayIpPort.split(ElasticConstant.SYMBOL_COLON);
            httpHost = new HttpHost(ipPort[0], Integer.valueOf(ipPort[1]), ElasticConstant.CLUSTER_SCHEME_HTTP);
            httpHosts[i] = httpHost;
        }
        return httpHosts;
    }

}
