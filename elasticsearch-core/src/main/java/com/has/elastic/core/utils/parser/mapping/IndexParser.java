package com.has.elastic.core.utils.parser.mapping;

import com.has.elastic.base.annotation.mapping.ElasticNested;
import com.jd.kos.commons.elasticsearch.annotation.mapping.Index;
import com.jd.kos.commons.elasticsearch.annotation.mapping.Join;
import com.jd.kos.commons.elasticsearch.annotation.mapping.Nested;
import com.jd.kos.commons.elasticsearch.annotation.mapping.Template;
import com.jd.kos.commons.elasticsearch.enums.common.ErrorType;
import com.jd.kos.commons.elasticsearch.exception.ElasticsearchException;
import com.jd.kos.commons.elasticsearch.model.IndexModel;
import com.jd.kos.commons.elasticsearch.model.JoinModel;
import com.jd.kos.commons.elasticsearch.model.NestedModel;
import com.jd.kos.commons.elasticsearch.model.TemplateModel;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.script.ScriptType;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * <p>The Elasticsearch mapping index parser </p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2018.10.16
 * @since JDK 1.8 and Elasticsearch 6.3
 */
public interface IndexParser extends BaseParser {

    /**
     * parser object elasticsearch mapping index property
     *
     * @param target parser object
     * @return
     */
    default IndexModel parserMappingInfo(Object target) {
        Map<String, Object> fieldsValue = getReflectAdapter().getFieldsValueMap(target);
        Annotation[] annotations = getReflectAdapter().getAnnotationsByClass(target.getClass());
        int len = annotations != null ? annotations.length : 0;
        for (int index = 0; index < len; index++) {
            Annotation annotation = annotations[index];
            if (annotation == null) {
                continue;
            }
            if (annotation instanceof Index) {
                return getIndexModel(fieldsValue, (Index) annotation);
            }
        }
        return null;
    }

    /**
     * Get object "ElasticMapping" annotation parameter
     *
     * @param fieldsValue
     * @param index
     * @return
     */
    default IndexModel getIndexModel(Map<String, Object> fieldsValue, Index index) {
        if (index == null) {
            throw new ElasticsearchException(ErrorType.INDEX_IS_NULL);
        }
        IndexModel model = new IndexModel();
        model.setIndex(index.index());
        model.setType(index.type());
        model.setUuid(index.uuid());
        model.setVersion(index.version());
        model.setFieldValues(fieldsValue);
        if (index.join().length > 0) {
            Join join = index.join()[0];
            JoinModel joinModel = new JoinModel();
            joinModel.setPath(join.path());
            joinModel.setName(join.joinName());
            joinModel.setType(join.type());
            joinModel.setParentId(join.parentId());
            model.setJoin(joinModel);
        }
        if (index.nested().length > 0) {
            ElasticNested nested = index.nested()[0];
            NestedModel nestedModel = new NestedModel();
            nestedModel.setPath(nested.path());
            nestedModel.setMapKey(nested.mapKey());
            nestedModel.setMapValue(nested.mapValue());
            model.setNested(nestedModel);
        }
        if (index.template().length > 0) {
            Template template = index.template()[0];
            TemplateModel templateModel = new TemplateModel();
            templateModel.setScriptType(ScriptType.valueOf(template.scriptType()));
            templateModel.setScript(template.script());
            model.setTemplate(templateModel);
        }
        if (StringUtils.isBlank(model.getIndex())) {
            throw new ElasticsearchException(ErrorType.INDEX_IS_NULL);
        }
        if (StringUtils.isBlank(model.getType())) {
            throw new ElasticsearchException(ErrorType.TYPE_IS_NULL);
        }
        return model;
    }

}