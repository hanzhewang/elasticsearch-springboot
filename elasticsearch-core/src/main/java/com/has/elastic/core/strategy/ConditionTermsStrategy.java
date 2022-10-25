package com.has.elastic.core.strategy;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQueryField;
import com.google.common.collect.Lists;
import com.has.elastic.core.bo.ElasticConditionBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <p></p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/19
 */
@Slf4j
public class ConditionTermsStrategy implements ConditionStrategy<Collection> {

    public static final ConditionStrategyFactory FACTORY = new ConditionStrategyFactory() {
        @Override
        public ConditionStrategy create() {
            return new ConditionTermsStrategy();
        }
    };

    @Override
    public Query createQuery(ElasticConditionBo condition, Collection target) {
        TermsQuery.Builder builder = new TermsQuery.Builder();
        if (condition.getBoost() != null) {
            builder.boost(condition.getBoost());
        }
        TermsQueryField field = this.getTermsQueryField(target);
        return builder.field(condition.getElasticName()).terms(field).build()._toQuery();
    }

    /**
     * Condition parameter convert to TermsQueryField
     *
     * @param target
     * @param <T>
     * @return
     */
    private TermsQueryField getTermsQueryField(Collection target) {
        TermsQueryField.Builder builder = new TermsQueryField.Builder();
        if (CollectionUtils.isEmpty(target)) {
            log.debug("terms query condition is null");
            return null;
        }
        List<FieldValue> fieldValues = Lists.newArrayList();
        FieldValue fieldValue = null;
        for (Object object : target) {
            fieldValue = this.getFieldValue(object);
            if (fieldValue != null) {
                fieldValues.add(fieldValue);
            }
        }
        return builder.value(fieldValues).build();
    }

    /**
     * @param target
     * @param <T>
     * @return
     */
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
