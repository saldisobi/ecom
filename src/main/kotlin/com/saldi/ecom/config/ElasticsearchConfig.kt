package com.saldi.ecom.config

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.transport.ElasticsearchTransport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories

@Configuration
@EnableElasticsearchRepositories
class ElasticsearchConfig : ElasticsearchConfiguration() {

    override fun clientConfiguration(): ClientConfiguration {
        return ClientConfiguration.builder()
            .connectedTo("localhost:9200")
            .build()
    }

    @Bean
    override fun elasticsearchClient(transport: ElasticsearchTransport): ElasticsearchClient {
        return ElasticsearchClient(transport)
    }
}