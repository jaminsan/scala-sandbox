package com.example.exception

abstract class ValidationException(val message: String) extends RuntimeException(message)
