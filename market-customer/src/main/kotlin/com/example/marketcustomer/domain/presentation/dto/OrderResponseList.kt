package com.example.marketcustomer.domain.presentation.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class OrderResponseList @JsonCreator constructor(
    @JsonProperty("orders") val orders: List<OrderElement>,
)

data class OrderElement @JsonCreator constructor(
    @JsonProperty("id") val id: Long,
    @JsonProperty("name") val name: String,
    @JsonProperty("price") val price: Int
)
