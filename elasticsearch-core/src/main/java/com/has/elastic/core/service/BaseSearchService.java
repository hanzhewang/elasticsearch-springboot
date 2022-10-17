package com.has.elastic.core.service;

import com.google.common.collect.Lists;
import com.jd.kos.commons.elasticsearch.model.IndexModel;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.util.List;
import java.util.function.BiFunction;

/**
 * <p>The Elasticsearch read data base interface</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.6
 * @since JDK 1.8 and Elasticsearch 6.3
 */
public interface BaseSearchService extends BaseService {

    /**
     * get one data
     *
     * @param target
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T get(Object target, Class<T> clazz);

    /**
     * get all data
     *
     * @param target
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> find(Object target, Class<T> clazz);

    /**
     * handle elasticsearch error
     *
     * @param model
     * @param returnFunc
     * @param function
     * @param <M>
     * @param <T>
     * @param <R>
     * @return
     */
    default <M extends IndexModel, T, R> R handleError(M model, BiFunction<Long, List<T>, R> returnFunc
            , BiFunction<M, BiFunction<Long, List<T>, R>, R> function) {
        try {
            return function.apply(model, returnFunc);
        } catch (com.jd.kos.commons.elasticsearch.exception.ElasticsearchException ex) {
            throw ex;
        } catch (ElasticsearchException ex) {
            throw ex;
        } catch (Throwable cause) {
            throw cause;
        }
    }

    /**
     * handle read response data
     *
     * @param searchResponse
     * @param clazz
     * @param returnFunc
     * @param <T>
     * @param <R>
     * @return
     */
    default <T, R> R parserSearchResponse(SearchResponse searchResponse, Class<T> clazz, BiFunction<Long, List<T>, R> returnFunc) {
        SearchHits hits = searchResponse.getHits();
        Long total = hits.getTotalHits();
        List<T> content = Lists.newArrayList();
        if (clazz != null && !clazz.equals(Void.class)) {
            for (SearchHit searchHit : searchResponse.getHits().getHits()) {
                T rowDate = getJsonAdapter().fromJson(searchHit.getSourceAsString(), clazz);
                content.add(rowDate);
            }
        }
        return returnFunc.apply(total, content);
    }
}
