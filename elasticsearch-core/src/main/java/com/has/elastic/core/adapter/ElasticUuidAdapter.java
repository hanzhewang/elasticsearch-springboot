package com.has.elastic.core.adapter;

import com.has.elastic.base.annotation.document.ElasticUuid;
import com.has.elastic.core.bo.ElasticUuidBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * <p>Elasticsearch Uuid</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/13
 * @since jdk1.8 and elasticsearch6、7
 */
@Slf4j
public class ElasticUuidAdapter implements ElasticAdapter<ElasticUuid, ElasticUuidBo> {

    public static final ElasticAdapterFactory FACTORY = new ElasticAdapterFactory() {
        @Override
        public ElasticUuidAdapter create() {
            return new ElasticUuidAdapter();
        }
    };

    @Override
    public ElasticUuidBo read(Field field, ElasticUuid uuid, Object value) {
        ElasticUuidBo bo = new ElasticUuidBo();
        bo.setField(field);
        bo.setElasticName(uuid.name());
        if (StringUtils.isNoneBlank(uuid.name())) {
            bo.setElasticName(uuid.name());
        }
        bo.setValue(value);
        bo.setOrder(uuid.order());
        return null;
    }
}