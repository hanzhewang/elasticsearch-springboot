package com.has.elastic.core.builder;

import co.elastic.clients.elasticsearch.core.DeleteRequest;
import com.has.elastic.core.adapter.ElasticAdapterFactory;
import com.has.elastic.core.bo.ElasticDocumentBo;
import com.has.elastic.core.paser.DocumentOperationParser;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * <p>Elasticsearch document delete builder</p>
 * Elasticsearch 删除请求对象建造者
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/12
 * @since 1.8
 */
@Slf4j
public class ElasticDeleteRequestBuilder {

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
    public ElasticDeleteRequestBuilder registerParser(DocumentOperationParser parser) {
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
    public ElasticDeleteRequestBuilder addParserComponents(ElasticAdapterFactory factory) {
        this.documentOperationParser.addElasticAdapterFactory(factory);
        return this;
    }

    /**
     * create Elasticsearch delete document request object
     * 创建删除请求对象
     *
     * @return
     */
    public <T extends Serializable> DeleteRequest create(T target) {
        ElasticDocumentBo bo = documentOperationParser.parser(target);
        DeleteRequest.Builder builder = new DeleteRequest.Builder();
        builder.index(bo.getIndex()).type(bo.getType()).id(bo.getId());
        return builder.build();
    }
}
