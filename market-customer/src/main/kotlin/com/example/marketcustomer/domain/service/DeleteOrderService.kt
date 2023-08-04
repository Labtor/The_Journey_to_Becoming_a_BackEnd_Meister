package com.example.marketcustomer.domain.service

import com.example.marketcustomer.domain.model.OrderRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service

@Service
class DeleteOrderService(
    private val orderRepository: OrderRepository,
) {

    @CacheEvict(value = ["order"], key = "#id")
    fun deleteOrder(id: Long) {
        orderRepository.deleteById(id)
    }
}
