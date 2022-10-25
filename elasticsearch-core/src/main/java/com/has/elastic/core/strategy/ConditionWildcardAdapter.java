package com.has.elastic.core.strategy;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.WildcardQuery;
import com.has.elastic.core.bo.ElasticConditionBo;

/**
 * <p>com.has.elastic.core.strategy</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/19
 */
public class ConditionWildcardAdapter implements ConditionStrategy<String> {

    public static final ConditionStrategyFactory FACTORY = new ConditionStrategyFactory() {
        @Override
        public ConditionStrategy<String> create() {
            return new ConditionWildcardAdapter();
        }
    };

    @Override
    public Query createQuery(ElasticConditionBo condition, String target) {
        WildcardQuery.Builder builder = new WildcardQuery.Builder();
        if (condition.getBoost() != null) {
            builder.boost(condition.getBoost());
        }
        builder.caseInsensitive(condition.getCaseInsensitive());
        // todo 其他配置参数， 需学习
        return builder.field(condition.getElasticName()).value(target).build()._toQuery();
    }
}
