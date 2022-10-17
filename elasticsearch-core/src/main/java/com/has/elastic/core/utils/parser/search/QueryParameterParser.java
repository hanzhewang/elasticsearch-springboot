package com.has.elastic.core.utils.parser.search;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jd.kos.commons.elasticsearch.annotation.search.QueryParameter;
import com.jd.kos.commons.elasticsearch.enums.mapping.ObjectType;
import com.jd.kos.commons.elasticsearch.enums.search.QueryParameterType;
import com.jd.kos.commons.elasticsearch.model.dto.QueryParserDto;
import com.jd.kos.commons.elasticsearch.utils.parser.mapping.BaseParser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.join.query.JoinQueryBuilders;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>The elasticsearch search parameter annotation parser</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2018.6.10
 * @since JDK1.8 and Elasticsearch 6.3
 */
public interface QueryParameterParser extends BaseParser {

    /**
     * parser query parameter object
     *
     * @param target
     * @return
     */
    default QueryBuilder parserQuery(Object target) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // reflect object field value array
        Map<String, Object> fieldsValue = getReflectAdapter().getFieldsValueMap(target);
        // reflect object field annotation array
        Map<String, Annotation[]> fieldsAnnotation = getReflectAdapter().getFieldsAnnotationsMap(target);
        // bool group
        getLog().error("QueryParameterParser.parserQuery#fieldsValue:{},fieldsAnnotation:{}", JSON.toJSONString(fieldsValue), JSON.toJSONString(fieldsAnnotation));
        Map<String, List<QueryParserDto>> boolGroupMap = boolGroup(fieldsValue, fieldsAnnotation);
        // bool group parser to BoolQueryBuilder
        boolQueryBuilder = boolGroupParser(boolQueryBuilder, boolGroupMap);
        if (boolQueryBuilder != null) {
            getLog().error("QueryParameterParser.parserQuery#boolQueryBuilder:{}", boolQueryBuilder.toString());
        }
        return boolQueryBuilder;
    }

    /**
     * parameter group
     *
     * @param fieldsValue
     * @param fieldsAnnotation
     * @return
     */
    default Map<String, List<QueryParserDto>> boolGroup(Map<String, Object> fieldsValue, Map<String, Annotation[]> fieldsAnnotation) {
        Map<String, List<QueryParserDto>> boolGroupMap = Maps.newHashMap();
        for (Map.Entry<String, Annotation[]> entry : fieldsAnnotation.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = fieldsValue.get(fieldName);
            if (null == fieldValue) {
                continue;
            }
            if (fieldValue instanceof String && StringUtils.isBlank(fieldValue.toString())) {
                continue;
            }
            Annotation[] annotations = entry.getValue();
            for (Annotation annotation : annotations) {
                if (annotation instanceof QueryParameter) {
                    QueryParameter field = (QueryParameter) annotation;
                    QueryParserDto dto = new QueryParserDto();
                    dto.setName(getFiledName(field, fieldName));
                    dto.setValue(fieldValue);
                    dto.setField(field);
                    getLog().error("QueryParameterParser.boolGroup#queryParserDto:{}", boolGroupMap.toString());
                    addMapList(boolGroupMap, field.boolGroup(), dto);
                    break;
                }
            }
        }
        if (boolGroupMap != null) {
            getLog().error("QueryParameterParser.boolGroup#fieldsValue {},fieldsAnnotation:{}", boolGroupMap.toString());
        }
        return boolGroupMap;
    }

    /**
     * group parser
     *
     * @param boolBuilder
     * @param boolGroupMap
     */
    default BoolQueryBuilder boolGroupParser(BoolQueryBuilder boolBuilder, Map<String, List<QueryParserDto>> boolGroupMap) {
        List<BoolQueryBuilder> queryBuilders = Lists.newArrayList();
        for (String boolPath : boolGroupMap.keySet()) {
            List<QueryParserDto> queryParsers = boolGroupMap.get(boolPath);
            if (CollectionUtils.isEmpty(queryParsers)) {
                continue;
            }
            BoolQueryBuilder groupBoolBuilder = QueryBuilders.boolQuery();
//// TODO: 2019-09-09 groupBoolBuilder.minimumShouldMatch(1);
//// TODO: 2019-09-09 去掉算分模式
            for (QueryParserDto queryParser : queryParsers) {
                QueryParameter queryField = queryParser.getField();
                QueryBuilder queryBuilder = parserQueryType(queryField.type(), queryParser.getName(), queryParser.getValue());
                if (queryBuilder == null) {
                    continue;
                }
                // elasticsearch new features
                switch (queryField.object()) {
                    case NESTED:
                        queryBuilder = QueryBuilders.nestedQuery(queryField.path(), queryBuilder, ScoreMode.None);
                        break;
                    case JOIN_PARENT:
                        queryBuilder = JoinQueryBuilders.hasParentQuery(queryField.path(), queryBuilder, true);
                        break;
                    case JOIN_CHILD:
                        queryBuilder = JoinQueryBuilders.hasChildQuery(queryField.path(), queryBuilder, ScoreMode.None);
                        break;
                    default:
                        break;
                }
                // elasticsearch bool
                switch (queryField.bool()) {
                    case MUST:
                        groupBoolBuilder.must(queryBuilder);
                        break;
                    case SHOULD:
                        groupBoolBuilder.should(queryBuilder);
                        break;
                    case MUST_NOT:
                        groupBoolBuilder.mustNot(queryBuilder);
                        break;
                    case FILTER:
                        groupBoolBuilder.filter(queryBuilder);
                        break;
                    default:
                        break;
                }
            }
            if (groupBoolBuilder != null) {
                getLog().error("QueryParameterParser.boolGroupParser#groupBoolBuilder:{}", groupBoolBuilder.toString());
            }
            queryBuilders.add(groupBoolBuilder);
        }
        if (queryBuilders.size() == 1) {
            return queryBuilders.get(0);
        } else {
            for (QueryBuilder queryBuilder : queryBuilders) {
                boolBuilder.must(queryBuilder);
            }
            return boolBuilder;
        }
    }

    /**
     * parser query condition by type
     *
     * @param type
     * @param fieldName
     * @param fieldValue
     * @return
     */
    default QueryBuilder parserQueryType(QueryParameterType type, String fieldName, Object fieldValue) {
        QueryBuilder queryBuilder = null;
        switch (type) {
            case TERM:
                queryBuilder = QueryBuilders.termQuery(fieldName, fieldValue);
                break;
            case TERMS:
                queryBuilder = parserTerms(fieldName, fieldValue);
                break;
            case RANGE_GT:
                queryBuilder = QueryBuilders.rangeQuery(fieldName).gt(getObject(fieldValue));
                break;
            case RANGE_GTE:
                queryBuilder = QueryBuilders.rangeQuery(fieldName).gte(getObject(fieldValue));
                break;
            case RANGE_LT:
                queryBuilder = QueryBuilders.rangeQuery(fieldName).lt(getObject(fieldValue));
                break;
            case RANGE_LTE:
                queryBuilder = QueryBuilders.rangeQuery(fieldName).lte(getObject(fieldValue));
                break;
            case EXISTS:
                queryBuilder = parserExists(fieldName, fieldValue);
                break;
            case MATCH:
                queryBuilder = QueryBuilders.matchQuery(fieldName, fieldValue);
                break;
            case PREFIX:
                queryBuilder = parserPrefix(fieldName, fieldValue);
                break;
            case WILDCARD:
                queryBuilder = parserWildcard(fieldName, fieldValue);
                break;
            case REGEXP:
                queryBuilder = parserRegexp(fieldName, fieldValue);
                break;
            default:
                break;
        }
        return queryBuilder;
    }

    /**
     * terms query parser
     *
     * @param fieldName
     * @param value
     * @return
     */
    default QueryBuilder parserTerms(String fieldName, Object value) {
        Object[] values = null;
        if (value instanceof Collection) {
            values = ((Collection) value).toArray();
        } else if (value.getClass().isArray()) {
            values = (Object[]) value;
        } else {
            values = new Object[]{value};
        }
        if (null == values || values.length == 0) {
            return null;
        }
        return QueryBuilders.termsQuery(fieldName, values);
    }

    /**
     * object is not null
     *
     * @param fieldName
     * @param value
     * @return
     */
    default QueryBuilder parserExists(String fieldName, Object value) {
        boolean is = false;
        if (value instanceof Boolean && Boolean.TRUE.equals(value)) {
            is = true;
        }
        if (is) {
            return QueryBuilders.existsQuery(fieldName);
        }
        return null;
    }

    /**
     * prefix query parser
     *
     * @param fieldName
     * @param value
     * @return
     */
    default QueryBuilder parserPrefix(final String fieldName, final Object value) {
        return parserIsNull(value, fieldValue -> QueryBuilders.prefixQuery(fieldName, fieldValue));
    }

    /**
     * wildcard query parser
     *
     * @param fieldName
     * @param value
     * @return
     */
    default QueryBuilder parserWildcard(final String fieldName, final Object value) {
        return parserIsNull(value, fieldValue -> QueryBuilders.wildcardQuery(fieldName, fieldValue));
    }

    /**
     * regexp query parserx
     *
     * @param fieldName
     * @param value
     * @return
     */
    default QueryBuilder parserRegexp(final String fieldName, final Object value) {
        return parserIsNull(value, fieldValue -> QueryBuilders.regexpQuery(fieldName, fieldValue));
    }

    /**
     * Get Elasticsearch property name
     *
     * @param queryField
     * @param defaultName
     * @return
     */
    default String getFiledName(QueryParameter queryField, String defaultName) {
        String queryFiledName = defaultName;
        if (StringUtils.isNotBlank(queryField.name())) {
            queryFiledName = queryField.name();
        }
        if (ObjectType.NESTED.equals(queryField.object()) && StringUtils.isNotBlank(queryField.path())) {
            return StringUtils.join(queryField.path(), ".", queryFiledName);
        }
        return queryFiledName;
    }

    /**
     * get object
     *
     * @param target
     * @return
     */
    default Object getObject(Object target) {
        if (target instanceof Date) {
            return ((Date) target).getTime();
        } else {
            return target;
        }
    }

    /**
     * parser validation parameter is null
     *
     * @param value
     * @param func
     * @return
     */
    default QueryBuilder parserIsNull(Object value, Function<String, QueryBuilder> func) {
        String fieldValue = null;
        boolean is = false;
        if (value != null && !Strings.isNullOrEmpty((fieldValue = String.valueOf(value).trim()))) {
            is = true;
        }
        if (is) {
            return func.apply(fieldValue);
        }
        return null;
    }

    /**
     * map to list function
     *
     * @param map
     * @param key
     * @param value
     */
    default void addMapList(Map<String, List<QueryParserDto>> map, String key, QueryParserDto value) {
        if (null == map) {
            map = Maps.newHashMap();
        }
        List<QueryParserDto> parserDtos;
        if (!map.containsKey(key) || null == (parserDtos = map.get(key))) {
            parserDtos = Lists.newArrayList();
        }
        parserDtos.add(value);
        map.put(key, parserDtos);
    }

}