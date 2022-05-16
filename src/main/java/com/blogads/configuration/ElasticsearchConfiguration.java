package com.blogads.configuration;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * @author NhatPA
 * @since 27/04/2022 - 09:39
 */
@Configuration
public class ElasticsearchConfiguration {

    private final ApplicationProperties.Elasticsearch elasticsearch;

    public ElasticsearchConfiguration(ApplicationProperties applicationProperties) {
        this.elasticsearch = applicationProperties.getElasticsearch();
    }

    @Bean
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(elasticsearch.getHost() + ":" + elasticsearch.getPort())
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
}
