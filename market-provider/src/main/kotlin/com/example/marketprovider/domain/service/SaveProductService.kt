package com.example.marketprovider.domain.service

import com.example.marketprovider.domain.model.ProductEntity
import com.example.marketprovider.domain.model.ProductRepository
import org.springframework.stereotype.Service

@Service
class SaveProductService(
    private val productRepository: ProductRepository,
) {
    fun saveProduct(productEntity: ProductEntity) {
        productRepository.save(productEntity)
    }
}
