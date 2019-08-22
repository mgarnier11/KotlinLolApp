package com.example.kotlinlolapp.core.rest

import android.util.Log
import com.example.kotlinlolapp.core.rest.http.HttpResponse
import com.example.kotlinlolapp.logic.ResultWrapper


class VersionsManager {
    val restManager: SimpleRestManager = SimpleRestManager()

    companion object {
        const val TAG = "VersionsManager"
    }

    fun retrieveVersions(): ResultWrapper<List<String>> {
        Log.d(TAG, "Retrieving versions")
        return when (val responseDTO = restManager.retrieveVersions()) {
            is HttpResponse.Success -> ResultWrapper.Success(responseDTO.value)
            is HttpResponse.Error -> ResultWrapper.Error(responseDTO.throwable)
        }
    }
}