package com.has.elastic.core.bo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * <p>Elasticsearch mapping Index</p>
 * 索引index
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/13
 */
@Slf4j
@Data
public class ElasticIndexBo implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3212128509325363120L;

    /**
     * index
     * 索引index
     */
    private String index;

    /**
     * type
     * 索引type
     */
    private String type;

}
