package com.has.elastic.core.utils.adapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * <p>The reflect parser adapter interface</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.3
 */
public interface ReflectAdapter {

    /**
     * The select object all field value
     *
     * @param target
     * @param fieldName filed name
     * @return
     */
    Object getFieldValue(Object target, String fieldName);

    /**
     * The set object field value
     *
     * @param target
     * @param fieldName
     * @param fieldType
     * @param fieldValue
     */
    void setFieldValue(Object target, String fieldName, Class fieldType, Object fieldValue);

    /**
     * The query object all field type array
     *
     * @param clazz object class
     * @return
     */
    Type[] getDeclaredFieldTypes(Class<?> clazz);

    /**
     * The query object public field info
     *
     * @param clazz
     * @return
     */
    Field[] getDeclaredFields(Class<?> clazz);

    /**
     * The query object class annotation
     *
     * @param clazz
     * @return
     */
    Annotation[] getAnnotationsByClass(Class<?> clazz);

    /**
     * The query object all field annotation
     *
     * @param field
     * @return
     */
    Annotation[] getAnnotationsByField(Field field);

    /**
     * The query object all field value map
     *
     * @param target
     * @return
     */
    Map<String, Object> getFieldsValueMap(Object target);

    /**
     * The query object all field annotation map
     *
     * @param target
     * @return
     */
    Map<String, Annotation[]> getFieldsAnnotationsMap(Object target);

}
