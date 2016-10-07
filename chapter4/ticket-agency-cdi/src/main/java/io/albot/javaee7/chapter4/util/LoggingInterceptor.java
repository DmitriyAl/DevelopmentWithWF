package io.albot.javaee7.chapter4.util;

import org.jboss.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

/**
 * @author D. Albot
 * @date 07.10.2016
 */
@Interceptor
@Logged
public class LoggingInterceptor implements Serializable {
    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        final Logger logger = Logger.getLogger(context.getTarget().getClass());
        logger.infov("Executing method {0}", context.getMethod().toString());
        return context.proceed();
    }

}
