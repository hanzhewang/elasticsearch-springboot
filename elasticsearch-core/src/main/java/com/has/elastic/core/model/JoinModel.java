package com.has.elastic.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>The Elasticsearch join mode class</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinModel implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2720653901860261002L;

    /**
     * join type
     * 1. child
     * 2. parent
     */
    private Integer type;

    /**
     * child foreign key
     */
    private String parentId;

    /**
     * join name;
     */
    private String name;

    /**
     * join path;
     */
    private String path;

}
