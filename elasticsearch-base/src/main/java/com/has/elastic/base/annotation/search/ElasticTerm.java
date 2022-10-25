package com.has.elastic.base.annotation.search;

import com.has.elastic.base.enums.mapping.ElasticFieldType;

import java.lang.annotation.*;

/**
 * <p>com.has.elastic.base.annotation.search</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/20
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.FIELD)
public @interface ElasticTerm {

    /**
     * Elasticsearch 属性字段（默认当前字段名）
     *
     * @return
     */
    String name() default "";

    /**
     * Elasticsearch 嵌套对象属性名
     *
     * @return
     */
    String nested() default "";

    /**
     * 开启权重
     *
     * @return
     */
    float boost() default 2.0F;

    /**
     * 不区分大小写
     *
     * @return
     */
    boolean caseInsensitive() default false;

}
