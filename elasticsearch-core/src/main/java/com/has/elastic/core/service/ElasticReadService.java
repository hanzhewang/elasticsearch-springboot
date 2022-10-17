package com.has.elastic.core.service;

import co.elastic.clients.elasticsearch.core.search.HitsMetadata;

import java.io.IOException;
import java.io.Serializable;

/**
 * <p>com.has.elastic.core.service</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/17
 */
public interface ElasticReadService {

    /**
     * 根据文档的UUID查询一条记录
     *
     * @param target
     * @param clazz
     * @param <T>
     * @param <R>
     * @return
     * @throws IOException
     */
    <T extends Serializable, R extends Serializable> R get(T target, Class<R> clazz) throws IOException;

    /**
     * 分页搜索
     *
     * @param target
     * @param clazz
     * @param <T>
     * @param <R>
     * @return
     * @throws IOException
     */
    <T extends Serializable, R extends Serializable> HitsMetadata<R> search(T target, Class<R> clazz) throws IOException;

    /**
     * 滚动搜索查询
     *
     * @param target
     * @param clazz
     * @param <T>
     * @param <R>
     * @return
     * @throws IOException
     */
    <T extends Serializable, R extends Serializable> HitsMetadata<R> scroll(T target, Class<R> clazz) throws IOException;
}
