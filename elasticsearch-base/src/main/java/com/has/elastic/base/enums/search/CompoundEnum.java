package com.has.elastic.base.enums.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>com.has.elastic.base.enums.search</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/23
 */
@Getter
@AllArgsConstructor
public enum CompoundEnum {

    must("bool"),

    must_not("bool"),

    should("bool"),

    filter("bool"),


    positive("boosting"),

    // negative_boost
    negative("boosting"),

    // boost
    filter("constant_score"),

    // tie_breaker
    queries("dis_max"),
    queries("function_score"),

    ;

    /**
     *
     */
    private String compound;
}
