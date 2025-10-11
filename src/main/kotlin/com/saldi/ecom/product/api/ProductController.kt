package com.saldi.ecom.product.api

import com.saldi.ecom.product.application.ProductService
import com.saldi.ecom.product.domain.Product
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService
) {

    @PostMapping
    fun createProduct(@RequestBody product: Product): Product =
        productService.addProduct(product)

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: String): Product? =
        productService.getProduct(id)

    @GetMapping
    fun getProducts(): ResponseEntity<List<Product>> {
        val products = productService.getAllProducts()
        return ResponseEntity.ok(products)
    }
    @GetMapping("/category/{category}")
    fun getProductsByCategory(@PathVariable category: String): List<Product> =
        productService.getProductsByCategory(category)
}
