package com.has.elastic.core.adapter;

import com.has.elastic.base.annotation.search.ElasticSort;
import com.has.elastic.core.bo.ElasticSortBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * <p>Elasticsearch 排序适配</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/13
 * @since jdk1.8 and elasticsearch6、7
 */
@Slf4j
public class ElasticSortAdapter implements ElasticAdapter<ElasticSort, ElasticSortBo> {
    /**
     * Elasticsearch 排序适配工厂
     */
    public static final ElasticAdapterFactory FACTORY = new ElasticAdapterFactory() {
        @Override
        public ElasticSortAdapter create() {
            return new ElasticSortAdapter();
        }
    };

    @Override
    public ElasticSortBo read(Field field, ElasticSort sort, Object value) {
        ElasticSortBo bo = new ElasticSortBo();
        bo.setField(field);
        bo.setOrderBy(sort.orderBy());
        if (StringUtils.isBlank(bo.getOrderBy()) && value != null) {
            bo.setOrderBy(value.toString());
        }
        return bo;
    }
}
