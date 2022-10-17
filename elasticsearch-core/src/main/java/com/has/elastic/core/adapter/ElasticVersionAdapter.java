package com.has.elastic.core.adapter;

import com.has.elastic.base.annotation.document.ElasticVersion;
import com.has.elastic.core.bo.ElasticVersionBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * <p>Elasticsearch 版本控制注解适配器 </p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/13
 * @since jdk1.8 and elasticsearch6、7
 */
@Slf4j
public class ElasticVersionAdapter implements ElasticAdapter<ElasticVersion, ElasticVersionBo> {

    /**
     * 版本号注解适配工厂
     */
    public static final ElasticAdapterFactory FACTORY = new ElasticAdapterFactory() {
        @Override
        public ElasticUuidAdapter create() {
            return new ElasticUuidAdapter();
        }
    };

    @Override
    public ElasticVersionBo read(Field field, ElasticVersion version, Object value) {
        ElasticVersionBo bo = new ElasticVersionBo();
        bo.setField(field);
        bo.setElasticName(version.name());
        if (StringUtils.isNoneBlank(version.name())) {
            bo.setElasticName(version.name());
        }
        bo.setValue(value);
        return bo;
    }
}