package com.example.marketcustomer.global.consumer

import com.example.marketcustomer.domain.model.OrderEntity
import com.example.marketcustomer.domain.service.SaveOrderService
import com.google.gson.Gson
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer(
    private val saveOrderService: SaveOrderService,
) {

    @KafkaListener(topics = ["NewTopic"], groupId = "group_id")
    fun consume(message: String) {
        val gson = Gson()
        val orderEntity = gson.fromJson(message, OrderEntity::class.java)
        saveOrderService.saveOrder(orderEntity)
    }
}