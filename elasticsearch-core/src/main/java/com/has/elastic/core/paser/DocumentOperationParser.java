package com.has.elastic.core.paser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.has.elastic.core.adapter.ElasticAdapterFactory;
import com.has.elastic.core.bo.ElasticDocumentBo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>Elasticsearch 文档操作解析器</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/17
 */
public class DocumentOperationParser {

    private Map<Class<?>, Object> classObjectMap = Maps.newConcurrentMap();

    private List<ElasticAdapterFactory> factories = Lists.newCopyOnWriteArrayList();

    public void addElasticAdapterFactory(ElasticAdapterFactory factory) {
        factories.add(factory);
    }

    public <T extends Serializable> ElasticDocumentBo parser(T entity) {
        return null;
    }

}
