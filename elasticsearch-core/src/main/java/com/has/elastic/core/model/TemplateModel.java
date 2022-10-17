package com.has.elastic.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.script.ScriptType;

/**
 * <p>The Elasticsearch template search mode class</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.17
 */
@Data
@NoArgsConstructor
public class TemplateModel extends com.jd.kos.commons.elasticsearch.model.IndexModel {

    /**
     * script template type
     */
    private ScriptType scriptType;

    /**
     * script template name
     */
    private String script;

}
