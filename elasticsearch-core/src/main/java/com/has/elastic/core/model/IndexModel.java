package com.has.elastic.core.model;

import com.google.common.collect.Maps;
import com.jd.kos.commons.elasticsearch.enums.common.ErrorType;
import com.jd.kos.commons.elasticsearch.exception.ElasticsearchException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>The Elasticsearch mapping index model class</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.17
 */
@Log4j2
@Data
@NoArgsConstructor
public class IndexModel implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2322263069887627520L;

    /**
     * mapping index
     */
    private String index;

    /**
     * mapping type
     */
    private String type;

    /**
     * document primary key
     */
    private String uuid;

    /**
     * document version lock
     */
    private String version;

    /**
     * join document "parent-child"
     */
    private JoinModel join;

    /**
     * document nested object
     */
    private NestedModel nested;

    /**
     * template search
     */
    private TemplateModel template;

    /**
     * template search
     */
    private Map<String, Object> fieldValues;

    /**
     * Is it parent-child structure
     *
     * @return
     */
    public boolean isJoin() {
        if (this.join != null) {
            return true;
        }
        return false;
    }

    /**
     * Is it nested structure
     *
     * @return
     */
    public boolean isNested() {
        if (this.nested != null) {
            return true;
        }
        return false;
    }

    /**
     * Get delete document data By Join mode
     *
     * @return
     */
    public String getDeleteRouter() {
        if (this.isJoin() && this.join.getType() == 1) {
            Object parentId = fieldValues.get(this.join.getParentId());
            if (parentId == null || StringUtils.isBlank(String.valueOf(parentId))) {
                throw new ElasticsearchException(ErrorType.JOIN_PARENT_ID_IS_NULL);
            }
            return parentId.toString();
        }
        return null;
    }

    /**
     * Get insert or update document data By Join mode
     *
     * @param target
     * @return
     */
    public Object getWriteParameterByJoin(Object target) {
        Map<String, Object> resultMap = fieldValues;
        if (this.isNested()) {
            resultMap = getWriteParameterByNested(target);
        }
        Map<String, Object> joinMap = Maps.newHashMap();
        String joinPath = this.join.getPath();
        String joinName = this.join.getName();
        if (StringUtils.isBlank(joinPath)) {
            throw new ElasticsearchException(ErrorType.JOIN_PATH_IS_NULL);
        }
        if (StringUtils.isBlank(joinName)) {
            throw new ElasticsearchException(ErrorType.JOIN_NAME_IS_NULL);
        }
        Object joinObject = null;
        switch (this.join.getType()) {
            case 1:
                Object parentId = fieldValues.get(this.join.getParentId());
                if (parentId == null || StringUtils.isBlank(String.valueOf(parentId))) {
                    throw new ElasticsearchException(ErrorType.JOIN_PARENT_ID_IS_NULL);
                }
                joinMap.put("name", joinName);
                joinMap.put("parent", parentId);
                joinObject = joinMap;
                break;
            case 2:
                joinObject = joinName;
                break;
            default:
                throw new ElasticsearchException(ErrorType.JOIN_TYPE);
        }
        resultMap.put(joinPath, joinObject);
        return target;
    }

    /**
     * Get insert or update document data By nested mode
     *
     * @param target
     * @return
     */
    public Map<String, Object> getWriteParameterByNested(Object target) {
        String nestedPath = this.nested.getPath();
        if (StringUtils.isBlank(nestedPath)) {
            throw new ElasticsearchException();
        }
        Map<String, Object> nestedMap = Maps.newHashMap();
        if (StringUtils.isBlank(nested.getMapKey()) && StringUtils.isBlank(nested.getMapValue())) {
            nestedMap.put(nestedPath, target);
        } else {
            Map<String, Object> resultMap = Maps.newHashMap();
            Object key = fieldValues.get(nested.getMapKey());
            resultMap.put((String) key, fieldValues.get(nested.getMapValue()));
            nestedMap.put(nestedPath, resultMap);
        }
        return nestedMap;
    }

}