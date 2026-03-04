package com.example.api.service

import com.example.api.dto.CreateCustomerRequest
import com.example.api.model.Customer
import com.example.api.repository.CustomerRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.NoSuchElementException
import java.util.Optional

class CustomerServiceTest {

    private val customerRepository = mockk<CustomerRepository>()
    private val customerService = CustomerService(customerRepository)

    @Test
    fun `add saves customer with all request fields`() {
        val request = CreateCustomerRequest("Alice", "123", "Pune")
        every { customerRepository.save(any()) } answers { firstArg() }

        val saved = customerService.add(request)

        assertEquals("Alice", saved.name)
        assertEquals("123", saved.phno)
        assertEquals("Pune", saved.city)
        verify(exactly = 1) { customerRepository.save(any()) }
    }

    @Test
    fun `delete throws when id does not exist`() {
        every { customerRepository.findById(42) } returns Optional.empty()

        assertThrows(NoSuchElementException::class.java) {
            customerService.delete(42)
        }

        verify(exactly = 1) { customerRepository.findById(42) }
        verify(exactly = 0) { customerRepository.delete(any()) }
    }

    @Test
    fun `delete removes customer when id exists`() {
        val customer = Customer(id = 2, name = "Bob", phno = "456", city = "Delhi")
        every { customerRepository.findById(2) } returns Optional.of(customer)
        every { customerRepository.delete(customer) } returns Unit

        customerService.delete(2)

        verify(exactly = 1) { customerRepository.delete(customer) }
    }

    @Test
    fun `get delegates to repository`() {
        val customers = listOf(Customer(id = 1, name = "Alice", phno = "123", city = "Pune"))
        every { customerRepository.findAll() } returns customers

        val result = customerService.get()

        assertEquals(customers, result)
        verify(exactly = 1) { customerRepository.findAll() }
    }
}
