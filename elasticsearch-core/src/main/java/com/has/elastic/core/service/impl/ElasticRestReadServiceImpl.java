package com.has.elastic.core.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.has.elastic.core.builder.ElasticSearchRequestBuilder;
import com.has.elastic.core.service.ElasticReadService;
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
public class ElasticRestReadServiceImpl implements ElasticReadService {

    /**
     * Elasticsearch Rest 客户端
     */
    @Setter
    private ElasticsearchClient client;

    @Setter
    private ElasticSearchRequestBuilder elasticSearchRequestBuilder;

    public <T extends Serializable, R extends Serializable> R get(T target, Class<R> clazz) throws IOException {
        GetRequest request = this.elasticSearchRequestBuilder.createGetRequest(target);
        GetResponse<R> response = this.client.get(request, clazz);
        if (!response.found()) {
            return null;
        }
        return response.source();
    }

    public <T extends Serializable, R extends Serializable> HitsMetadata<R> search(T target, Class<R> clazz) throws IOException {
        SearchRequest request = this.elasticSearchRequestBuilder.createSearchRequest(target);
        SearchResponse<R> response = this.client.search(request, clazz);
        return response.hits();
    }

    public <T extends Serializable, R extends Serializable> HitsMetadata<R> scroll(T target, Class<R> clazz) throws IOException {
        ScrollRequest request = this.elasticSearchRequestBuilder.createScrollRequest(target);
        ScrollResponse<R> response = this.client.scroll(request, clazz);
        return response.hits();
    }

}
