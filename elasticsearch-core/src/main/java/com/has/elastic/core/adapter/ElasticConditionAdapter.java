package com.has.elastic.core.adapter;

import com.has.elastic.base.annotation.search.ElasticCondition;
import com.has.elastic.core.bo.ElasticConditionBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * <p>Elasticsearch 查询条件适配器</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/13
 * @since jdk1.8 and elasticsearch6、7
 */
@Slf4j
public class ElasticConditionAdapter implements ElasticAdapter<ElasticCondition, ElasticConditionBo> {

    /**
     * Elasticsearch 查询条件适配工厂
     */
    public static final ElasticAdapterFactory FACTORY = new ElasticAdapterFactory() {
        @Override
        public ElasticAdapter create() {
            return new ElasticConditionAdapter();
        }
    };

    @Override
    public ElasticConditionBo read(Field field, ElasticCondition condition, Object value) {
        if (value == null) {
            return null;
        }
        ElasticConditionBo bo = new ElasticConditionBo();
        bo.setField(field);
        bo.setElasticName(condition.name());
        if (StringUtils.isNoneBlank(condition.name())) {
            bo.setElasticName(condition.name());
        }
        bo.setElasticNested(condition.nested());
        bo.setCondition(condition.condition());
        bo.setBool(condition.bool());
        bo.setBoolGroup(condition.boolGroup());
        bo.setValue(value);
        return bo;
    }
}
