package com.has.elastic.base.annotation.search;

/**
 * <p>com.has.elastic.base.annotation.search</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/23
 */
public @interface ElasticCompound {

    String ss() default "";

    String STRING() default "bool";
}
