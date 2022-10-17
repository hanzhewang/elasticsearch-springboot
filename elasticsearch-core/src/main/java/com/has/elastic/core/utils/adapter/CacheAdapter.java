package com.has.elastic.core.utils.adapter;

/**
 * <p>Cache adapter interface</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.12
 */
public interface CacheAdapter {

    /**
     * Get cache object for key
     *
     * @param key
     * @param <T>
     * @return
     */
    <T> T get(String key);

    /**
     * Set cache object value for key
     *
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     * Remove data for cache key cacheKey
     *
     * @param cacheKey
     */
    void remove(String cacheKey);

    /**
     * Clear cache all data
     */
    void clear();

}
