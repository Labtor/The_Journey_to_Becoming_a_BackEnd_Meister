package com.example.marketcustomer.domain.service

import com.example.marketcustomer.domain.model.OrderRepository
import com.example.marketcustomer.domain.presentation.dto.OrderElement
import com.example.marketcustomer.domain.presentation.dto.OrderResponseList
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class QueryOrderService(
    private val orderRepository: OrderRepository,
) {

    @Cacheable(value = ["order"])
    fun queryOrder(): OrderResponseList {
        val orders = orderRepository.findAll()
            .map {
                OrderElement(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                )
            }

        return OrderResponseList(
            orders = orders,
        )
    }

    @Cacheable(value = ["order"], key = "#id")
    fun queryOrderById(id: Long): OrderElement {
        return orderRepository.findById(id)
            .map {
                OrderElement(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                )
            }
            .orElseThrow { throw Exception("Order not found") }
    }
}