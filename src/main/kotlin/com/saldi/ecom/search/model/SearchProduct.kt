package com.saldi.ecom.search.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "products")
data class SearchProduct(
    @Id
    val id: String? = null,

    @Field(type = FieldType.Text)
    val title: String,

    @Field(type = FieldType.Text)
    val description: String,

    @Field(type = FieldType.Keyword)
    val category: String,

    @Field(type = FieldType.Double)
    val price: Double,

    @Field(type = FieldType.Integer)
    val stock: Int,

    @Field(type = FieldType.Integer)
    val popularity: Int = 0
)
