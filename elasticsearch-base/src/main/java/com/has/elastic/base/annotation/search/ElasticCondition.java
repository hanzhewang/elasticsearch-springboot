package com.has.elastic.base.annotation.search;

import com.has.elastic.base.enums.mapping.ElasticFieldType;
import com.has.elastic.base.enums.search.QueryBoolType;
import com.has.elastic.base.enums.search.QueryConditionType;

import java.lang.annotation.*;

/**
 * <p>ES 过滤拦截器注解</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2018.6.11
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.FIELD)
public @interface ElasticCondition {

    /**
     * ES 对应索引的检索字段（默认当前字段名）
     *
     * @return
     */
    String name() default "";

    /**
     * ES 对象
     *
     * @return
     */
    ElasticFieldType type();

    /**
     * ES 条件类型
     *
     * @return
     */
    QueryConditionType condition() default QueryConditionType.TERM;

    /**
     * ES 组合查询规则
     *
     * @return
     */
    QueryBoolType bool() default QueryBoolType.MUST;

    /**
     * ES 组合匹配（or）
     *
     * @return
     */
    String boolGroup() default "simple";

    /**
     * 子对象路径
     *
     * @return
     */
    String nested() default "";

}
