package com.has.elastic.core.bo;

import com.google.common.collect.Maps;
import com.google.gson.annotations.Since;
import com.has.elastic.base.annotation.search.ElasticSort;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * <p>Elasticsearch Document business object</p>
 * 文档业务对象
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/17
 * @since 1.8
 */
@Slf4j
@Data
public class ElasticDocumentBo {

    /**
     * Elasticsearch document mapping index
     * 文档索引: index
     */
    private String index;

    /**
     * Elasticsearch document mapping type
     * 文档索引: type
     */
    @Since(6)
    private String type;

    /**
     * Elasticsearch document nested name
     * 文档：嵌套对象名称
     */
    private String nested;

    /**
     * Elasticsearch document uuid
     * 文档：UUID键
     */
    private List<ElasticUuidBo> uuids;

    /**
     * Elasticsearch document version
     * 文档：版本控制器
     */
    private ElasticVersionBo version;

    /**
     * Elasticsearch document query condition
     * 文档：查询条件
     */
    private Map<String, ElasticConditionBo> conditionMap = Maps.newHashMap();

    /**
     * Elasticsearch document query sort
     * 文档：查询排序
     */
    private List<ElasticSort> sorts;
}
