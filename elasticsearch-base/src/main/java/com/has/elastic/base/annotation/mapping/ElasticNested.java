package com.has.elastic.base.annotation.mapping;


import java.lang.annotation.*;

/**
 * <p>Elasticsearch Mapping nested</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.2
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.TYPE)
public @interface ElasticNested {
    /**
     * Nested 属性名
     *
     * @return
     */
    String name();

    /**
     * Elasticsearch Index
     *
     * @return
     */
    ElasticMapping index();

}