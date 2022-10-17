package com.has.elastic.core.config;

import lombok.Data;

/**
 * <p>Elasticsearch客户端配置</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/12
 * @since jdk1.8 and elasticsearch6、7
 */
@Data
public class ElasticConfig {

    /**
     * 客户端名称
     */
    protected String clusterName;

    /**
     * 网关及端口
     */
    protected String gatewayIpPorts;

    /**
     * 登录账号
     */
    protected String securityUser;
    /**
     * 登录密码
     */
    protected String securityPassword;

    /**
     * 自动发现新的Elasticsearch集群节点
     * 内网可以配置 true
     * 外网不可以配置 true
     * 默认为 false
     */
    protected Boolean clusterTransportSniff = false;

    /**
     * Elasticsearch netty 配置
     * 解决初始化错误： availableProcessors is already set to [4], rejecting [4]
     */
    protected String nettyRuntimeAvailableProcessors = "false";

}
