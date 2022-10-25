package com.has.elastic.base.annotation.search;

/**
 * <p>com.has.elastic.base.annotation.search</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/21
 */
public @interface ElasticTermsSet {

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
     * (Optional, string) Numeric field containing the number of matching terms required to return a document.
     * @return
     */
    String minimumShouldMatchField() default "";

    /**
     * (Optional, string) Numeric field containing the number of matching terms required to return a document.
     * @return
     */
    String minimumShouldMatchScript() default "";
}
