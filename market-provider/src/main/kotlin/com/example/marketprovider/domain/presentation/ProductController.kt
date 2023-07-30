package com.example.marketprovider.domain.presentation

import com.example.marketprovider.domain.facade.ProductFacade
import com.example.marketprovider.domain.model.ProductEntity
import com.example.marketprovider.domain.presentation.dto.ProductRequest
import org.springframework.http.HttpStatus
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class ProductController(
    private val productFacade: ProductFacade,
) {

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/order")
    fun orderProduct(@Valid @RequestBody productRequest: ProductRequest) {
        productFacade.sendTopic(productRequest)
    }
}
