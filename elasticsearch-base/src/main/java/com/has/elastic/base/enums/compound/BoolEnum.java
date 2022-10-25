package com.has.elastic.base.enums.compound;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>com.has.elastic.base.enums.compound</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/23
 */
@Getter
@AllArgsConstructor
public enum BoolEnum {

    /**
     * The Elasticsearch term query
     * Consistent with "and" in SQL
     */
    must,
    /**
     * The Elasticsearch term query
     * Consistent with "or" in SQL
     */
    should,
    /**
     * The Elasticsearch term query
     * Consistent with "!=s" in SQL
     */
    must_not,

    /**
     * The Elasticsearch filter
     */
    filter;
}
