package com.example.marketcustomer.domain.service

import com.example.marketcustomer.domain.model.OrderEntity
import com.example.marketcustomer.domain.model.OrderRepository
import org.springframework.stereotype.Service

@Service
class SaveOrderService(
    private val orderRepository: OrderRepository,
) {

    fun saveOrder(orderEntity: OrderEntity) {
        orderRepository.save(orderEntity)
    }
}