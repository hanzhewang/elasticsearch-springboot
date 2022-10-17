package com.has.elastic.base.enums.mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Elasticsearch 属性类型</p>
 *
 * @author wanghanzhe
 * @version 6.0.1
 * @date 2018.10.16
 * @since JDK 1.8 Elasticsearch 6.x
 */
@AllArgsConstructor
public enum ElasticFieldType {
    /**
     *
     */
    KEYWORD("keyword"),
    TEXT("text"),
    LONG("long"),
    INTEGER("integer"),
    SHORT("short"),
    BYTE("byte"),
    FLOAT("float"),
    DOUBLE("double"),
    HALF_FLOAT("half_float"),
    SCALED_FLOAT("scaled_float"),
    DATE("date"),
    BOOLEAN("boolean"),
    BINARY("binary"),
    INTEGER_RANGE("integer_range"),
    FLOAT_RANGE("float_range"),
    LONG_RANGE("long_range"),
    DOUBLE_RANGE("double_range"),
    DATE_RANGE("date_range"),
    ARRAY("array"),
    OBJECT("object"),
    NESTED("nested"),
    GEO_POINT("geo_point"),
    GEO_SHAPE("geo_shape"),
    IP("ip"),
    COMPLETION("completion"),
    TOKEN_COUNT("token_count"),
    MAPPER_MURMUR3("mapper_murmur3"),
    PERCOLATOR("percolator"),
    JOIN("join"),
    JOIN_PARENT("join_parent"),
    JOIN_CHILD("join_child"),
    //   6.0
    //   @Deprecated
//   STRING("string"),
//   attachment("attachment"),
    //   6.4
//   ALIAS(""),
    //   6.5
//   mapper-annotated-text(""),
    //   7.0
//   RANK_FEATURE(""),
//   RANK_FEATURES(""),
//   DENSE_VECTOR(""),
//   Sparse_vector(""),
    ;
    /**
     * 编码
     */
    @Getter
    private String code;

}
