package com.example.kotlinlolapp.core.concurrency

import android.os.Handler
import android.os.Looper

object Handler {

    val mainHandler = Handler(Looper.getMainLooper())

}