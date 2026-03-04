package com.example.api.service

import com.example.api.dto.CreateCustomerRequest
import com.example.api.model.Customer
import com.example.api.repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.NoSuchElementException

@Service
class CustomerService(private val customerRepository: CustomerRepository) {

    fun add(request: CreateCustomerRequest): Customer {
        val customer = Customer(name = request.name, phno = request.phno, city = request.city)
        return customerRepository.save(customer)
    }

    fun get(): List<Customer> {
        return customerRepository.findAll().toList()
    }

    fun delete(id: Int) {
        val customer = customerRepository.findById(id)
            .orElseThrow { NoSuchElementException("Customer with id $id was not found") }
        customerRepository.delete(customer)
    }
}
