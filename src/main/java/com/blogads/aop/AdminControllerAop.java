package com.blogads.aop;

import com.blogads.caching.CacheService;
import com.blogads.controller.admin.service.AdminService;
import com.blogads.entity.mysql.Post;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author NhatPA
 * @since 05/03/2022 - 05:10
 */
@Aspect
@Component
@Slf4j
public class AdminControllerAop {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public static final String POST_VIEW = "post_view_";

    /**
     * update view post
     *
     * @param joinPoint {@link JoinPoint}
     * @author NhatPA
     */
    @Around(value = "execution(* com.blogads.controller.user.UserController.post(..))")
    public Object updatePostView(ProceedingJoinPoint joinPoint) {
        ModelAndView mav = null;
        try {
            mav = (ModelAndView) joinPoint.proceed();
        } catch (Throwable e) {
            log.error("updatePostView {}", e.getMessage());
        }
        if (mav != null) {
            int id = ((Post) mav.getModel().get("post")).getPostId();
            redisTemplate.opsForValue().increment(POST_VIEW + id);
        }
        return mav;
    }

    /**
     * clear cache
     *
     * @param joinPoint {@link JoinPoint}
     * @author NhatPA
     */
    @AfterReturning(pointcut = "execution(* com.blogads.controller.admin.service.AdminService.insert*(..)) || " +
            "execution(* com.blogads.controller.admin.service.AdminService.update*(..)) ||" +
            "execution(* com.blogads.controller.admin.service.AdminService.delete*(..))")
    public void updatePostView(JoinPoint joinPoint) {
        //  cacheService.clearCache(Constants.CACHE_NAME_USER_SERVICE_PREFIX, CACHE_NAME_ADMIN_SERVICE_PREFIX);
    }
}
