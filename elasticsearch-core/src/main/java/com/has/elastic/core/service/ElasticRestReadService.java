package com.has.elastic.core.service;

import com.has.elastic.core.builder.ElasticDeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.io.Serializable;

/**
 * <p>com.has.elastic.core.service</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/17
 */
public class ElasticRestReadService {

    /**
     * Elasticsearch Rest 客户端
     */
    private static RestHighLevelClient client;

    public <T extends Serializable> RestStatus index(T target) throws IOException {
        IndexRequest request = new IndexRequest();
        IndexRequest.Builder<T> indexReqBuilder = new IndexRequest.Builder<>();

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        return response.status();
    }

    public <T extends Serializable> RestStatus update(T target) throws IOException {
        UpdateRequest request = new UpdateRequest();
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        return response.status();
    }

    public <T extends Serializable> RestStatus delete(T target) throws IOException {
        DeleteRequest request = new ElasticDeleteRequestBuilder().create(target);
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        return response.status();
    }
}
