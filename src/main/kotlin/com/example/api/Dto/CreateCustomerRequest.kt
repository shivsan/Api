package com.example.api.Dto

import javax.validation.constraints.NotBlank

class CreateCustomerRequest(
    @field:NotBlank(message = "name is required")
    val name: String,
    @field:NotBlank(message = "phno is required")
    val phno: String,
    @field:NotBlank(message = "city is required")
    val city: String
)
