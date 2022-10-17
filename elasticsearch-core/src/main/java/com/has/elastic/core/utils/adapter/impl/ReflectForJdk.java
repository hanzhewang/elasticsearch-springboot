package com.has.elastic.core.utils.adapter.impl;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jd.kos.commons.elasticsearch.utils.adapter.ReflectAdapter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.commons.text.WordUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * <p>The jdk reflect adapter</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.3
 */
@Log4j2
public class ReflectForJdk implements ReflectAdapter {

    /**
     * The object property get method prefix
     */
    private final String PROPERTY_GET_METHOD_PREFIX = "get";

    /**
     * The object property set method prefix
     */
    private final String PROPERTY_SET_METHOD_PREFIX = "set";

    @Override
    public Object getFieldValue(Object target, String fieldName) {
        try {
            return getFieldValueByMethod(target, fieldName, true);
        } catch (Throwable ex) {
            return getFieldValueByFiled(target, fieldName, false);
        }
    }

    private Object getFieldValueByFiled(Object target, String fieldName, boolean isThrow) {
        if (target == null || StringUtils.isBlank(fieldName)) {
            return null;
        }
        Class clazz = target.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            if (!Modifier.isPublic(field.getModifiers())) {
                field.setAccessible(true);
            }
            return field.get(target);
        } catch (Throwable ex) {
            if (!"serialVersionUID".equals(fieldName)) {
                log.error("ReflectForJdk.getFieldValueByFiled#fieldName:{},error:{}", fieldName, ex);
            }
            if (isThrow) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    private Object getFieldValueByMethod(Object target, String fieldName, boolean isThrow) {
        if (target == null || StringUtils.isBlank(fieldName)) {
            return null;
        }
        Class clazz = target.getClass();
        String methodName = StringUtils.join(PROPERTY_GET_METHOD_PREFIX, WordUtils.capitalize(fieldName));
        try {
            Method method = MethodUtils.getAccessibleMethod(clazz, methodName);
            if (!Modifier.isPublic(method.getModifiers())) {
                method.setAccessible(true);
            }
            return method.invoke(target);
        } catch (Throwable ex) {
            if (!"serialVersionUID".equals(fieldName)) {
                log.error("ReflectForJdk.getFieldValueByMethod#methodName:{},error:{}", methodName, ex);
            }
            if (isThrow) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    @Override
    public void setFieldValue(Object target, String fieldName, Class fieldType, Object fieldValue) {
        try {
            setFieldValueByMethod(target, fieldName, fieldType, fieldValue, true);
        } catch (Exception ex) {
            setFieldValueByField(target, fieldName, fieldValue, false);
        }
    }

    private void setFieldValueByMethod(Object target, String fieldName, Class fieldType, Object fieldValue, boolean isThrow) {
        if (target == null || StringUtils.isBlank(fieldName)) {
            return;
        }
        if (fieldValue != null && !fieldType.isAssignableFrom(fieldValue.getClass())) {
            return;
        }
        Class clazz = target.getClass();
        String methodName = StringUtils.join(PROPERTY_SET_METHOD_PREFIX, WordUtils.capitalize(fieldName));
        try {
            Method method = MethodUtils.getAccessibleMethod(clazz, methodName, fieldType);
            if (!Modifier.isPublic(method.getModifiers())) {
                method.setAccessible(true);
            }
            method.invoke(target, fieldValue);
        } catch (Throwable ex) {
            log.info("ReflectForJdk.setFieldValueByField#methodName:{},error:{}", methodName, ex);
            if (isThrow) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void setFieldValueByField(Object target, String fieldName, Object fieldValue, boolean isThrow) {
        if (target == null || StringUtils.isBlank(fieldName)) {
            return;
        }
        Class clazz = target.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            if (!Modifier.isPublic(field.getModifiers())) {
                field.setAccessible(true);
            }
            field.set(target, fieldValue);
        } catch (Throwable ex) {
            log.info("ReflectForJdk.setFieldValueByMethod#fieldName:{},error:{}", fieldName, ex);
            if (isThrow) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public Field[] getDeclaredFields(Class<?> clazz) {
        Field[] fields = FieldUtils.getAllFields(clazz);
        Field[] fieldsWithOutThis = new Field[fields.length];
        int j = 0;
        for (int i = 0; i < fields.length; ++i) {
            if (!fields[i].getName().equals("this$0")) {
                fieldsWithOutThis[j] = fields[i];
                ++j;
            }
        }
        return fieldsWithOutThis;
    }

    @Override
    public Type[] getDeclaredFieldTypes(Class<?> clazz) {
        Set<Type> types = foreachField(clazz, Sets.newHashSet(), (set, field) -> {
            set.add(field.getGenericType());
        });
        return types.toArray(new Type[types.size()]);
    }

    @Override
    public Map<String, Annotation[]> getFieldsAnnotationsMap(Object target) {
        return foreachField(target.getClass(), Maps.newHashMap(), (map, field) -> {
            Annotation[] annos = getAnnotationsByField(field);
            map.put(field.getName(), annos);
        });
    }

    @Override
    public Annotation[] getAnnotationsByClass(Class<?> clazz) {
        return clazz.getAnnotations();
    }

    public <T extends Annotation> T getAnnotationsByClass(Class<?> clazz, Class<T> anno) {
        return clazz.getAnnotation(anno);
    }

    @Override
    public Annotation[] getAnnotationsByField(Field field) {
        return field.getAnnotations();
    }

    public <T extends Annotation> T getAnnotationsByField(Field field, Class<T> anno) {
        return field.getAnnotation(anno);
    }

    @Override
    public Map<String, Object> getFieldsValueMap(Object target) {
        return foreachField(target.getClass(), Maps.newHashMap(), (map, field) -> {
            String fieldName = field.getName();
            Object value = getFieldValue(target, fieldName);
            if (value != null) {
                map.put(fieldName, value);
            }
        });
    }

    /**
     * common for method
     *
     * @param clazz
     * @param m
     * @param consumer
     * @param <M>
     * @return
     */
    private <M> M foreachField(Class<?> clazz, M m, BiConsumer<M, Field> consumer) {
        Field[] fields = getDeclaredFields(clazz);
        int fieldsLen = fields.length;
        for (int fieldsIndex = 0; fieldsIndex < fieldsLen; fieldsIndex++) {
            Field field = fields[fieldsIndex];
            consumer.accept(m, field);
        }
        return m;
    }
}
