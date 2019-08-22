package com.example.kotlinlolapp.core.rest


import android.util.Log
import com.example.kotlinlolapp.core.rest.http.HttpResponse
import com.example.kotlinlolapp.logic.ChampionEntity
import com.example.kotlinlolapp.logic.ResultWrapper

class ChampionsManager {
    val restManager: SimpleRestManager = SimpleRestManager()

    companion object {
        const val TAG = "BreweryManager"
    }

    fun retrieveChampions(): ResultWrapper<List<ChampionEntity>> {
        Log.d(TAG, "Retrieving breweries")
        return when (val responseDTO = restManager.retrieveChampions()) {
            is HttpResponse.Success -> ResultWrapper.Success(responseDTO.value?.map { it.toEntity() })
            is HttpResponse.Error -> ResultWrapper.Error(responseDTO.throwable)
        }
    }

    /*

    fun retrieveBrewery(id: Int): ResultWrapper<BreweryEntity> {
        Log.d(TAG, "Retrieving brewery with id $id")
        return when (val responseDTO = restManager.retrieveBrewerie(id)) {
            is HttpResponse.Success -> ResultWrapper.Success(responseDTO.value?.toEntity())
            is HttpResponse.Error -> ResultWrapper.Error(responseDTO.throwable)
        }
    }
*/
}