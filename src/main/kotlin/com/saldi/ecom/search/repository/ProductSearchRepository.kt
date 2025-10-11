package com.saldi.ecom.search.repository

import com.saldi.ecom.search.model.SearchProduct
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository


@Repository
interface ProductSearchRepository : ElasticsearchRepository<SearchProduct, String>
