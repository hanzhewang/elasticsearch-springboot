package com.has.elastic.core.strategy;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsSetQuery;
import com.has.elastic.core.bo.ElasticConditionBo;

import java.util.List;

/**
 * <p>com.has.elastic.core.strategy</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/21
 */
public class ElasticTermsSetStrategy implements ConditionStrategy<List<String>>{

    @Override
    public Query createQuery(ElasticConditionBo condition, List<String> target) {
        TermsSetQuery.Builder builder = new TermsSetQuery.Builder();
        if (condition.getBoost() != null) {
            builder.boost(condition.getBoost());
        }
        return builder.field(condition.getElasticName()).terms(target).build()._toQuery();
    }
}
