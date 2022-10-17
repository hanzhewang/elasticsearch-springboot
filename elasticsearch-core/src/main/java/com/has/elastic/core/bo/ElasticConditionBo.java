package com.has.elastic.core.bo;

import com.has.elastic.base.enums.search.QueryBoolType;
import com.has.elastic.base.enums.search.QueryConditionType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * <p>Elasticsearch query condition business object</p>
 * 查询条件业务对象
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/13
 */
@Slf4j
@Data
public class ElasticConditionBo implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2734130116007133619L;

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
     * 条件
     */
    private QueryConditionType condition;

    /**
     * 连接
     */
    private QueryBoolType bool;

    /**
     * 分组
     */
    private String boolGroup;

    /**
     * 属性值
     */
    private Object value;
}
