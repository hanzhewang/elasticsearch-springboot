package com.has.elastic.springboot.config;

import com.has.elastic.core.client.RestApiClient;
import com.has.elastic.core.client.TransportApiClient;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * <p>Elasticsearch客户端自动装配</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/9/30
 */
@Slf4j
@Configuration
public class ElasticConfigurer {

    /**
     * Elasticsearch 属性配置
     */
    @Resource
    private ElasticProperties elasticProperties;

    /**
     * Elasticsearch Rest 客户端
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    RestHighLevelClient getRestApiClient() {
        return RestApiClient.FACTORY.create(elasticProperties);
    }

    /**
     * Elasticsearch Transport 客户端
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    TransportClient getTransportApiClient() {
        return TransportApiClient.FACTORY.create(elasticProperties);
    }
}
