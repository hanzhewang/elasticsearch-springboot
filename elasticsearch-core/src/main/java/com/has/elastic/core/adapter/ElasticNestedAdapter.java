package com.has.elastic.core.adapter;

import com.has.elastic.base.annotation.mapping.ElasticNested;
import com.has.elastic.core.bo.ElasticNestedBo;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * <p>Elasticsearch Nested 序列化适配器</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/13
 * @since jdk1.8 and elasticsearch6、7
 */
@Slf4j
public class ElasticNestedAdapter implements ElasticAdapter<ElasticNested, ElasticNestedBo> {

    /**
     * Elasticsearch Nested 工厂
     */
    public static final ElasticAdapterFactory FACTORY = new ElasticAdapterFactory() {
        @Override
        public ElasticNestedAdapter create() {
            return new ElasticNestedAdapter();
        }
    };

    @Override
    public ElasticNestedBo read(Field field, ElasticNested nested, Object value) {
        ElasticNestedBo bo = new ElasticNestedBo();
        bo.setElasticNested(nested.name());
        bo.setIndex(nested.index().index());
        bo.setType(nested.index().type());
        return bo;
    }
}