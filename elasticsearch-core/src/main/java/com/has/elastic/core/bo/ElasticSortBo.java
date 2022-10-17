package com.has.elastic.core.bo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * <p>Elasticsearch 排序</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/13
 */
@Slf4j
@Data
public class ElasticSortBo implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6552755202646913382L;

    /**
     * 属性
     */
    private Field field;

    /**
     * Elasticsearch 属性名
     */
    private String elasticName;

    /**
     * 属性排序
     */
    private String orderBy;
}
