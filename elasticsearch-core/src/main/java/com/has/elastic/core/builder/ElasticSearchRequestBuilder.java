package com.has.elastic.core.builder;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * <p>com.has.elastic.core.builder</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/12
 */
@Slf4j
public class ElasticSearchRequestBuilder {

    /**
     * 注册解析器
     *
     * @return
     */
    public ElasticSearchRequestBuilder registerPaster() {
        return this;
    }

    /**
     * 注册解析工厂(注解)
     *
     * @return
     */
    public ElasticSearchRequestBuilder registerPasterFactory() {
        return this;
    }

    /**
     * 构建查询条件
     *
     * @return
     */
    public <T> QueryBuilder create(T target) {
        return null;
    }
}
