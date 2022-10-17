package com.has.elastic.base.annotation.document;

/**
 * <p>Elasticsearch Document UUid</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/13
 */
public @interface ElasticUuid {

    /**
     * uuid
     *
     * @return
     */
    String name() default "";

    /**
     * 顺序
     *
     * @return
     */
    int order();
}
