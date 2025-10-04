package com.saldi.ecom.product.domain.adapter

import com.saldi.ecom.product.domain.Product
import com.saldi.ecom.product.domain.ProductRepository
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

interface SpringDataMongoProductRepository : MongoRepository<Product, String> {
    fun findByCategory(category: String): List<Product>
}

@Repository
class MongoProductRepository(
    private val springRepo: SpringDataMongoProductRepository
) : ProductRepository {
    override fun save(product: Product): Product = springRepo.save(product)
    override fun findById(id: String): Product? = springRepo.findById(id).orElse(null)
    override fun findByCategory(category: String): List<Product> = springRepo.findByCategory(category)
}
