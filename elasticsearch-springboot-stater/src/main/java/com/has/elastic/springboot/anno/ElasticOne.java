package com.has.elastic.springboot.anno;

import java.lang.annotation.*;

/**
 * <p>com.has.elasticsearch.springboot.anno</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/12
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ElasticOne {
}
