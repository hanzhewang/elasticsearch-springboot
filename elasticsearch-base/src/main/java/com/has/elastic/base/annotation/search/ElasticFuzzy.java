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
public @interface ElasticFuzzy {

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
     * (Optional, string) Maximum edit distance allowed for matching. See Fuzziness for valid values and more information.
     * @return
     */
    String fuzziness() default "";

    /**
     * (Optional, integer) Number of beginning characters left unchanged when creating expansions. Defaults to 0.
     * @return
     */
    int prefixLength() default 0;

    /**
     * (Optional, Boolean) Indicates whether edits include transpositions of two adjacent characters (ab → ba). Defaults to true.
     * @return
     */
    boolean transpositions() default false;


    /**
     * (Optional, integer) Maximum number of variations created. Defaults to 50.
     * @return
     */
    int maxExpansions() default 50;

    /**
     * (Optional, string) Method used to rewrite the query. For valid values and more information, see the rewrite parameter.
     * @return
     */
    String rewrite() default "";
}
