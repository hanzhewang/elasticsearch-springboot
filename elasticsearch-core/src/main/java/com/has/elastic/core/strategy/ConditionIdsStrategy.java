package com.has.elastic.core.strategy;

import co.elastic.clients.elasticsearch._types.query_dsl.IdsQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.has.elastic.core.bo.ElasticConditionBo;

import java.util.List;

/**
 * <p>com.has.elastic.core.strategy</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/19
 */
public class ConditionIdsStrategy implements ConditionStrategy<List<String>> {

    public static final ConditionStrategyFactory FACTORY = new ConditionStrategyFactory() {
        @Override
        public ConditionStrategy<List<String>> create() {
            return new ConditionIdsStrategy();
        }
    };

    @Override
    public Query createQuery(ElasticConditionBo condition, List<String> target) {
        IdsQuery.Builder builder = new IdsQuery.Builder();
        if (condition.getBoost() != null) {
            builder.boost(condition.getBoost());
        }
        builder.values(target);
        return builder.values(target).build()._toQuery();
    }
}
