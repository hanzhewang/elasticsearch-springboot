package com.has.elastic.base.annotation.search.compound;

import java.lang.annotation.*;

/**
 * <p>Disjunction max query</p>
 * Returns documents matching one or more wrapped queries, called query clauses or clauses.
 * <p>
 * If a returned document matches multiple query clauses,
 * the dis_max query assigns the document the highest relevance score from any matching clause,
 * plus a tie breaking increment for any additional matching subqueries.
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/23
 * @since 1.8
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DisjunctionMax {

    /**
     * Floating point number between 0 and 1.0 used to increase the relevance scores of documents matching multiple query clauses.
     * Defaults to 0.0.
     *
     * @return
     */
    float tieBreaker() default 0.0F;
}
