package com.has.elastic.core.utils.adapter;

/**
 * <p>The json parser adapter interface</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.3
 */
public interface JsonAdapter {

    /**
     * object to json format string
     *
     * @param traget
     * @param <T>
     * @return
     */
    <T> String toJsonString(T traget);

    /**
     * json format string to object
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T fromJson(String json, Class<T> clazz);
}
