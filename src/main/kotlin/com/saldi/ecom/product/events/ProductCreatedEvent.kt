package com.saldi.ecom.product.events

data class ProductCreatedEvent(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val stock: Int
)
