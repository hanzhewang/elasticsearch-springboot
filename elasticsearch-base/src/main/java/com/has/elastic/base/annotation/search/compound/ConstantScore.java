package com.has.elastic.base.annotation.search.compound;

import com.has.elastic.base.constant.ElasticConstant;

import java.lang.annotation.*;

/**
 * <p>Constant sore query</p>
 * Wraps a filter query and returns every matching document with a relevance score equal to the boost parameter value.
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/23
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConstantScore {

    /**
     * Floating point number used as the constant relevance score for every document matching the filter query.
     * Defaults to 1.0.
     *
     * @return
     */
    float boost() default 1.0F;

    /**
     * Elasticsearch bool type group
     *
     * @return
     */
    String group() default ElasticConstant.DEFAULT_GROUP;
}
