package com.has.elastic.core.strategy;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.json.JsonData;
import com.has.elastic.core.bo.ElasticConditionBo;

/**
 * <p>com.has.elastic.core.strategy</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/19
 */
public class ConditionRangeStrategy implements ConditionStrategy<Object> {

    public static final ConditionStrategyFactory FACTORY = new ConditionStrategyFactory() {
        @Override
        public ConditionStrategy<Object> create() {
            return new ConditionRangeStrategy();
        }
    };

    @Override
    public Query createQuery(ElasticConditionBo condition, Object target) {
        RangeQuery.Builder builder = new RangeQuery.Builder();
        if (condition.getBoost() != null) {
            builder.boost(condition.getBoost());
        }
        // todo builder.format().timeZone()
        setValue(builder, condition, target);
        return builder.field(condition.getElasticName()).build()._toQuery();
    }

    private void setValue(RangeQuery.Builder builder, ElasticConditionBo condition, Object target) {
        JsonData jsonData = JsonData.of(target);
        switch (condition.getCondition()) {
            case RANGE_LT:
                builder.lt(jsonData);
                break;
            case RANGE_LTE:
                builder.lte(jsonData);
                break;
            case RANGE_GT:
                builder.gt(jsonData);
                break;
            case RANGE_GTE:
                builder.gte(jsonData);
                break;
            default:
        }
    }

}
