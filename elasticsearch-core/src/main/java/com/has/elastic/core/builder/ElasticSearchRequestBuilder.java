package com.has.elastic.core.builder;

import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.ScoreSort;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.ScrollRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.google.common.collect.Maps;
import com.has.elastic.base.enums.search.SortRule;
import com.has.elastic.core.adapter.ElasticAdapterFactory;
import com.has.elastic.core.bo.ElasticConditionBo;
import com.has.elastic.core.bo.ElasticDocumentBo;
import com.has.elastic.core.bo.ElasticSortBo;
import com.has.elastic.core.paser.DocumentOperationParser;
import com.has.elastic.core.paser.QueryConditionParser;
import com.has.elastic.core.paser.QuerySortParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>com.has.elastic.core.builder</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/12
 */
@Slf4j
public class ElasticSearchRequestBuilder {

    /**
     * Elasticsearch document parser
     * 对象解析文档操作信息
     */
    private DocumentOperationParser documentOperationParser;

    private QueryConditionParser queryConditionParser;

    private QuerySortParser querySortParser;

    /**
     * register object parser document info
     * 注册对象解析器, 对象解析成Elasticsearch文档信息
     *
     * @param parser
     * @return
     */
    public ElasticSearchRequestBuilder registerParser(DocumentOperationParser parser) {
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
    public ElasticSearchRequestBuilder addParserComponents(ElasticAdapterFactory factory) {
        this.documentOperationParser.addElasticAdapterFactory(factory);
        return this;
    }

    /**
     * 构建查询条件
     *
     * @return
     */
    public <T extends Serializable> SearchRequest createSearchRequest(T target) {
        ElasticDocumentBo bo = this.documentOperationParser.parser(target);
        SearchRequest.Builder builder = new SearchRequest.Builder();
        builder.index(bo.getIndex()).type(bo.getType());
        Query query = this.getQuery(bo, target);
        builder.query(query);
        List<SortOptions> sortOptions = this.getSortOptions(bo, target);
        builder.sort(sortOptions);
        return builder.build();
    }

    /**
     * 构建查询条件
     *
     * @return
     */
    public <T extends Serializable> GetRequest createGetRequest(T target) {
        return null;
    }

    public <T extends Serializable> ScrollRequest createScrollRequest(T target) {
        return null;
    }


    protected <T extends Serializable> Map<String, BoolQuery.Builder> get(List<ElasticConditionBo> conditions, T target) throws IllegalAccessException {
        Map<String, BoolQuery.Builder> map = Maps.newHashMap();
        if (CollectionUtils.isEmpty(conditions)) {
            return null;
        }
        for (ElasticConditionBo condition : conditions) {
            Field field = condition.getField();
            Object value = field.get(target);
            if (value == null) {
                continue;
            }
            Query query = getQuery(condition, target);
            if (query == null) {
                continue;
            }
            BoolQuery.Builder builder = map.get(condition.getBoolGroup());
            if (builder == null) {
                builder = new BoolQuery.Builder();
                map.put(condition.getBoolGroup(), builder);
            }
            bool(condition, builder, query);
        }
        return map;
    }

    private void bool(ElasticConditionBo condition, BoolQuery.Builder builder, Query query) {
        switch (condition.getBool()) {
            case MUST:
                builder.must(query);
                break;
            case MUST_NOT:
                builder.mustNot(query);
                break;
            case SHOULD:
                builder.should(query);
                break;
            case FILTER:
                builder.filter(query);
                break;
        }
    }

    /**
     * parser query condition by type
     *
     * @param condition
     * @param target
     * @return
     */
    public <T extends Serializable> Query getQuery(ElasticConditionBo condition, T target) throws IllegalAccessException {
        if (condition == null || target == null || condition.getCondition() == null) {
            return null;
        }
        Object fieldvalue = condition.getField().get(target);
        Query query = null;
        switch (condition.getCondition()) {
            case TERM:
                // todo boost
                query = new TermQuery.Builder().boost(condition.getBoost()).field(condition.getElasticName()).value(fieldValue).build()._toQuery();
                break;
            case TERMS:
                // todo boost
                query = new TermsQuery.Builder().field(condition.getElasticName()).build()._toQuery();
                break;
            case RANGE_GT:
                // todo boost
                query = new RangeQuery.Builder().field(condition.getElasticName()).gt(fieldvalue).build()._toQuery();
                break;
            case RANGE_GTE:
                // todo boost
                query = new RangeQuery.Builder().field(condition.getElasticName()).gte(fieldvalue).build()._toQuery();
                break;
            case RANGE_LT:
                // todo boost
                query = new RangeQuery.Builder().field(condition.getElasticName()).lt(fieldvalue).build()._toQuery();
                break;
            case RANGE_LTE:
                // todo boost
                query = new RangeQuery.Builder().boost().field(condition.getElasticName()).lte(fieldvalue).build()._toQuery();
                break;
            case EXISTS:
                // todo boost
                query = new ExistsQuery.Builder().boost(Boolean.TRUE).field(condition.getElasticName()).build()._toQuery();
                break;
            case MATCH:
                // todo boost
                query = new MatchQuery.Builder().boost().field(condition.getElasticName()).build()._toQuery();
                break;
            case PREFIX:
                // todo caseInsensitive  boost
                query = new PrefixQuery.Builder().boost().caseInsensitive(Boolean.TRUE).field(condition.getElasticName()).value(fieldvalue).build()._toQuery();
                break;
            case WILDCARD:
                String value = getFieldValue(fieldvalue);
                // todo caseInsensitive  boost
                query = new WildcardQuery.Builder().boost()
                        .caseInsensitive(Boolean.TRUE)
                        .field(condition.getElasticName())
                        .value(value).build()._toQuery();
                break;
            case REGEXP:
                String value = getFieldValue(fieldvalue);
                // todo caseInsensitive  boost
                query = new RegexpQuery.Builder()
                        .boost()
                        .caseInsensitive(condition.getCaseInsensitive())
                        .field(condition.getElasticName())
                        .value(value).build()._toQuery();
                break;
            default:
                break;
        }
        return query;
    }

    private String getFieldValue(Object fieldValue) {
        if (fieldValue != null) {
            return fieldValue.toString();
        }
        return "";
    }

    /**
     * @param bo
     * @param target
     * @param <T>
     * @return
     */
    protected <T extends Serializable> List<SortOptions> getSortOptions(ElasticDocumentBo bo, T target) {
        List<ElasticSortBo> sorts = bo.getSorts();
        if (CollectionUtils.isEmpty(sorts)) {
            return null;
        }
        return sorts.stream().sorted(Comparator.comparing(sort -> sort.getSort())).map(sort -> {
            SortOptions.Builder builder = new SortOptions.Builder();
            // 排序属性
            FieldSort.Builder fieldSortBuilder = new FieldSort.Builder();
            fieldSortBuilder.field(sort.getElasticName());
            builder.field(fieldSortBuilder.build());
            // 排序规则
            ScoreSort.Builder sortBuilder = new ScoreSort.Builder();
            if (SortRule.DESC.equals(sort.getSortRule())) {
                sortBuilder.order(SortOrder.Desc);
            } else {
                sortBuilder.order(SortOrder.Asc);
            }
            builder.score(sortBuilder.build());
            return builder.build();
        }).collect(Collectors.toList());
    }
}
