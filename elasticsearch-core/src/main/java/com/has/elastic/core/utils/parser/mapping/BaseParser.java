package com.has.elastic.core.utils.parser.mapping;

import com.jd.kos.commons.elasticsearch.utils.adapter.CacheAdapter;
import com.jd.kos.commons.elasticsearch.utils.adapter.ReflectAdapter;
import org.slf4j.Logger;

/**
 * <p>The Elasticsearch basic parser </p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2018.10.16
 * @since JDK 1.8 and Elasticsearch 6.3
 */
public interface BaseParser {

    /**
     * Object reflect tool adapter
     *
     * @return
     */
    ReflectAdapter getReflectAdapter();

    /**
     * Object cache tool adapter
     *
     * @return
     */
    CacheAdapter getCacheAdapter();

    /**
     * 日志工具
     *
     * @return
     */
    Logger getLog();

}
