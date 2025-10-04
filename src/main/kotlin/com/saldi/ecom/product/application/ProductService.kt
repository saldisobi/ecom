package com.saldi.ecom.product.application

import com.saldi.ecom.product.domain.Product
import com.saldi.ecom.product.domain.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun addProduct(product: Product): Product = productRepository.save(product)
    fun getProduct(id: String): Product? = productRepository.findById(id)
    fun getProductsByCategory(category: String): List<Product> = productRepository.findByCategory(category)
}
