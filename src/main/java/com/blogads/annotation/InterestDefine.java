package com.blogads.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author NhatPA
 * @since 26/02/2022 - 22:23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InterestDefine {

    String field();

    Class<?> typeField() default Object.class;
}
