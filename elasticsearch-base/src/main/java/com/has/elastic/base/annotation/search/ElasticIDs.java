package com.has.elastic.base.annotation.search;

import java.lang.annotation.*;

/**
 * <p>com.has.elastic.base.annotation.search</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/21
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.FIELD)
public @interface ElasticIDs {

    /**
     * 开启权重
     *
     * @return
     */
    float boost() default 2.0F;
}
