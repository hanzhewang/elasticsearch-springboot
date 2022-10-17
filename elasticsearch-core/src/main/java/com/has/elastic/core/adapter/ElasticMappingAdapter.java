package com.has.elastic.core.adapter;

import com.has.elastic.base.annotation.mapping.ElasticMapping;
import com.has.elastic.core.bo.ElasticIndexBo;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * <p>Elasticsearch Mapping 序列化适配器</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/12
 * @since jdk1.8 and elasticsearch6、7
 */
@Slf4j
public class ElasticMappingAdapter implements ElasticAdapter<ElasticMapping, ElasticIndexBo> {

    /**
     * Elasticsearch Mapping 序列化适配工厂
     */
    public static final ElasticAdapterFactory FACTORY = new ElasticAdapterFactory() {
        @Override
        public ElasticMappingAdapter create() {
            return new ElasticMappingAdapter();
        }
    };

    @Override
    public ElasticIndexBo read(Field field, ElasticMapping mapping, Object value) {
        ElasticIndexBo bo = new ElasticIndexBo();
        bo.setIndex(mapping.index());
        bo.setType(mapping.type());
        return bo;
    }

}
