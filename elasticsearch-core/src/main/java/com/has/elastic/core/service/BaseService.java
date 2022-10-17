package com.has.elastic.core.service;

import com.jd.kos.commons.elasticsearch.utils.adapter.JsonAdapter;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;

/**
 * <p>The Elasticsearch base interface</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.6
 * @since JDK 1.8 and Elasticsearch 6.3
 */
public interface BaseService {

    /**
     * The Elasticsearch client
     *
     * @return
     */
    TransportClient getTransportClient();

    /**
     * The json parser adapter
     *
     * @return
     */
    JsonAdapter getJsonAdapter();

    Logger getLog();

}
