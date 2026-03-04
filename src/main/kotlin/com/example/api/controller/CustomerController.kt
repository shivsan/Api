package com.example.api.controller

import com.example.api.dto.CreateCustomerRequest
import com.example.api.dto.CustomerResponse
import com.example.api.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/customers")
class CustomerController(private val customerService: CustomerService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addNewCustomer(@Valid @RequestBody request: CreateCustomerRequest): CustomerResponse {
        return customerService.add(request).toResponse()
    }

    @GetMapping
    fun getCustomers(): List<CustomerResponse> {
        return customerService.get().map { it.toResponse() }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@PathVariable id: Int) {
        customerService.delete(id)
    }
}
