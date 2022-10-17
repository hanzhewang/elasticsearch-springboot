package com.has.elastic.core.adapter;

/**
 * <p>Elasticsearch适配器工厂</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/12
 * @since jdk1.8 and elasticsearch6、7
 */
public interface ElasticAdapterFactory {

    /**
     * 创建注解解析对象
     *
     * @return
     */
    ElasticAdapter create();

}
