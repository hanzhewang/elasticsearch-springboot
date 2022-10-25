package com.has.elastic.base.annotation.search.compound;

import com.has.elastic.base.constant.ElasticConstant;
import com.has.elastic.base.enums.compound.BoolEnum;

import java.lang.annotation.*;

/**
 * <p>Bool query</p>
 * A query that matches documents matching boolean combinations of other queries. The bool query maps to Lucene BooleanQuery.
 * It is built using one or more boolean clauses, each clause with a typed occurrence.
 * The occurrence types are:
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/23
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Bool {

    /**
     * must
     * The clause (query) must appear in matching documents and will contribute to the score.
     * <p>
     * filter
     * The clause (query) must appear in matching documents.
     * However unlike must the score of the query will be ignored.
     * Filter clauses are executed in filter context, meaning that scoring is ignored and clauses are considered for caching.
     * <p>
     * should
     * The clause (query) should appear in the matching document.
     * <p>
     * must_not
     * The clause (query) must not appear in the matching documents.
     * Clauses are executed in filter context meaning that scoring is ignored and clauses are considered for caching.
     * Because scoring is ignored, a score of 0 for all documents is returned.
     *
     * @return
     */
    BoolEnum type() default BoolEnum.filter;

    /**
     * Elasticsearch bool type group
     *
     * @return
     */
    String group() default ElasticConstant.DEFAULT_GROUP;


   int minimum_should_match() default 1;

}
