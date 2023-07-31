package com.example.marketcustomer.domain.service

import com.example.marketcustomer.domain.model.OrderRepository
import com.example.marketcustomer.domain.presentation.dto.OrderElement
import com.example.marketcustomer.domain.presentation.dto.OrderResponseList
import org.springframework.stereotype.Service

@Service
class QueryOrderService(
    private val orderRepository: OrderRepository,
) {

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
}