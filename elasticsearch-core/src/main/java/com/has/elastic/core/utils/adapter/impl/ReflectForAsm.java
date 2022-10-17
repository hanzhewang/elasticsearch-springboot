package com.has.elastic.core.utils.adapter.impl;


import com.jd.kos.commons.elasticsearch.utils.adapter.ReflectAdapter;
import lombok.extern.log4j.Log4j2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * <p>The asm reflect adapter</p>
 * TODO
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.3
 */
@Deprecated
@Log4j2
public class ReflectForAsm implements ReflectAdapter {

    @Override
    public Object getFieldValue(Object target, String fieldName) {
        return null;
    }

    @Override
    public void setFieldValue(Object target, String fieldName, Class fieldType, Object fieldValue) {

    }

    @Override
    public Type[] getDeclaredFieldTypes(Class<?> clazz) {
        return new Type[0];
    }

    @Override
    public Field[] getDeclaredFields(Class<?> clazz) {
        return new Field[0];
    }

    @Override
    public Annotation[] getAnnotationsByClass(Class<?> clazz) {
        return new Annotation[0];
    }

    @Override
    public Annotation[] getAnnotationsByField(Field field) {
        return new Annotation[0];
    }

    @Override
    public Map<String, Object> getFieldsValueMap(Object target) {
        return null;
    }

    @Override
    public Map<String, Annotation[]> getFieldsAnnotationsMap(Object target) {
        return null;
    }
}
