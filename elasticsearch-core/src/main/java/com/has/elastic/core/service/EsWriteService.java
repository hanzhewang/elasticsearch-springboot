package com.has.elastic.core.service;


import com.has.elastic.base.enums.common.ErrorType;
import com.has.elastic.core.model.IndexModel;
import com.has.elastic.core.utils.parser.mapping.IndexParser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * <p>The Elasticsearch document service api </p>
 *
 * @author wanghanzhe
 * @version 6.0.1
 * @date 2018.10.16
 * @since JDK 1.8 Elasticsearch 6.3
 */
public interface EsWriteService extends BaseService, IndexParser {

    /**
     * The elasticsearch save document
     *
     * @param target
     */
    default void upsert(Object target) {
        if (target == null) {
            throw new ElasticsearchException("The elasticsearch save document is failures: target is null");
        }
        IndexRequestBuilder indexRequest = getIndexRequestBuilder(target);
        IndexResponse indexResponse = indexRequest.get();
        RestStatus status = indexResponse.status();
        if (!RestStatus.OK.equals(status)) {
            throw new ElasticsearchException(StringUtils.join("The elasticsearch save document is failures: status=", status.getStatus()));
        }
    }

    /**
     * The elasticsearch save document
     *
     * @param target
     */
    default void upsertSection(Object target) {
        if (target == null) {
            throw new ElasticsearchException("The elasticsearch save document is failures: target is null");
        }
        UpdateRequestBuilder updateRequest = getUpdateRequestBuilder(target);
        UpdateResponse response = updateRequest.get();
        RestStatus status = response.status();
        if (!RestStatus.OK.equals(status)) {
            throw new ElasticsearchException(StringUtils.join("The elasticsearch save document is failures: status=", status.getStatus()));
        }
    }

    /**
     * The elasticsearch delete document
     *
     * @param target
     */
    default void delete(Object target) {
        if (target == null) {
            throw new ElasticsearchException("The elasticsearch save document is failures: target is null");
        }
        DeleteRequestBuilder deleteRequest = getDeleteRequestBuilder(target);
        DeleteResponse response = deleteRequest.get();
        RestStatus status = response.status();
        if (!RestStatus.OK.equals(status)) {
            throw new ElasticsearchException(StringUtils.join("The elasticsearch delete document is failures: status=", status.getStatus()));
        }
    }

    /**
     * The elasticsearch batch save document
     *
     * @param targets
     */
    default void upsertAll(List<? extends Object> targets) {
        bulkRequest(targets, (bulkRequest, target) -> {
            IndexRequestBuilder indexRequest = getIndexRequestBuilder(target);
            bulkRequest.add(indexRequest);
        });
    }

    /**
     * The elasticsearch batch save document
     *
     * @param targets
     */
    default void upsertSectionAll(List<?> targets) {
        bulkRequest(targets, (bulkRequest, target) -> {
            UpdateRequestBuilder updateRequest = getUpdateRequestBuilder(target);
            bulkRequest.add(updateRequest);
        });
    }

    /**
     * The elasticsearch batch delete document
     *
     * @param targets
     */
    default void deleteAll(List<?> targets) {
        bulkRequest(targets, (bulkRequest, target) -> {
            DeleteRequestBuilder deleteRequest = getDeleteRequestBuilder(target);
            bulkRequest.add(deleteRequest);
        });
    }

    /**
     * The elasticsearch batch request common method
     *
     * @param targets
     * @param documentFunc
     */
    default <T> void bulkRequest(List<T> targets, BiConsumer<BulkRequestBuilder, T> documentFunc) {
        try {
            if (CollectionUtils.isEmpty(targets) || documentFunc == null) {
                throw new ElasticsearchException("The elasticsearch bulk document is failures: targets is null:");
            }
            BulkRequestBuilder bulkRequest = getTransportClient().prepareBulk();
            for (T target : targets) {
                if (target == null) {
                    throw new ElasticsearchException("The elasticsearch bulk document is failures: target is null");
                }
                documentFunc.accept(bulkRequest, target);
            }
            BulkResponse bulkResponse = bulkRequest.execute().actionGet();
            if (bulkResponse.hasFailures()) {
                throw new ElasticsearchException("elasticsearch bulk document is failures:" + bulkResponse.buildFailureMessage());
            }
        } catch (Exception ex) {
            throw new ElasticsearchException("elasticsearch bulk document is failures: error=", ex);
        }
    }

    /**
     * Get elasticsearch save request builder
     *
     * @param target
     */
    default IndexRequestBuilder getIndexRequestBuilder(Object target) {
        IndexModel model = this.parserMappingInfo(target);
        getLog().error("EsWriteService.getIndexRequestBuilder#model:{},object:{}", JSON.toJSONString(model), JSON.toJSONString(target));
        Object requestParameter = null;
        if (model.isNested()) {
            throw new ElasticsearchException("");
        }
        if (model.isJoin()) {
            requestParameter = model.getWriteParameterByJoin(target);
        } else {
            requestParameter = target;
        }
        String json = getJsonAdapter().toJsonString(requestParameter);
        String uuid = model.getFieldValues().get(model.getUuid()).toString();
        IndexRequestBuilder indexRequest = getTransportClient()
                .prepareIndex(model.getIndex(), model.getType(), uuid)
                .setSource(json, XContentType.JSON);
        return indexRequest;
    }

    /**
     * Get elasticsearch save request builder
     *
     * @param target
     */
    default UpdateRequestBuilder getUpdateRequestBuilder(Object target) {
        IndexModel model = this.parserMappingInfo(target);
        getLog().error("EsWriteService.getUpdateRequestBuilder#model:{},object:{}", JSON.toJSONString(model), JSON.toJSONString(target));
        String uuid = model.getFieldValues().get(model.getUuid()).toString();
        UpdateRequestBuilder updateRequest = getTransportClient().prepareUpdate(model.getIndex(), model.getType(), uuid).setDocAsUpsert(true);
        Object requestParameter = null;
        if (model.isJoin()) {
            requestParameter = model.getWriteParameterByJoin(target);
        } else if (model.isNested()) {
            requestParameter = model.getWriteParameterByNested(target);
        } else {
            requestParameter = target;
        }
        String json = getJsonAdapter().toJsonString(requestParameter);
        updateRequest.setDoc(json, XContentType.JSON);
        return updateRequest;
    }

    /**
     * Get elasticsearch delete request builder
     *
     * @param target
     */
    default DeleteRequestBuilder getDeleteRequestBuilder(Object target) {
        IndexModel model = this.parserMappingInfo(target);
        getLog().error("EsWriteService.getDeleteRequestBuilder#model:{},object:{}", JSON.toJSONString(model), JSON.toJSONString(target));
        String uuid = model.getFieldValues().get(model.getUuid()).toString();
        DeleteRequestBuilder deleteRequest = getTransportClient().prepareDelete(model.getIndex(), model.getType(), uuid);
        getTransportClient();
        String router = model.getDeleteRouter();
        if (StringUtils.isNotBlank(router)) {
            deleteRequest.setRouting(router);
        }
        if (model.isNested()) {
            throw new com.jd.kos.commons.elasticsearch.exception.ElasticsearchException(ErrorType.NESTED_NOT_DELETE);
        }
        return deleteRequest;
    }

}