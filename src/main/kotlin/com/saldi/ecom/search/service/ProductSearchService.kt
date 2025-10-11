package com.saldi.ecom.search.service

import com.saldi.ecom.search.model.SearchProduct
import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.stereotype.Service

@Service
class ProductSearchService(
    private val elasticsearchOperations: ElasticsearchOperations
) {

    fun searchProducts(keyword: String): List<SearchProduct> {
        val query = NativeQuery.builder()
            .withQuery { q ->
                q.multiMatch { m ->
                    m.query(keyword)
                        .fields("title^3.0", "description^1.0", "category^2.0")
                }
            }
            .build()

        val searchHits = elasticsearchOperations.search(query, SearchProduct::class.java)
        return searchHits.map { it.content }.toList()
    }

    fun saveProduct(product: SearchProduct): SearchProduct {
        return elasticsearchOperations.save(product)
    }
}
