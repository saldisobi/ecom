package com.saldi.ecom.config

import com.saldi.ecom.product.events.ProductCreatedEvent
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer

@EnableKafka
@Configuration
class KafkaConfig {

    @Bean
    fun producerFactory(): ProducerFactory<String, ProductCreatedEvent> {
        val config = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        )
        return DefaultKafkaProducerFactory(config)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, ProductCreatedEvent> =
        KafkaTemplate(producerFactory())

    @Bean
    fun consumerFactory(): ConsumerFactory<String, ProductCreatedEvent> {
        val deserializer = JsonDeserializer(ProductCreatedEvent::class.java)
        deserializer.addTrustedPackages("*")
        return DefaultKafkaConsumerFactory(
            mapOf(
                "bootstrap.servers" to "localhost:9092",
                "group.id" to "search-indexer"
            ),
            StringDeserializer(),
            deserializer
        )
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, ProductCreatedEvent> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, ProductCreatedEvent>()
        factory.consumerFactory = consumerFactory()
        return factory
    }
}
