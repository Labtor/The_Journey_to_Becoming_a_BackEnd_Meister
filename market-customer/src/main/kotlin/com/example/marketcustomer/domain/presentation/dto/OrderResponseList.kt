package com.example.marketcustomer.domain.presentation.dto

data class OrderResponseList(
    val orders: List<OrderElement>,
)

data class OrderElement(
    val id: Long,
    val name: String,
    val price: Int,
)
