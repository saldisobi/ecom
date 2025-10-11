package com.saldi.ecom.search.controller

import com.saldi.ecom.search.model.SearchProduct
import com.saldi.ecom.search.service.ProductSearchService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/search")
class ProductSearchController(
    private val productSearchService: ProductSearchService
) {

    @GetMapping("/api/search")
    fun search(@RequestParam keyword: String): List<SearchProduct> {
        return productSearchService.searchProducts(keyword)
    }
}
