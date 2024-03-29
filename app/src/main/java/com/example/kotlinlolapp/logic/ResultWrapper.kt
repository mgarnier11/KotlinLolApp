package com.example.kotlinlolapp.logic


sealed class ResultWrapper<out T : Any> {
    data class Success<T : Any>(var data: T?) : ResultWrapper<T>()

    data class Error(val throwable: Throwable) : ResultWrapper<Nothing>()
}