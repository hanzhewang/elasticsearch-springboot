package com.has.elastic.core.strategy;

import co.elastic.clients.elasticsearch._types.query_dsl.FuzzyQuery;
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
public class ConditionFuzzyAdapter implements ConditionStrategy<String> {

    public static final ConditionStrategyFactory FACTORY = new ConditionStrategyFactory() {
        @Override
        public ConditionStrategy<String> create() {
            return new ConditionFuzzyAdapter();
        }
    };

    @Override
    public Query createQuery(ElasticConditionBo condition, String target) {
        FuzzyQuery.Builder builder = new FuzzyQuery.Builder();
        if (condition.getBoost() != null) {
            builder.boost(condition.getBoost());
        }
        // todo builder.prefixLength().maxExpansions()
        return builder.field(condition.getElasticName()).fuzziness(target).build()._toQuery();
    }
}
