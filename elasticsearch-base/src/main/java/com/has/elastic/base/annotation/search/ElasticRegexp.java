package com.has.elastic.base.annotation.search;

import java.lang.annotation.*;

/**
 * <p>com.has.elastic.base.annotation.search</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/21
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.FIELD)
public @interface ElasticRegexp {

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


    /**
     * (Optional, string) Enables optional operators for the regular expression.
     * For valid values and more information, see Regular expression syntax.
     *
     * @return
     */
    String flags() default "";


    /**
     * (Optional, integer) Maximum number of automaton states required for the query. Default is 10000.
     * <p>
     * Elasticsearch uses Apache Lucene internally to parse regular expressions. Lucene converts each regular expression to a finite automaton containing a number of determinized states.
     * <p>
     * You can use this parameter to prevent that conversion from unintentionally consuming too many resources. You may need to increase this limit to run complex regular expressions.
     *
     * @return
     */
    int maxDeterminizedStates() default 10000;

    /**
     * (Optional, string) Method used to rewrite the query.
     * For valid values and more information, see the rewrite parameter.
     *
     * @return
     */
    String rewrite() default "";
}
