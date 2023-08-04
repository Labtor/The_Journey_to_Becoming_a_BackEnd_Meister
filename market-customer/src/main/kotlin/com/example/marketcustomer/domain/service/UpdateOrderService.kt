package com.example.marketcustomer.domain.service

import com.example.marketcustomer.domain.model.OrderEntity
import com.example.marketcustomer.domain.model.OrderRepository
import com.example.marketcustomer.domain.presentation.dto.OrderElement
import org.springframework.cache.annotation.CachePut
import org.springframework.stereotype.Service

@Service
class UpdateOrderService(
    private val orderRepository: OrderRepository,
) {

    @CachePut(value = ["order"], key = "#id")
    fun updateOrder(id: Long, name: String, price: Int): OrderElement {

        val order = orderRepository.findById(id)
            .orElseThrow { throw Exception("Order not found") }

        order.updateGit(name, price)

        orderRepository.save(order)

        return OrderElement(
            id = order.id,
            name = order.name,
            price = order.price,
        )
    }
}