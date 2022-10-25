package com.has.elastic.core.strategy;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import com.has.elastic.core.bo.ElasticConditionBo;

import java.util.Date;

/**
 * <p>com.has.elastic.core.adapter</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/18
 */
public class ConditionTermStrategy implements ConditionStrategy<Object> {

    public static final ConditionStrategyFactory FACTORY = new ConditionStrategyFactory() {
        @Override
        public ConditionStrategy create() {
            return new ConditionTermStrategy();
        }
    };

    @Override
    public Query createQuery(ElasticConditionBo condition, Object target) {
        TermQuery.Builder builder = new TermQuery.Builder();
        if (condition.getBoost() != null) {
            builder.boost(condition.getBoost());
        }
        builder.caseInsensitive(condition.getCaseInsensitive());
        return builder.field(condition.getElasticName()).value(this.getFieldValue(target)).build()._toQuery();
    }

    public FieldValue getFieldValue(Object target) {
        FieldValue.Builder builder = new FieldValue.Builder();
        if (target == null) {
            return builder.nullValue().build();
        }
        if (target instanceof Date) {
            Date date = (Date) target;
            return builder.longValue(date.getTime()).build();
        }
        if (target instanceof Boolean) {
            return builder.booleanValue((Boolean) target).build();
        }
        if (target instanceof Short || target instanceof Integer || target instanceof Long) {
            return builder.longValue(Long.valueOf(target.toString())).build();
        }
        if (target instanceof Number) {
            return builder.doubleValue(Double.valueOf(target.toString())).build();
        }
        return builder.stringValue(String.valueOf(target)).build();
    }
}
