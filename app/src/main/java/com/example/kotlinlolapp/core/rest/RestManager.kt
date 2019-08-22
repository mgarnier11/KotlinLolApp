package com.example.kotlinlolapp.core.rest

import android.util.Log
import com.example.kotlinlolapp.core.dto.ChampionDto
import com.example.kotlinlolapp.core.rest.http.HttpClient
import com.example.kotlinlolapp.core.rest.http.HttpMethod
import com.example.kotlinlolapp.core.rest.http.HttpRequest
import com.example.kotlinlolapp.core.rest.http.HttpResponse
import com.example.kotlinlolapp.logic.KotlinLolApp
import com.example.kotlinlolapp.utils.*
import org.json.JSONArray
import java.lang.Exception
import java.net.URL


class SimpleRestManager {

    companion object {
        const val TAG: String = "RestManager"
    }

    private val httpClient by lazy { HttpClient() }
    private val rootUrl = "http://ddragon.leagueoflegends.com/"
    private val latestVersion = "9.16.1"

    private fun rootUrl(): String {
        return "${rootUrl}cdn/${KotlinLolApp.latestApiVersion}/data/fr_FR"
    }


    fun retrieveVersions(): HttpResponse<List<String>> {
        val response = httpClient.executeRequest<List<String>>(
            HttpRequest(
                HttpMethod.GET,
                URL("${rootUrl}api/versions.json")
            )
        )
        if (response is HttpResponse.Success) {
            try {
                val data = JSONArray(response.body)
                data?.let {
                    response.value = jsonStringsArrayToListStrings(it)?.map { version -> version }
                }
            } catch (ex: Exception) {
                Log.d("DEBUG", ex.toString(), ex)
            }
        }
        return response
    }

    fun retrieveChampions(): HttpResponse<List<ChampionDto>> {
        val response = httpClient.executeRequest<List<ChampionDto>>(
            HttpRequest(
                HttpMethod.GET,
                URL("${rootUrl()}/champion.json")
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

    fun retrieveChampion(id: String): HttpResponse<ChampionDto> {
        val response = httpClient.executeRequest<ChampionDto>(
            HttpRequest(
                HttpMethod.GET,
                URL("${rootUrl()}/champion/$id.json")
            )
        )
        if (response is HttpResponse.Success) {
            val data = dataObjectFromJson(response.body)
            data?.let {
                response.value = ChampionDto.fromJson(it.getJSONObject(id))
            } ?: Log.w(TAG, "Unable to parse data in json")
        }
        return response
    }

}