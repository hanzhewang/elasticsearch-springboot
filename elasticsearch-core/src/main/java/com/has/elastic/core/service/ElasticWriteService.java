package com.has.elastic.core.service;

import co.elastic.clients.elasticsearch._types.Result;

import java.io.IOException;
import java.io.Serializable;

/**
 * <p>com.has.elastic.core.service</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/17
 */
public interface ElasticWriteService {

    /**
     * 写
     *
     * @param target
     * @param <T>
     * @return
     * @throws IOException
     */
    <T extends Serializable> Result index(T target) throws IOException;

    /**
     * 写入
     *
     * @param target
     * @param <T>
     * @return
     * @throws IOException
     */
    <T extends Serializable> Result update(T target) throws IOException;

    /**
     * 删除
     *
     * @param target
     * @param <T>
     * @return
     * @throws IOException
     */
    <T extends Serializable> Result delete(T target) throws IOException;

}
