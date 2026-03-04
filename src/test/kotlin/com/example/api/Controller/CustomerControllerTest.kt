package com.example.api.Controller

import com.example.api.Dto.CreateCustomerRequest
import com.example.api.Model.Customer
import com.example.api.Service.CustomerService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CustomerController::class)
class CustomerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var customerService: CustomerService

    @Test
    fun `post customers returns created`() {
        val request = CreateCustomerRequest("Alice", "123", "Pune")
        given(customerService.add(any(CreateCustomerRequest::class.java)))
            .willReturn(Customer(id = 1, name = "Alice", phno = "123", city = "Pune"))

        mockMvc.perform(
            post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.name").value("Alice"))
            .andExpect(jsonPath("$.city").value("Pune"))

        then(customerService).should().add(any(CreateCustomerRequest::class.java))
    }

    @Test
    fun `post customers with invalid body returns bad request`() {
        val invalidRequest = mapOf("name" to "", "phno" to "", "city" to "")

        mockMvc.perform(
            post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error").value("Validation failed"))
    }

    @Test
    fun `get customers returns list`() {
        given(customerService.get()).willReturn(listOf(Customer(id = 1, name = "Alice", phno = "123", city = "Pune")))

        mockMvc.perform(get("/customers"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value(1))

        then(customerService).should().get()
    }

    @Test
    fun `delete customer returns no content`() {
        mockMvc.perform(delete("/customers/1"))
            .andExpect(status().isNoContent)

        then(customerService).should().delete(1)
    }
}
