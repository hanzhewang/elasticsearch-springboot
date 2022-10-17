package com.has.elastic.base.enums.search;

import lombok.AllArgsConstructor;

/**
 * <p>The Elasticsearch query sort mode enum</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.2
 */
@AllArgsConstructor
public enum SortMode {
    /**
     * static mode
     */
    STATICS,
    /**
     * dynamic mode
     */
    DYNAMIC;
}
