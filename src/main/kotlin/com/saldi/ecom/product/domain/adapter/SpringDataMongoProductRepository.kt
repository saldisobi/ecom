package com.saldi.ecom.product.domain.adapter

import com.saldi.ecom.product.domain.Product
import com.saldi.ecom.product.domain.ProductRepository
import com.saldi.ecom.product.events.ProductCreatedEvent
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Repository

interface SpringDataMongoProductRepository : MongoRepository<Product, String> {
    fun findByCategory(category: String): List<Product>
}

@Repository
class MongoProductRepository(
    private val springRepo: SpringDataMongoProductRepository,
    private val kafkaTemplate: KafkaTemplate<String, ProductCreatedEvent>
) : ProductRepository {
    override fun save(product: Product): Product {
        val saved = springRepo.save(product)

        // Publish Kafka event
        val event = ProductCreatedEvent(
            id = saved.id!!,
            name = saved.name,
            description = saved.category, // TODO saldi temp
            price = saved.price,
            category = saved.category,
            stock = 1 // TODO saldi
        )
        kafkaTemplate.send("products", event.id, event)

        return saved

    }

    override fun findById(id: String): Product? = springRepo.findById(id).orElse(null)
    override fun findByCategory(category: String): List<Product> = springRepo.findByCategory(category)
    override fun getAllProducts() = springRepo.findAll()
}
