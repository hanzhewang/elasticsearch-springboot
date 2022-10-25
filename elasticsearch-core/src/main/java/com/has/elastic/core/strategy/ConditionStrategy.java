package com.has.elastic.core.strategy;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.has.elastic.core.bo.ElasticConditionBo;

/**
 * <p>com.has.elastic.core.adapter</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/18
 */
public interface ConditionStrategy<T> {

    Query createQuery(ElasticConditionBo condition, T target);

}
