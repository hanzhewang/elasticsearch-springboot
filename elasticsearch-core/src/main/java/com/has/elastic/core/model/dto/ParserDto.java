package com.has.elastic.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * <p>elasticsearch parser simple dto</p>
 *
 * @author wanghanzhe
 * @version 6.0.1
 * @date 2018.7.9
 * @since JDK1.8 and Elasticsearch 6.x
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ParserDto implements Serializable{

    /**
     * object property name
     */
    private String name;

    /**
     * object annotation
     */
    private Annotation annotation;

    /**
     * parser object property value array
     */
    private Map<String, Object> fieldsValue;
}
