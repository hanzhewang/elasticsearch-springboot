package com.has.elastic.core.builder;

import com.has.elastic.core.model.IndexModel;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import sun.jvm.hotspot.asm.Register;

/**
 * <p>com.has.elastic.core.builder</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/12
 */
@Slf4j
public class ElasticIndexRequestBuilder {

    /**
     * 注册解析器
     *
     * @return
     */
    public ElasticIndexRequestBuilder registerPaster() {
        return this;
    }

    /**
     * 注册解析工厂(注解)
     *
     * @return
     */
    public ElasticIndexRequestBuilder registerPasterFactory() {
        return this;
    }

    /**
     * @return
     */
    public IndexRequestBuilder create() {
        IndexModel model = this.parserMappingInfo(target);
        getLog().error("EsWriteService.getIndexRequestBuilder#model:{},object:{}", JSON.toJSONString(model), JSON.toJSONString(target));
        Object requestParameter = null;
        if (model.isNested()) {
            throw new ElasticsearchException("");
        }
        if (model.isJoin()) {
            requestParameter = model.getWriteParameterByJoin(target);
        } else {
            requestParameter = target;
        }
        String json = getJsonAdapter().toJsonString(requestParameter);
        String uuid = model.getFieldValues().get(model.getUuid()).toString();
        IndexRequestBuilder indexRequest = getTransportClient()
                .prepareIndex(model.getIndex(), model.getType(), uuid)
                .setSource(json, XContentType.JSON);
        return null;
    }
}
