package com.blogads.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * @author NhatPA
 * @since 28/02/2022 - 22:36
 */
@Configuration
public class BeanConfiguration {

    /**
     * use for {@link org.springframework.validation.annotation.Validated}
     *
     * @return {@link MethodValidationPostProcessor}
     * @author NhatPA
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
