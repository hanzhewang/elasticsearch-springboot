package com.has.elastic.core.strategy;

import co.elastic.clients.elasticsearch._types.query_dsl.ExistsQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.has.elastic.core.bo.ElasticConditionBo;

/**
 * <p>Elasticsearch field value is not null</p>
 * Elasticsearch的属性是否存在
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/19
 * @since 1.8
 */
public class ConditionExistsAdapter implements ConditionStrategy<Boolean> {

    /**
     * Exists
     */
    public static final ConditionStrategyFactory FACTORY = new ConditionStrategyFactory() {
        @Override
        public ConditionStrategy<Boolean> create() {
            return new ConditionExistsAdapter();
        }
    };

    @Override
    public Query createQuery(ElasticConditionBo condition, Boolean target) {
        if (!Boolean.TRUE.equals(target)) {
            return null;
        }
        ExistsQuery.Builder builder = new ExistsQuery.Builder();
        if (condition.getBoost() != null) {
            builder.boost(condition.getBoost());
        }
        return builder.field(condition.getElasticName()).build()._toQuery();
    }

}
