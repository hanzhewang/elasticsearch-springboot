package com.has.elastic.core.adapter;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * <p>Elasticsearch适配器</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/12
 * @since jdk1.8 and elasticsearch6、7
 */
public interface ElasticAdapter<A extends Annotation, T extends Serializable> {

    /**
     * 读取
     *
     * @param field
     * @param annotation
     * @param value
     * @return
     */
    T read(Field field, A annotation, Object value);

}
