package com.has.elastic.core.model.dto;

import com.jd.kos.commons.elasticsearch.annotation.search.QueryParameter;
import com.jd.kos.commons.elasticsearch.enums.search.QueryBoolType;
import com.jd.kos.commons.elasticsearch.enums.search.QueryParameterType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>elasticsearch search param vo</p>
 *
 * @author wanghanzhe
 * @version 6.0.1
 * @date 2018.7.9
 * @since JDK1.8 and Elasticsearch 6.x
 */
@Data
@NoArgsConstructor
public class QueryParserDto implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2248403739183714655L;

    /**
     * Elasticsearch property name
     * default java object property name
     */
    private String name;

    /**
     * Elasticsearch property value
     */
    private Object value;

    /**
     * Elasticsearch query condition type
     *
     * @see QueryParameterType
     */
    private QueryParameterType type;

    /**
     * group name
     *
     */
    private String boolPath;

    /**
     * nest path
     */
    private String path;

    /**
     * bool type
     */
    private QueryBoolType boolEnum;

    /**
     * query annotation
     */
    private QueryParameter field;

    /**
     * base constructor
     * @param fieldName
     * @param field
     * @param fieldsValue
     */
    public QueryParserDto(String fieldName, QueryParameter field, Map<String, Object> fieldsValue) {
        this.name = fieldName;
        this.value = fieldsValue.get(fieldName);
        this.field = field;
        this.type = field.type();
        this.boolPath = field.boolGroup();
        this.path = field.path();
        this.boolEnum = field.bool();
    }

}
