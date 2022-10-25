package com.has.elastic.core.strategy;

import co.elastic.clients.elasticsearch._types.query_dsl.PrefixQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.has.elastic.core.bo.ElasticConditionBo;

/**
 * <p>com.has.elastic.core.strategy</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/19
 */
public class ConditionPrefixStrategy implements ConditionStrategy<String> {

    public static final ConditionStrategyFactory FACTORY = new ConditionStrategyFactory() {
        @Override
        public ConditionStrategy<String> create() {
            return new ConditionPrefixStrategy();
        }
    };

    @Override
    public Query createQuery(ElasticConditionBo condition, String target) {
        PrefixQuery.Builder builder = new PrefixQuery.Builder();
        if (condition.getBoost() != null) {
            builder.boost(condition.getBoost());
        }
        builder.caseInsensitive(condition.getCaseInsensitive());
        return builder.field(condition.getElasticName()).value(String.valueOf(target)).build()._toQuery();
    }
}
