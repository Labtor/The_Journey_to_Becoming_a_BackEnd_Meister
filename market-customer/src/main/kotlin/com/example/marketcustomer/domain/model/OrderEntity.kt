package com.example.marketcustomer.domain.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_order")
class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    name: String,

    price: Int,
) {

    var name: String = name
        protected set

    var price: Int = price
        protected set

    fun updateGit(name: String, price: Int) {
        this.name = name
        this.price = price
    }
}
