package com.example.api.Dto

class ErrorResponse(
    val error: String,
    val details: List<String> = emptyList()
)
