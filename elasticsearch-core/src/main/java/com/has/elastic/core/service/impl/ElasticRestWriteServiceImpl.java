package com.has.elastic.core.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.*;
import com.has.elastic.core.builder.ElasticDeleteRequestBuilder;
import com.has.elastic.core.builder.ElasticIndexRequestBuilder;
import com.has.elastic.core.builder.ElasticUpdateRequestBuilder;
import com.has.elastic.core.service.ElasticWriteService;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;

/**
 * <p>com.has.elastic.core.service</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/17
 */
public class ElasticRestWriteServiceImpl implements ElasticWriteService {

    /**
     * Elasticsearch Rest 客户端
     */
    @Setter
    private ElasticsearchClient client;

    @Setter
    private ElasticIndexRequestBuilder elasticIndexRequestBuilder;

    @Setter
    private ElasticUpdateRequestBuilder elasticUpdateRequestBuilder;

    @Setter
    private ElasticDeleteRequestBuilder elasticDeleteRequestBuilder;

    public <T extends Serializable> Result index(T target) throws IOException {
        IndexRequest request = this.elasticIndexRequestBuilder.create(target);
        IndexResponse response = client.index(request);
        return response.result();
    }

    public <T extends Serializable> Result update(T target) throws IOException {
        UpdateRequest request = this.elasticUpdateRequestBuilder.create(target);
        UpdateResponse<T> response = client.update(request, target.getClass());
        return response.result();
    }

    public <T extends Serializable> Result delete(T target) throws IOException {
        DeleteRequest request = this.elasticDeleteRequestBuilder.create(target);
        DeleteResponse response = client.delete(request);
        return response.result();
    }
}
