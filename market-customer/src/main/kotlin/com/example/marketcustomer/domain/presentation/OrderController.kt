package com.example.marketcustomer.domain.presentation

import com.example.marketcustomer.domain.presentation.dto.OrderResponseList
import com.example.marketcustomer.domain.service.QueryOrderService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val queryOrderService: QueryOrderService,
) {

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/product")
    fun getNewProduct(): OrderResponseList {
        return queryOrderService.queryOrder()
    }
}
