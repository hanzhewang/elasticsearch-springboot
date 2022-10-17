package com.has.elastic.core.builder;

import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.ScrollRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.has.elastic.core.adapter.ElasticAdapterFactory;
import com.has.elastic.core.paser.DocumentOperationParser;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

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
     * Elasticsearch document parser
     * 对象解析文档操作信息
     */
    private DocumentOperationParser documentOperationParser;

    /**
     * register object parser document info
     * 注册对象解析器, 对象解析成Elasticsearch文档信息
     *
     * @param parser
     * @return
     */
    public ElasticSearchRequestBuilder registerParser(DocumentOperationParser parser) {
        this.documentOperationParser = parser;
        return this;
    }

    /**
     * add object parser factory
     * 注册对象解析器适配工厂, 对象解析成Elasticsearch文档信息
     *
     * @param factory
     * @return
     */
    public ElasticSearchRequestBuilder addParserComponents(ElasticAdapterFactory factory) {
        this.documentOperationParser.addElasticAdapterFactory(factory);
        return this;
    }

    /**
     * 构建查询条件
     *
     * @return
     */
    public <T extends Serializable> SearchRequest createSearchRequest(T target) {
        return null;
    }

    /**
     * 构建查询条件
     *
     * @return
     */
    public <T extends Serializable> GetRequest createGetRequest(T target) {
        return null;
    }

    public <T extends Serializable> ScrollRequest createScrollRequest(T target){
        return null;
    }
}
