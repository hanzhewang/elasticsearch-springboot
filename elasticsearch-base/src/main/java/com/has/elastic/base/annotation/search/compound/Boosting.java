package com.has.elastic.base.annotation.search.compound;

import com.has.elastic.base.constant.ElasticConstant;
import com.has.elastic.base.enums.compound.BoostingEnum;

import java.lang.annotation.*;

/**
 * <p>Boosting query</p>
 * Returns documents matching a positive query while reducing the relevance score of documents that also match a negative query.
 * <p>
 * You can use the boosting query to demote certain documents without excluding them from the search results.
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/23
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Boosting {

    /**
     * positive
     * (Required, query object) Query you wish to run. Any returned documents must match this query.
     * negative
     * (Required, query object) Query used to decrease the relevance score of matching documents.
     * If a returned document matches the positive query and this query, the boosting query calculates the final relevance score for the document as follows:
     * <p>
     * Take the original relevance score from the positive query.
     * Multiply the score by the negative_boost value.
     *
     * @return
     */
    BoostingEnum type() default BoostingEnum.positive;

    /**
     * Floating point number used as the constant relevance score for every document matching the filter query.
     * Defaults to 1.0.
     *
     * @return
     */
    float negativeBoost() default 1.0F;

    /**
     * Elasticsearch bool type group
     *
     * @return
     */
    String group() default ElasticConstant.DEFAULT_GROUP;
}
