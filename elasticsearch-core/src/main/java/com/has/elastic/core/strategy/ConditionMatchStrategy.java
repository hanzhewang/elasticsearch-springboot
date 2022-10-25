package com.has.elastic.core.strategy;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.has.elastic.core.bo.ElasticConditionBo;

/**
 * <p>com.has.elastic.core.strategy</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/19
 */
public class ConditionMatchStrategy implements ConditionStrategy<Object> {

    public static final ConditionStrategyFactory FACTORY = new ConditionStrategyFactory() {
        @Override
        public ConditionStrategy<Object> create() {
            return new ConditionMatchStrategy();
        }
    };

    @Override
    public Query createQuery(ElasticConditionBo condition, Object target) {
        MatchQuery.Builder builder = new MatchQuery.Builder();
        if (condition.getBoost() != null) {
            builder.boost(condition.getBoost());
        }
        // todo 其他配置参数， 需学习 builder.analyzer().autoGenerateSynonymsPhraseQuery().minimumShouldMatch().fuzziness().prefixLength()
        return builder.field(condition.getElasticName()).query(this.getFieldValue(target)).build()._toQuery();
    }

    public FieldValue getFieldValue(Object target) {
        FieldValue.Builder builder = new FieldValue.Builder();
        if (target == null) {
            builder.nullValue();
        }
        if (target instanceof Number && target instanceof Comparable) {
            builder.doubleValue(Double.valueOf(target.toString()));
        } else if (target instanceof Number) {
            builder.longValue(Long.valueOf(target.toString()));
        }
        if (target instanceof String) {
            builder.stringValue(String.valueOf(target));
        }
        if (target instanceof Boolean) {
            builder.booleanValue((Boolean) target);
        }
        return builder.build();
    }
}