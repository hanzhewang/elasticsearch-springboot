package com.has.elastic.core.strategy;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RegexpQuery;
import com.has.elastic.core.bo.ElasticConditionBo;

/**
 * <p>com.has.elastic.core.strategy</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/19
 */
public class ConditionRegexpAdapter implements ConditionStrategy<String> {

    public static final ConditionStrategyFactory FACTORY = new ConditionStrategyFactory() {
        @Override
        public ConditionStrategy<String> create() {
            return new ConditionRegexpAdapter();
        }
    };

    @Override
    public Query createQuery(ElasticConditionBo condition, String target) {
        RegexpQuery.Builder builder = new RegexpQuery.Builder();
        if (condition.getBoost() != null) {
            builder.boost(condition.getBoost());
        }
        // todo  builder.flags().maxDeterminizedStates()
        builder.caseInsensitive(condition.getCaseInsensitive());
        return builder.field(condition.getElasticName()).value(target).build()._toQuery();
    }
}
