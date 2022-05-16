package com.blogads.configuration;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@EnableConfigurationProperties(ApplicationProperties.class)
@ConfigurationPropertiesScan(basePackages = {"com.blogads.configuration"})
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Component
public class ApplicationProperties {

    private Elasticsearch elasticsearch;

    @Getter
    @Setter
    public static class Elasticsearch {
        //        private String clusterName;
        private String host;
        private int port;
    }
}
