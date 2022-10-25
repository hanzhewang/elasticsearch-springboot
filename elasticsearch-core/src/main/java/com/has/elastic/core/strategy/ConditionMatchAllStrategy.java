package com.has.elastic.core.strategy;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.has.elastic.core.bo.ElasticConditionBo;

/**
 * <p>com.has.elastic.core.strategy</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/20
 */
public class ConditionMatchAllStrategy implements ConditionStrategy<String> {

    @Override
    public Query createQuery(ElasticConditionBo condition, String target) {
        MatchAllQuery.Builder builder = new MatchAllQuery.Builder();
        if (condition.getBoost() != null) {
            builder.boost(condition.getBoost());
        }
        return builder.build()._toQuery();
    }
}
