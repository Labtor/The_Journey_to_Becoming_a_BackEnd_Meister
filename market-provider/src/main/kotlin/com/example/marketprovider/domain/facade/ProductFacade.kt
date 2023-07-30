package com.example.marketprovider.domain.facade

import com.example.marketprovider.domain.model.ProductEntity
import com.example.marketprovider.domain.presentation.dto.ProductRequest
import com.example.marketprovider.domain.service.SaveProductService
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class ProductFacade(
    private val kafkaTemplate: KafkaTemplate<String, ProductEntity>,
    private val saveProductService: SaveProductService,
) {

    companion object {
        private const val TOPIC = "NewTopic"
    }

    fun sendTopic(productRequest: ProductRequest) {
        val productEntity = ProductEntity(
            name = productRequest.name,
            price = productRequest.price,
        )
        saveProductService.saveProduct(productEntity)
        kafkaTemplate.send(TOPIC, productEntity)
    }
}
