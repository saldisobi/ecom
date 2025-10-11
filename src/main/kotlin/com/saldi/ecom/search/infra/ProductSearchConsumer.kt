package com.saldi.ecom.search.infra

import com.saldi.ecom.product.events.ProductCreatedEvent
import com.saldi.ecom.search.model.SearchProduct
import com.saldi.ecom.search.repository.ProductSearchRepository
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class ProductSearchConsumer(
    private val productSearchRepository: ProductSearchRepository
) {
    @KafkaListener(topics = ["products"], groupId = "search-indexer")
    fun consume(event: ProductCreatedEvent) {
        val doc = SearchProduct(
            id = event.id,
            title = event.name,
            description = event.description,
            category = event.category,
            price = event.price,
            stock = event.stock,
            popularity = 0
        )
        productSearchRepository.save(doc)
        println("Indexed product ${event.id} into Elasticsearch")
    }
}
