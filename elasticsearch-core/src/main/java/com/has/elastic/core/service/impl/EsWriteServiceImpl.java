package com.has.elastic.core.service.impl;

import com.jd.kos.commons.elasticsearch.config.ClientFactory;
import com.jd.kos.commons.elasticsearch.service.EsWriteService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;

/**
 * <p>The elasticsearch write data service</p>
 *
 * @author wanghanzhe
 * @version 1.2.1
 * @date 2019.9.6
 */
@Slf4j
public class EsWriteServiceImpl extends AdapterService implements EsWriteService {

    @Setter
    private ClientFactory clientFactory;

    @Override
    public TransportClient getTransportClient() {
        return clientFactory.getClient();
    }

    @Override
    public Logger getLog() {
        return log;
    }

}
