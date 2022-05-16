package com.blogads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackages = {"com.blogads.entity.mysql"})
@EnableJpaRepositories(basePackages = {"com.blogads.repository.mysql"})
@EnableElasticsearchRepositories(basePackages = {"com.blogads.repository.elasticsearch"})
@EnableScheduling
public class BlogAdsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogAdsApplication.class, args);
    }

    @PostConstruct
    public void setTimeZone() {
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
    }
}
