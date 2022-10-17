package com.has.elastic.core.builder;

import com.has.elastic.core.adapter.ElasticAdapterFactory;
import com.has.elastic.core.bo.ElasticDocumentBo;
import com.has.elastic.core.paser.DocumentOperationParser;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteRequestBuilder;

import java.io.Serializable;

/**
 * <p>com.has.elastic.core.builder</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/12
 */
@Slf4j
public class ElasticDeleteRequestBuilder {

    private DocumentOperationParser documentOperationParser;

    /**
     * @param parser
     * @return
     */
    public ElasticDeleteRequestBuilder registerParser(DocumentOperationParser parser) {
        this.documentOperationParser = documentOperationParser;
        return this;
    }

    /**
     * @param factory
     * @return
     */
    public ElasticDeleteRequestBuilder addParserComponents(ElasticAdapterFactory factory) {
        this.documentOperationParser.addElasticAdapterFactory(factory);
        return this;
    }

    /**
     * @return
     */
    public <T extends Serializable> DeleteRequest create(T target) {
        ElasticDocumentBo bo = documentOperationParser.parser(target);
        DeleteRequest request = new DeleteRequest().index(index).type(type).id(id);
        return request;
    }
}
