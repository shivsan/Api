package com.example.api.Service

import com.example.api.Dto.CreateCustomerRequest
import com.example.api.Model.Customer
import com.example.api.Repository.CustomerRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.NoSuchElementException

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
        every { customerRepository.existsById(42) } returns false

        assertThrows(NoSuchElementException::class.java) {
            customerService.delete(42)
        }

        verify(exactly = 1) { customerRepository.existsById(42) }
        verify(exactly = 0) { customerRepository.deleteById(any()) }
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
