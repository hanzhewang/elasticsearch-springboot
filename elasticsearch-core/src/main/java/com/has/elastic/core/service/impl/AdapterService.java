package com.has.elastic.core.service.impl;

import com.jd.kos.commons.elasticsearch.service.BaseService;
import com.jd.kos.commons.elasticsearch.utils.adapter.CacheAdapter;
import com.jd.kos.commons.elasticsearch.utils.adapter.JsonAdapter;
import com.jd.kos.commons.elasticsearch.utils.adapter.ReflectAdapter;
import com.jd.kos.commons.elasticsearch.utils.adapter.impl.CacheForGoogle;
import com.jd.kos.commons.elasticsearch.utils.adapter.impl.JsonForFastjson;
import com.jd.kos.commons.elasticsearch.utils.adapter.impl.ReflectForJdk;
import com.jd.kos.commons.elasticsearch.utils.parser.mapping.BaseParser;
import lombok.Setter;

/**
 * <p></p>
 *
 * @author wanghanzhe
 * @version 1.0.1
 * @date 2019.10.21
 */
public abstract class AdapterService implements BaseParser, BaseService {

    /**
     * json adapter
     */
    @Setter
    private JsonAdapter jsonAdapter;

    /**
     * reflect adapter
     */
    @Setter
    private ReflectAdapter reflectAdapter;

    /**
     * local cache adapter
     */
    @Setter
    private CacheAdapter cacheAdapter;

    @Override
    public JsonAdapter getJsonAdapter() {
        if (jsonAdapter == null) {
            jsonAdapter = new JsonForFastjson();
        }
        return jsonAdapter;
    }
    @Override
    public ReflectAdapter getReflectAdapter() {
        if (reflectAdapter == null) {
            reflectAdapter = new ReflectForJdk();
        }
        return reflectAdapter;
    }
    @Override
    public CacheAdapter getCacheAdapter() {
        if (cacheAdapter == null) {
            cacheAdapter = new CacheForGoogle();
        }
        return cacheAdapter;
    }
}
