package com.has.elastic.core.utils.adapter.impl;

import com.alibaba.fastjson.JSON;
import com.jd.kos.commons.elasticsearch.utils.adapter.JsonAdapter;
import lombok.extern.log4j.Log4j2;

/**
 * <p>alibaba fastjson parser</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.12
 */
@Log4j2
public class JsonForFastjson implements JsonAdapter {

    @Override
    public <T> String toJsonString(T target) {
        if (log.isDebugEnabled()) {
            log.info("my elasticsearch parser json toJsonString: object:{}", target);
        }
        return JSON.toJSONString(target);
    }

    @Override
    public <T> T fromJson(String json, Class<T> clazz) {
        if (log.isDebugEnabled()) {
            log.info("my elasticsearch parser json formJson: class:{}, json={}", clazz, json);
        }
        return JSON.parseObject(json, clazz);
    }
}
