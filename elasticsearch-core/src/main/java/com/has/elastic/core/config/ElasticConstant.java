package com.has.elastic.core.config;

/**
 * <p>Elasticsearch常量类</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/12
 * @since jdk1.8 and elasticsearch6、7
 */
public class ElasticConstant {

    /**
     * Elasticsearch客户端协议
     */
    public static final String CLUSTER_SCHEME_HTTP = "http";

    /**
     * Elasticsearch 集群名称配置KEY
     */
    public static final String CLUSTER_NAME = "cluster.name";

    /**
     * Elasticsearch 自动发现节点配置KEY
     */
    public static final String CLUSTER_TRANSPORT_SNIFF = "client.transport.sniff";


    /**
     * Elasticsearch Transport客户端的权限KEY
     */
    public static final String CLUSTER_TRANSPORT_SECURITY_KEY = "request.headers.Authorization";

    /**
     * Elasticsearch Transport客户端的权限前缀
     */
    public static final String CLUSTER_TRANSPORT_SECURITY_PREFIX = "Basic";

    /**
     * Elasticsearch Transport客户端的netty配置
     */
    public static final String NETTY_RUNTIME_AVAILABLE = "es.set.netty.runtime.available.processors";

    /**
     * 逗号分隔符
     */
    public static final String SYMBOL_COMMA = ",";
    /**
     * 点分隔符
     */
    public static final String SYMBOL_POINT = ".";
    /**
     * 冒号分隔符
     */
    public static final String SYMBOL_COLON = ":";
    /**
     * 下划线分隔符
     */
    public static final String SYMBOL_UNDERLINE = "_";

}
