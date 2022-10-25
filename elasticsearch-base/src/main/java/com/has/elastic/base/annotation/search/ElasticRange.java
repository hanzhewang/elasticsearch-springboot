package com.has.elastic.base.annotation.search;

import java.lang.annotation.*;

/**
 * <p>com.has.elastic.base.annotation.search</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/21
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.FIELD)
public @interface ElasticRange {

    /**
     * Elasticsearch 属性字段（默认当前字段名）
     *
     * @return
     */
    String name() default "";

    /**
     * Elasticsearch 嵌套对象属性名
     *
     * @return
     */
    String nested() default "";

    /**
     * 开启权重
     *
     * @return
     */
    float boost() default 2.0F;

    /**
     * gt
     * (Optional) Greater than.
     * gte
     * (Optional) Greater than or equal to.
     * lt
     * (Optional) Less than.
     * lte
     * (Optional) Less than or equal to.
     *
     * @return
     */
    String type() default "gt";

    /**
     * (Optional, string) Coordinated Universal Time (UTC) offset or IANA time zone used to convert date values in the query to UTC.
     * <p>
     * Valid values are ISO 8601 UTC offsets, such as +01:00 or -08:00, and IANA time zone IDs, such as America/Los_Angeles.
     * <p>
     * For an example query using the time_zone parameter, see Time zone in range queries.
     *
     * @return
     */
    String timeZone() default "UTC";

    /**
     * (Optional, string) Date format used to convert date values in the query.
     * <p>
     * By default, Elasticsearch uses the date format provided in the <field>'s mapping. This value overrides that mapping format.
     * <p>
     * For valid syntax, see format.
     *
     * @return
     */
    String format() default "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * relation
     * (Optional, string) Indicates how the range query matches values for range fields. Valid values are:
     * <p>
     * INTERSECTS (Default)
     * Matches documents with a range field value that intersects the query’s range.
     * CONTAINS
     * Matches documents with a range field value that entirely contains the query’s range.
     * WITHIN
     * Matches documents with a range field value entirely within the query’s range.
     *
     * @return
     */
    String relation() default "INTERSECTS";

    /**
     * (Optional, string) Method used to rewrite the query.
     * For valid values and more information, see the rewrite parameter.
     *
     * @return
     */
    String rewrite() default "";

}
