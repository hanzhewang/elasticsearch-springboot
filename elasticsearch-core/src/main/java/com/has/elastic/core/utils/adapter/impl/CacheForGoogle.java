package com.has.elastic.core.utils.adapter.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jd.kos.commons.elasticsearch.utils.adapter.CacheAdapter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * <p>Google guava cache tool</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.12
 */
@Log4j2
public class CacheForGoogle implements CacheAdapter {

    /**
     * Cache key contact prefix
     */
    @Setter
    private String cachePrefix = "elasticsearch_tool_cache_";

    /**
     * Cache client
     */
    private final Cache<String, Object> cache = CacheBuilder.newBuilder()
            //设置cache的初始大小为10，要合理设置该值
            .initialCapacity(15)
            //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
            .concurrencyLevel(5)
            //设置cache中的数据在写入之后的存活时间为10秒
            .expireAfterWrite(365, TimeUnit.DAYS)
            //构建cache实例
            .build();


    @Override
    public Object get(String cacheKey) {
        String key = StringUtils.join(cachePrefix, cacheKey);
        if (log.isDebugEnabled()) {
            log.info("elasticsearch cache tool getCache: key={}", key);
        }
        return cache.getIfPresent(key);
    }

    @Override
    public void set(String cacheKey, Object value) {
        String key = StringUtils.join(cachePrefix, cacheKey);
        if (log.isDebugEnabled()) {
            log.info("elasticsearch cache tool putCache: key={}, value:{}", key, value);
        }
        cache.put(key, value);
    }

    @Override
    public void remove(String cacheKey) {
        String key = StringUtils.join(cachePrefix, cacheKey);
        if (log.isDebugEnabled()) {
            log.info("elasticsearch cache tool remove: key={}, value:{}", key);
        }
        cache.invalidate(key);
    }

    @Override
    public void clear() {
        if (log.isDebugEnabled()) {
            log.info("elasticsearch cache tool clear");
        }
        cache.invalidateAll();
    }

}
