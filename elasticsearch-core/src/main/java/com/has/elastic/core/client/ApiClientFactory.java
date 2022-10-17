package com.has.elastic.core.client;

import com.has.elastic.core.config.ElasticConfig;

/**
 * <p>Elasticsearch客户端工厂</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2019.4.28
 * @since jdk1.8 and elasticsearch6、7
 */
public interface ApiClientFactory<C extends ElasticConfig, T> {

    /**
     * 创建客户端对象
     *
     * @param elasticConfig
     * @return
     */
    T create(C elasticConfig);

}