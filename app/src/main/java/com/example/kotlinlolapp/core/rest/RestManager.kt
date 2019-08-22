package com.example.kotlinlolapp.core.rest

import android.util.Log
import com.example.kotlinlolapp.core.dto.ChampionDto
import com.example.kotlinlolapp.core.rest.http.HttpClient
import com.example.kotlinlolapp.core.rest.http.HttpMethod
import com.example.kotlinlolapp.core.rest.http.HttpRequest
import com.example.kotlinlolapp.core.rest.http.HttpResponse
import com.example.kotlinlolapp.utils.dataArrayFromJson
import com.example.kotlinlolapp.utils.dataObjectFromJson
import com.example.kotlinlolapp.utils.jsonArrayFromData
import com.example.kotlinlolapp.utils.jsonToList
import java.lang.Exception
import java.net.URL


class SimpleRestManager {

    companion object {
        const val TAG: String = "RestManager"
    }

    private val httpClient by lazy { HttpClient() }
    private val rootUrl  = "http://ddragon.leagueoflegends.com/cdn/9.3.1/data/fr_FR"

    fun retrieveChampions(): HttpResponse<List<ChampionDto>> {
        val response = httpClient.executeRequest<List<ChampionDto>>(
            HttpRequest(
                HttpMethod.GET,
                URL("$rootUrl/champion.json")
            )
        )
        if (response is HttpResponse.Success) {
            try {
                val data = jsonArrayFromData(response.body)
                data?.let {
                    response.value = jsonToList(it)?.map { champion -> ChampionDto.fromJson(champion) }
                } ?: Log.w(TAG, "Unable to parse data in json")

            } catch (ex: Exception) {
                Log.d("DEBUG", ex.toString(), ex)
            }


        }
        return response
    }
/*
    fun retrieveChampion(id: String): HttpResponse<BreweryDTO> {
        val response = httpClient.executeRequest<BreweryDTO>(
            HttpRequest(
                HttpMethod.GET,
                URL("$rootUrl/breweries/$id")
            )
        )
        if (response is HttpResponse.Success) {
            val data = dataObjectFromJson(response.body)
            data?.let {
                response.value = BreweryDTO.fromJson(it)
            } ?: Log.w(TAG, "Unable to parse data in json")
        }
        return response
    }
    */
}