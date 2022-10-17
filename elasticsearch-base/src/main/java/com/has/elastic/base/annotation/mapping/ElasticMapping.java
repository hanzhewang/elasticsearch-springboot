package com.has.elastic.base.annotation.mapping;

import java.lang.annotation.*;

/**
 * <p>Elasticsearch Mapping Index</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.2
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.TYPE)
public @interface ElasticMapping {

    /**
     * The Elasticsearch mapping index
     *
     * @return
     */
    String index();

    /**
     * The Elasticsearch mapping type
     *
     * @return
     */
    String type();

}