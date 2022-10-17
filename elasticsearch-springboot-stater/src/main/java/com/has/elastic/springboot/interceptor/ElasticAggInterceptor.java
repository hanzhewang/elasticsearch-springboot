package com.has.elastic.springboot.interceptor;

import com.has.elastic.springboot.config.ElasticProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import javax.annotation.Resource;

/**
 * <p>com.has.elasticsearch.springboot.interceptor</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/12
 */
@Slf4j
@Aspect
public class ElasticAggInterceptor {

    @Resource
    private ElasticProperties elasticProperties;

    @Pointcut("@annotation(com.has.elastic.springboot.anno.ElasticAgg)")
    public void point() {

    }

    /**
     * Exec j annotation object.
     *
     * @param jp the jp
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("point()")
    public Object execJAnnotation(ProceedingJoinPoint jp) throws Throwable {
        return jp.proceed();
    }
}
