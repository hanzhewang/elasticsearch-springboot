package com.has.elastic.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>The Elasticsearch nested mode class</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.17
 */
@Data
@NoArgsConstructor
public class NestedModel extends com.jd.kos.commons.elasticsearch.model.IndexModel {

    /**
     * nested path
     */
    private String path;

    /**
     * list to map key property name
     */
    private String mapKey;

    /**
     * list to map value property name
     */
    private String mapValue;
}
