package com.has.elastic.base.annotation.search.geo;

/**
 * <p>com.has.elastic.base.annotation.search.geo</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/23
 */
public @interface GeoDistance {

    String distance() default "200km";

    String distance_type() default "arc";
}
