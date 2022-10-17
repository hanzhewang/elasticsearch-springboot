package com.has.elastic.springboot.config;

import com.has.elastic.core.config.ElasticConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>属性配置</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/9/29
 */
@Slf4j
@Data
@ConfigurationProperties(
        prefix = "has.springboot.elastic"
)
public class ElasticProperties extends ElasticConfig {

}
