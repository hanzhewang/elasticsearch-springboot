package com.has.elastic.core.builder;

import co.elastic.clients.elasticsearch.core.IndexRequest;
import com.has.elastic.core.adapter.ElasticAdapterFactory;
import com.has.elastic.core.bo.ElasticDocumentBo;
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
public class ElasticIndexRequestBuilder {

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
    public ElasticIndexRequestBuilder registerParser(DocumentOperationParser parser) {
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
    public ElasticIndexRequestBuilder addParserComponents(ElasticAdapterFactory factory) {
        this.documentOperationParser.addElasticAdapterFactory(factory);
        return this;
    }

    /**
     * create Elasticsearch insert or update document request object
     * 创建删除请求对象
     *
     * @return
     */
    public <T extends Serializable> IndexRequest<T> create(T target) {
        ElasticDocumentBo bo = documentOperationParser.parser(target);
        IndexRequest.Builder<T> builder = new IndexRequest.Builder();
        builder.index(bo.getIndex()).type(bo.getType()).id(bo.getId());
        builder.document(target);
        return builder.build();
    }
}
