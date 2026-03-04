package com.example.api.dto

class ErrorResponse(
    val error: String,
    val details: List<String> = emptyList()
)
