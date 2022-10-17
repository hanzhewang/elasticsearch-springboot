package com.has.elastic.base.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>elasticsearch data type enums</p>
 *
 * @author wanghanzhe
 * @version 6.0.1
 * @date 2018.10.16
 * @since JDK 1.8 Elasticsearch 6.x
 */
@AllArgsConstructor
public enum JavaType {
    LONG(Long.class, "long"),
    INTEGER(Integer.class, "integer"),
    SHORT(Integer.class, "integer"),
    FLOAT(Float.class, "double"),
    DOUBLE(Double.class, "double"),
    BIG_DECIMAL(BigDecimal.class, "double"),
    BOOLEAN(Boolean.class, "boolean"),
    BYTE(Byte.class, "byte"),
    CHAR(Character.class, "keyword"),
    STRING(String.class, "keyword"),
    DATE(Date.class, "date"),
    ;

    /**
     * 编码
     */
    @Getter
    private Class clazz;
    /**
     * 编码
     */
    @Getter
    private String code;

    /**
     * value to ObjectType
     *
     * @param clazz
     * @return
     */
    public static JavaType valueof(Class clazz) {
        JavaType[] objectTypes = JavaType.values();
        for (JavaType objectType : objectTypes) {
            if (objectType.getClazz().equals(clazz)) {
                return objectType;
            }
        }
        return null;
    }

}
