package com.saldi.ecom.product.domain

interface ProductRepository {
    fun save(product: Product): Product
    fun findById(id: String): Product?
    fun findByCategory(category: String): List<Product>
}
