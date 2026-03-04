package com.example.api.dto

import com.example.api.model.Customer

class CustomerResponse(
    val id: Int,
    val name: String,
    val phno: String,
    val city: String
)

fun Customer.toResponse(): CustomerResponse {
    return CustomerResponse(
        id = id ?: 0,
        name = name,
        phno = phno,
        city = city
    )
}
