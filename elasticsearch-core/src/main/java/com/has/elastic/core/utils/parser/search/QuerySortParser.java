package com.has.elastic.core.utils.parser.search;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.jd.kos.commons.elasticsearch.annotation.search.QuerySort;
import com.jd.kos.commons.elasticsearch.utils.parser.mapping.BaseParser;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * <p>The elasticsearch sort annotation parser</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2018.7.9
 * @since JDK1.8 and Elasticsearch 6.3
 */
public interface QuerySortParser extends BaseParser {

    /**
     * The elasticsearch parser search sort rule
     *
     * @param target
     * @return
     */
    default List<SortBuilder> parserSort(Object target) {
        Map<String, Object> fieldsValue = getReflectAdapter().getFieldsValueMap(target);
        Annotation[] annotations = getReflectAdapter().getAnnotationsByClass(target.getClass());
        getLog().error("QuerySortParser.parserSort#fieldsValue:{},annotations:{}", JSON.toJSONString(fieldsValue), JSON.toJSONString(annotations));
        int len = annotations != null ? annotations.length : 0;
        for (int index = 0; index < len; index++) {
            Annotation annotation = annotations[index];
            if (annotation instanceof QuerySort) {
                QuerySort querySort = (QuerySort) annotation;
                getLog().error("QuerySortParser.parserSort#querySort:{}", querySort.toString());
                return parserQuerySort(querySort, fieldsValue);
            }
        }
        return null;
    }

    /**
     * The Elasticsearch search sort order parser annotation
     *
     * @param querySort   sort order annotation
     * @param fieldsValue field value
     * @return
     */
    default List<SortBuilder> parserQuerySort(QuerySort querySort, Map<String, Object> fieldsValue) {
        getLog().error("QuerySortParser.parserQuerySort#parserQuerySort:{},fieldsValue:{}", querySort, JSON.toJSONString(fieldsValue));
        List<SortBuilder> sortBuilders = Lists.newArrayList();
        String[] properties = querySort.properties();
        String[] rules = querySort.rules();
        int ruleLen = rules != null ? rules.length : 0;
        int propertyLen = properties.length;
        for (int propertyIndex = 0; propertyIndex < propertyLen; propertyIndex++) {
            String property = properties[propertyIndex];
            String rule = null;
            if (propertyIndex < ruleLen) {
                rule = rules[propertyIndex];
            }
            SortBuilder sortBuilder = null;
            switch (querySort.mode()) {
                case DYNAMIC:
                    sortBuilder = dynamicSort(fieldsValue, property, rule);
                    if(sortBuilder != null){
                        getLog().error("QuerySortParser.parserQuerySort#dynamic:{},sortBuilder:{}", sortBuilder.toString());
                    }
                    break;
                case STATICS:
                    sortBuilder = staticsSort(property, rule);
                    if(sortBuilder != null){
                        getLog().error("QuerySortParser.parserQuerySort#statics:{},sortBuilder:{}", sortBuilder.toString());
                    }
                default:
            }
            sortBuilders.add(sortBuilder);
        }
        if (sortBuilders != null) {
            getLog().error("QuerySortParser.parserQuerySort#response:{},fieldsValue:{}", sortBuilders.toString());
        }
        return sortBuilders;
    }

    /**
     * The Elasticsearch search dynamic sort order
     *
     * @param fieldsValue field value
     * @param property    sort field property name
     * @param rule        sort rule field property name
     * @return
     */
    default SortBuilder dynamicSort(Map<String, Object> fieldsValue, String property, String rule) {
        Object sortPropertyName = null;
        if (StringUtils.isBlank(property) || (sortPropertyName = fieldsValue.get(rule)) == null) {
            return null;
        }
        SortBuilder sortBuilder = SortBuilders.fieldSort(sortPropertyName.toString());
        SortOrder sortOrder = SortOrder.DESC;
        Object sortPropertyRule = null;
        if (StringUtils.isNotBlank(rule) && (sortPropertyRule = fieldsValue.get(rule)) != null
                && SortOrder.valueOf(sortPropertyRule.toString()) != null) {
            sortOrder = SortOrder.valueOf(sortPropertyRule.toString());
        }
        sortBuilder.order(sortOrder);
        return sortBuilder;
    }


    /**
     * The Elasticsearch search statics sort order
     *
     * @param property sort field property name
     * @param rule     sort rule field property name
     * @return
     */
    default SortBuilder staticsSort(String property, String rule) {
        SortBuilder sortBuilder = SortBuilders.fieldSort(property);
        SortOrder sortOrder = SortOrder.DESC;
        if (rule != null && SortOrder.valueOf(rule) != null) {
            sortOrder = SortOrder.valueOf(rule.toUpperCase());
        }
        sortBuilder.order(sortOrder);
        return sortBuilder;
    }

}