package com.has.elastic.core.bo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * <p>Elasticsearch 版本号</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/13
 */
@Slf4j
@Data
public class ElasticVersionBo implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6133842370588414397L;

    /**
     * 属性
     */
    private Field field;

    /**
     * Elasticsearch 属性名
     */
    private String elasticName;

    /**
     * Elasticsearch Nested属性名
     */
    private String elasticNested;

    /**
     * 属性值
     */
    private Object value;
}
