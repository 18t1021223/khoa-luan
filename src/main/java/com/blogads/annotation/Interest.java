package com.blogads.annotation;

import com.blogads.controller.user.dto.InterestDto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * use cookie name = {@link com.blogads.utils.Constants#INTEREST_COOKIE}
 * type value cookie = {@link InterestDto}
 *
 * @author NhatPA
 * @since 26/02/2022 - 21:53
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Interest {

    //    String cookieName() default "interest";

    InterestDefine value();
}
