package com.has.elastic.base.enums.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>elasticsearch query combination method </p>
 *
 * @author wanghanzhe
 * @version 6.0.1
 * @date 2018.10.16
 * @since JDK 1.8 and Elasticsearch 6.x
 */
@AllArgsConstructor
public enum QueryBoolType {

    /**
     * The Elasticsearch term query
     * Consistent with "and" in SQL
     */
    MUST("must", "and"),
    /**
     * The Elasticsearch term query
     * Consistent with "or" in SQL
     */
    SHOULD("should", "or"),
    /**
     * The Elasticsearch term query
     * Consistent with "!=s" in SQL
     */
    MUST_NOT("must_not", "!"),

    /**
     * The Elasticsearch filter
     */
    FILTER("filter", "");

    /**
     * query bool code
     */
    @Getter
    private String code;

    /**
     * query bool code desc
     */
    @Getter
    private String desc;

    /**
     * value to QueryBoolType
     *
     * @param code
     * @return
     */
    public static QueryBoolType valueof(String code) {
        QueryBoolType[] queryBoolTypes = QueryBoolType.values();
        for (QueryBoolType queryBoolType : queryBoolTypes) {
            if (queryBoolType.getCode().equals(code)) {
                return queryBoolType;
            }
        }
        return null;
    }

    /**
     * to string
     *
     * @return
     */
    @Override
    public String toString() {
        return this.code;
    }

}
