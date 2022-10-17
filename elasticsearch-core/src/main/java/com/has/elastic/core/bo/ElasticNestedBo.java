package com.has.elastic.core.bo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * <p>Elasticsearch 嵌套对象</p>
 * 嵌套对象
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/13
 */
@Slf4j
@Data
public class ElasticNestedBo implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3161338197076157531L;

    /**
     * Elasticsearch Nested属性名
     */
    private String elasticNested;

    /**
     * index
     */
    private String index;

    /**
     * type
     */
    private String type;

}
