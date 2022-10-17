package com.has.elastic.springboot.interceptor;

import com.has.elastic.springboot.config.ElasticProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * <p>com.has.elasticsearch.springboot.interceptor</p>
 *
 * @author wanghanzhe
 * @version 1.0.0
 * @date 2022/10/12
 */
@Slf4j
@Aspect
public class ElasticPageInterceptor {

    @Resource
    private ElasticProperties elasticProperties;

    @Pointcut("@annotation(com.has.elastic.springboot.anno.ElasticPage)")
    public void point() {
    }

    @After("point()")
    public void execJAnnotation(ProceedingJoinPoint jp) {

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


        Class<?> errorClass = null;

        Object[] params = jp.getArgs();

        Object returnValue = jp.proceed();

        Method method = this.getMethod(jp);

        try {

            return returnValue;

        } catch (Throwable e) {

        }

        return null;
    }

    /**
     * 获取包名与类名
     *
     * @param jp
     * @param index
     * @return
     */
    default String getIntefaceName(JoinPoint jp, int index) throws Exception {
        MethodSignature msig = (MethodSignature) jp.getSignature();
        Class[] clazzArr = msig.getDeclaringType().getInterfaces();
        if (clazzArr != null && clazzArr.length > 0) {
            return clazzArr[index].getName();
        }
        return "";
    }

    /**
     * 获取拦截的方法
     *
     * @param jp
     * @return
     * @throws Exception
     */
    default Method getMethod(JoinPoint jp) throws Exception {
        MethodSignature msig = (MethodSignature) jp.getSignature();
        return msig.getMethod();
    }
}
