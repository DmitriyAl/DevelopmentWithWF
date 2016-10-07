package io.albot.javaee7.chapter4.util;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

/**
 * @author D. Albot
 * @date 07.10.2016
 */
@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Logged {
}
