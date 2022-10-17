package com.has.elastic.base.annotation.document;

import java.lang.annotation.*;

/**
 * <p>Elasticsearch Document 版本号</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.TYPE)
public @interface ElasticVersion {

    /**
     * 版本号
     *
     * @return
     */
    String name() default "";

    /**
     * 开启外部版本模式
     *
     * @return
     */
    boolean outSwitch() default false;
}
