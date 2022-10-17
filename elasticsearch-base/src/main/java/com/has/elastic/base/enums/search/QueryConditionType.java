package com.has.elastic.base.enums.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>elasticsearch query type enum</p>
 *
 * @author wanghanzhe
 * @version 6.0.1
 * @date 2018.10.16
 * @since JDK 1.8 Elasticsearch 6.x
 */
@AllArgsConstructor
public enum QueryConditionType {
    /**
     * The Elasticsearch term query
     * Consistent with "=" in SQL
     */
    TERM("term", "精确查询", null),
    /**
     * The Elasticsearch terms query
     * Consistent with "in ()" in SQL
     */
    TERMS("terms", "多值查询", null),
    /**
     * The Elasticsearch gte query
     * Consistent with ">=" in SQL
     */
    RANGE_GTE("gte", "大于或等于", "range"),
    /**
     * The Elasticsearch lte query
     * Consistent with "<=" in SQL
     */
    RANGE_LTE("lte", "小于或等于", "range"),
    /**
     * The Elasticsearch gt query
     * Consistent with ">" in SQL
     */
    RANGE_GT("gt", "大于", "range"),
    /**
     * The Elasticsearch gt query
     * Consistent with "<" in SQL
     */
    RANGE_LT("lt", "小于", "range"),
    /**
     * The Elasticsearch exists query
     * Consistent with "is not null" in SQL
     */
    EXISTS("exists", "is not null", null),
    /**
     * The Elasticsearch prefix query
     * Consistent with "like XXX%" in SQL
     */
    PREFIX("prefix", "前缀", null),
    /**
     * The Elasticsearch gt query
     * Consistent with "like %XXX%" in SQL
     */
    WILDCARD("wildcard", "通配符", null),
    /**
     * The Elasticsearch REGEXP query
     */
    REGEXP("regexp", "正则", null),
    /**
     * The Elasticsearch match query
     * Word Segmenter-based ElasticCondition
     */
    MATCH("match", "短语匹配", null),
    /**
     * The Elasticsearch match query
     * Word Segmenter-based ElasticCondition
     */
    FUZZY("fuzzy", "", null),
    /**
     * The Elasticsearch match query
     * Word Segmenter-based ElasticCondition
     */
    TYPE("type", "", null),
    /**
     * The Elasticsearch match query
     * Word Segmenter-based ElasticCondition
     */
    IDS("ids", "", null),
    ;

    /**
     * 编码
     */
    @Getter
    private String code;

    /**
     * 描述
     */
    @Getter
    private String desc;

    /**
     * The elasticsearch query type group
     */
    private String group;

}
