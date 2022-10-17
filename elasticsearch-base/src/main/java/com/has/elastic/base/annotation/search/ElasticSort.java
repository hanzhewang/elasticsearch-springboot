package com.has.elastic.base.annotation.search;


import java.lang.annotation.*;

/**
 * <p>The Elasticsearch query sort annotation</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.2
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.TYPE)
public @interface ElasticSort {

    /**
     * The query sort property name
     * 1. mode == SortMode.STATICS
     * sort property name is elasticsearch mapping property name
     * 2. mode == SortMode.DYNAMIC
     * sort property name is java entity property name
     *
     * @return
     */
    String orderBy() default "";
}