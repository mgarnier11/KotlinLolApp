package com.example.kotlinlolapp.core.rest.http

class HttpException(httpCode: Int, httpMessage: String) : Exception("HTTP $httpCode: $httpMessage")