package com.blogads.aop;

import com.blogads.controller.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author NhatPA
 * @since 04/03/2022 - 20:31
 */
@Aspect
@Component
@Slf4j
public class UserControllerAop {

    @Autowired
    private UserService userService;

    @AfterReturning(pointcut = "execution(* com.blogads.controller.user.UserController.*(..))", returning = "object")
    public void addObject(JoinPoint joinPoint, Object object) {
        if (object instanceof ModelAndView) {
            // GET LIST CATEGORIES
            ((ModelAndView) object).addObject("categories", userService.findAllCategoryPopulation());
            // GET LIST TAG
            ((ModelAndView) object).addObject("tags", userService.findAllTag());
            // GET RECENT POSTS
            ((ModelAndView) object).addObject("recentPosts", userService.getRecentPosts());
            // GET HOST POSTS
            ((ModelAndView) object).addObject("hostPosts", userService.getHostPosts());
        }
    }
}
