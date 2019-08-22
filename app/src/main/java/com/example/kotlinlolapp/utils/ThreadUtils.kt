package com.example.kotlinlolapp.utils

import com.example.kotlinlolapp.core.concurrency.Executor
import com.example.kotlinlolapp.core.concurrency.Handler


internal inline fun executeOnBackground(noinline runnable: () -> Unit) {
    Executor.ioExecutorService.submit(runnable)
}

internal inline fun executeOnUi(noinline runnable: () -> Unit) {
    Handler.mainHandler.post(runnable)
}