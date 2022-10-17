package com.has.elastic.core.bo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * <p>Elasticsearch UUID</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/13
 */
@Slf4j
@Data
public class ElasticUuidBo implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6873798687316235160L;

    /**
     * 属性
     */
    private Field field;

    /**
     * Elasticsearch 属性名
     */
    private String elasticName;

    /**
     * 属性值
     */
    private Object value;

    /**
     * 排序
     */
    private Integer order;
}
