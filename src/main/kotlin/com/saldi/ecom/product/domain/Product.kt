package com.saldi.ecom.product.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "products")
data class Product(
    @Id val id: String? = null,
    val name: String,
    val category: String,
    val price: Double,
    val attributes: Map<String, Any> = emptyMap(),
    val stock: Map<String, Int> = emptyMap(),
    val createdAt: Instant = Instant.now()
)
