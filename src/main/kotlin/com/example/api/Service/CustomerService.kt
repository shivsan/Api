package com.example.api.Service

import com.example.api.Dto.CreateCustomerRequest
import com.example.api.Model.Customer
import com.example.api.Repository.CustomerRepository
import org.springframework.stereotype.Service
import java.util.NoSuchElementException

@Service
class CustomerService(private val customerRepository: CustomerRepository) {

    fun add(request: CreateCustomerRequest): Customer {
        val customer = Customer(name = request.name, phno = request.phno, city = request.city)
        return customerRepository.save(customer)
    }

    fun get(): Iterable<Customer> {
        return customerRepository.findAll()
    }

    fun delete(id: Int) {
        if (!customerRepository.existsById(id)) {
            throw NoSuchElementException("Customer with id $id was not found")
        }
        customerRepository.deleteById(id)
    }
}
