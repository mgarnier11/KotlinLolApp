package com.example.kotlinlolapp.core.rest


import android.util.Log
import com.example.kotlinlolapp.core.rest.http.HttpResponse
import com.example.kotlinlolapp.logic.ChampionEntity
import com.example.kotlinlolapp.logic.ResultWrapper

class ChampionsManager {
    val restManager: SimpleRestManager = SimpleRestManager()

    companion object {
        const val TAG = "ChampionsManager"
    }

    fun retrieveChampions(): ResultWrapper<List<ChampionEntity>> {
        Log.d(TAG, "Retrieving champions")
        return when (val responseDTO = restManager.retrieveChampions()) {
            is HttpResponse.Success -> ResultWrapper.Success(responseDTO.value?.map { it.toEntity() })
            is HttpResponse.Error -> ResultWrapper.Error(responseDTO.throwable)
        }
    }

    fun retrieveChampion(id: String): ResultWrapper<ChampionEntity> {
        Log.d(TAG, "Retrieving $id")
        return when (val responseDTO = restManager.retrieveChampion(id)) {
            is HttpResponse.Success -> ResultWrapper.Success(responseDTO.value?.toEntity())
            is HttpResponse.Error -> ResultWrapper.Error(responseDTO.throwable)
        }
    }
}