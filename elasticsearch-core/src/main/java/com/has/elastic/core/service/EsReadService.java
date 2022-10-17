package com.has.elastic.core.service;

import com.alibaba.fastjson.JSON;
import com.jd.kos.commons.elasticsearch.model.IndexModel;
import com.jd.kos.commons.elasticsearch.model.dto.PageDto;
import com.jd.kos.commons.elasticsearch.utils.parser.mapping.IndexParser;
import com.jd.kos.commons.elasticsearch.utils.parser.search.QueryParameterParser;
import com.jd.kos.commons.elasticsearch.utils.parser.search.QuerySortParser;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import java.util.List;
import java.util.function.BiFunction;

/**
 * <p>The Elasticsearch search service api</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2018.10.16
 * @since JDK 1.8 AND Elasticsearch 6.3
 */
public interface EsReadService extends BaseSearchService, IndexParser, QueryParameterParser, QuerySortParser {

    default <T> Long getCount(Object target, Class<T> clazz) {
        return this.search(target, new PageDto(1, 1), clazz, (total, content) -> total);
    }

    @Override
    default <T> T get(Object target, Class<T> clazz) {
        return this.search(target, new PageDto(1, 1), clazz, (total, dataArray) -> {
            if (total == null || total == 0L) {
                return null;
            } else if (total == 1) {
                return dataArray.get(1);
            } else {
                throw new ElasticsearchException("search total row {} > 1", total);
            }
        });
    }

    @Override
    default <T> List<T> find(Object target, Class<T> clazz) {
        return this.search(target, new PageDto(1, 10000), clazz, (total, dataArray) -> dataArray);
    }

    default <T, R> R search(Object target, PageDto page, Class<T> clazz, BiFunction<Long, List<T>, R> returnFunc) {
        IndexModel model = this.parserMappingInfo(target);
        getLog().error("EsReadService.search#model:{},page:{}, target:{}", JSON.toJSONString(model), JSON.toJSONString(page), JSON.toJSONString(target));
        QueryBuilder queryBuilder = this.parserQuery(target);
        List<SortBuilder> sortBuilders = this.parserSort(target);
        if (queryBuilder != null) {
            getLog().error("EsReadService.search#QueryBuilder:{}", queryBuilder.toString());
        }
        if (sortBuilders != null) {
            getLog().error("EsReadService.search#sortBuilders:{}", sortBuilders.toString());
        }

        return this.search(model, page, queryBuilder, sortBuilders, clazz, returnFunc);
    }

    default <T, R> R search(final IndexModel model, final PageDto page,
                            QueryBuilder queryBuilder, List<SortBuilder> sortBuilders,
                            final Class<T> clazz, BiFunction<Long, List<T>, R> returnFunc) {
        return handleError(model, returnFunc, (m, r) -> {
            SearchRequestBuilder builder = getTransportClient().prepareSearch(m.getIndex()).setTypes(m.getType());
            builder.setFrom(page.getFrom());
            builder.setSize(page.getPageSize());
            if (null != queryBuilder) {
                builder.setQuery(queryBuilder);
            }
            if (null != sortBuilders && sortBuilders.size() > 0) {
                for (SortBuilder sortBuilder : sortBuilders) {
                    builder.addSort(sortBuilder);
                }
            }
            SearchResponse searchResponse = builder.execute().actionGet();
            return this.parserSearchResponse(searchResponse, clazz, r);
        });
    }

}