package com.example.marketcustomer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class MarketCustomerApplication

fun main(args: Array<String>) {
    runApplication<MarketCustomerApplication>(*args)
}
