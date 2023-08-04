package com.example.marketcustomer.domain.presentation

import com.example.marketcustomer.domain.model.OrderEntity
import com.example.marketcustomer.domain.presentation.dto.OrderElement
import com.example.marketcustomer.domain.presentation.dto.OrderResponseList
import com.example.marketcustomer.domain.service.DeleteOrderService
import com.example.marketcustomer.domain.service.QueryOrderService
import com.example.marketcustomer.domain.service.UpdateOrderService
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val queryOrderService: QueryOrderService,
    private val updateOrderService: UpdateOrderService,
    private val deleteOrderService: DeleteOrderService,
) {

    companion object {
        val logger: Logger = org.slf4j.LoggerFactory.getLogger(OrderController::class.java)
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/product")
    fun getNewProduct(): OrderResponseList {
        logger.info("get product list in MySQL DB")
        return queryOrderService.queryOrder()
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping("/product/{id}")
    fun getNewProductById(@PathVariable(name = "id") id: Long): OrderElement {
        logger.info("get product in MySQL DB")
        return queryOrderService.queryOrderById(id)
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping("/product/{id}")
    fun updateProduct(
        @PathVariable(name = "id") id: Long,
        @RequestParam(name = "name") name: String,
        @RequestParam(name = "price") price: Int,
    ): OrderElement {
        logger.info("update product in MySQL DB")
        return updateOrderService.updateOrder(id, name, price)
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/product/{id}")
    fun deleteProduct(@PathVariable(name = "id") id: Long) {
        logger.info("delete product in MySQL DB")
        deleteOrderService.deleteOrder(id)
    }
}
