package com.has.elastic.core.service;

import com.jd.kos.commons.elasticsearch.model.IndexModel;
import com.jd.kos.commons.elasticsearch.utils.parser.mapping.IndexParser;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * <p>The search template query service interface</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.6
 * @since JDK 1.8 and Elasticsearch 6.3
 */
public interface EsReadForTemplateService extends BaseSearchService, IndexParser {

    /**
     * The search template search query total count
     *
     * @param target
     * @param <T>
     * @return
     */
    default <T> Long getCount(Object target) {
        IndexModel model = this.parserMappingInfo(target);
        return this.search(model, model.getFieldValues(), Void.class, (total, dataArray) -> total);
    }

    /**
     * The search template query object
     *
     * @param target
     * @param clazz
     * @param <T>
     * @return one object
     */
    @Override
    default <T> T get(Object target, Class<T> clazz) {
        IndexModel model = this.parserMappingInfo(target);
        return this.search(model, model.getFieldValues(), clazz, (total, dataArray) -> {
            if (total == null || total == 0L) {
                return null;
            } else if (total == 1) {
                return dataArray.get(1);
            } else {
                throw new ElasticsearchException("search total row {} > 1", total);
            }
        });
    }

    /**
     * The search template query list
     *
     * @param target
     * @param clazz
     * @param <T>
     * @return fixed length array
     */
    @Override
    default <T> List<T> find(Object target, Class<T> clazz) {
        IndexModel model = this.parserMappingInfo(target);
        return this.search(model, model.getFieldValues(), clazz, (total, dataArray) -> dataArray);
    }

    /**
     * The search template search function
     *
     * @param target
     * @param clazz      json parser object type
     * @param returnFunc return function
     * @param <T>
     * @param <R>
     * @return
     */
    default <T, R> R search(Object target, Class<T> clazz, BiFunction<Long, List<T>, R> returnFunc) {
        IndexModel model = this.parserMappingInfo(target);
        return search(model, model.getFieldValues(), clazz, returnFunc);
    }

    /**
     * The search template search function
     *
     * @param model      template relevant configuration
     * @param clazz      json parser object type
     * @param returnFunc return function
     * @param <T>
     * @param <R>
     * @return
     */
    default <T, R> R search(final IndexModel model, Map<String, Object> params, final Class<T> clazz, BiFunction<Long, List<T>, R> returnFunc) {
        return handleError(model, returnFunc, (m, r) -> {
            SearchResponse searchResponse = new SearchTemplateRequestBuilder(getTransportClient())
                    .setScriptType(m.getTemplate().getScriptType())
                    .setScript(m.getTemplate().getScript())
                    .setRequest(new SearchRequest(m.getIndex()).types(m.getType()))
                    .setScriptParams(params)
                    .get()
                    .getResponse();
            return this.parserSearchResponse(searchResponse, clazz, r);
        });
    }

}
