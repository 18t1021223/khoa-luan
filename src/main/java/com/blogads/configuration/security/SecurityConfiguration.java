package com.blogads.configuration.security;

import com.blogads.filter.AdminEnableFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static com.blogads.exception.BlogAdsException.ACCESS_DENIED;
import static com.blogads.exception.BlogAdsException.USERNAME_PASSWORD_INCORRECT;

/**
 * @author NhatPA
 * @since 25/02/2022 - 02:34
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(
                        "/admin/login",
                        "/admin/register",
                        "/admin/forgot-password",
                        "/admin/change-password/**",
                        "/admin/new-password/**",
                        "/error"
                ).permitAll()
                .antMatchers("/admin/**").authenticated()
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .defaultSuccessUrl("/admin/home")
                .loginProcessingUrl("/j_spring_security_check")
                .loginPage("/admin/login")
                .failureUrl("/admin/login?message=" + USERNAME_PASSWORD_INCORRECT)
                .permitAll()
                .and()
                .logout().permitAll()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling().accessDeniedPage("/error?status=403&message=" + ACCESS_DENIED);
        http.rememberMe().key("key").tokenValiditySeconds(60 * 60 * 24 * 7);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //region filter
    @Bean
    public FilterRegistrationBean<AdminEnableFilter> filterRegistrationBean() {
        FilterRegistrationBean<AdminEnableFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new AdminEnableFilter());
        filter.setUrlPatterns(Collections.singletonList(
                "/admin/*"
        ));
        return filter;
    }
    //endregion
}
