package com.has.elastic.core.strategy;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TypeQuery;
import com.has.elastic.core.bo.ElasticConditionBo;

/**
 * <p>com.has.elastic.core.strategy</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/19
 */
public class ConditionTypeAdapter implements ConditionStrategy<String> {

    public static final ConditionStrategyFactory FACTORY = new ConditionStrategyFactory() {
        @Override
        public ConditionStrategy<String> create() {
            return new ConditionTypeAdapter();
        }
    };

    @Override
    public Query createQuery(ElasticConditionBo condition, String target) {
        TypeQuery.Builder builder = new TypeQuery.Builder();
        if (condition.getBoost() != null) {
            builder.boost(condition.getBoost());
        }
        return builder.value(target)
                .build()
                ._toQuery();
    }


}
