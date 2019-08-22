package com.example.kotlinlolapp.logic

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.kotlinlolapp.core.dto.ChampionDto
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import com.example.kotlinlolapp.core.rest.ChampionsManager
import com.example.kotlinlolapp.core.rest.VersionsManager
import com.example.kotlinlolapp.utils.executeOnBackground
import com.example.kotlinlolapp.utils.executeOnUi
import org.json.JSONObject


class KotlinLolApp {
    data class LoadingEvent(val value: Int, val maxValue: Int, val finished: Boolean)
    data class MessageEvent(val message: String)

    val championsManager: ChampionsManager = ChampionsManager()
    val versionsManager: VersionsManager = VersionsManager()

    var lstChampions: MutableList<ChampionEntity> = mutableListOf()

    private constructor()

    companion object {
        var latestApiVersion = ""

        val instance = KotlinLolApp()
    }

    fun initApp(context: Context) {
        executeOnBackground {
            val versionSharedPreferences = context.getSharedPreferences("version", Context.MODE_PRIVATE)
            val championsSharedPreferences = context.getSharedPreferences("champions", Context.MODE_PRIVATE)
            KotlinLolApp.latestApiVersion = versionSharedPreferences.getString("latest", "")
            val lastApiVersion = KotlinLolApp.latestApiVersion

            when (val result = versionsManager.retrieveVersions()) {
                is ResultWrapper.Success -> {
                    result.data?.let {
                        KotlinLolApp.latestApiVersion = it[0]

                        if (lastApiVersion !== KotlinLolApp.latestApiVersion) with(versionSharedPreferences.edit()) {
                            putString("latest", KotlinLolApp.latestApiVersion)
                            commit()
                        }
                    }
                }
                is ResultWrapper.Error -> {
                    Log.e("DEBUG", result.throwable.message)
                }
            }

            val championsIds = championsSharedPreferences.getStringSet("championsIds", null)

            if (championsIds == null || latestApiVersion != lastApiVersion) {
                getAllChampionsDatas(championsSharedPreferences)

                with(championsSharedPreferences.edit()) {
                    putStringSet("championsIds", lstChampions.map { it.id }.toSet())
                    commit()
                }
            } else {
                for ((index, championId) in championsIds.withIndex()) {
                    val championJson = championsSharedPreferences.getString(championId, null)

                    championJson?.let { lstChampions.add(ChampionDto.fromJson(JSONObject(it)).toEntity()) }

                    executeOnUi { publish(LoadingEvent(index, championsIds.size, false)) }
                }

                lstChampions.sortBy { it.name }

                executeOnUi { publish(LoadingEvent(championsIds.size, championsIds.size, true)) }
            }
        }
    }

    fun getAllChampionsDatas(champSharedPref: SharedPreferences) {
        when (val result = championsManager.retrieveChampions()) {
            is ResultWrapper.Success -> {
                result.data?.let {
                    for ((index, champion) in it.withIndex()) {
                        executeOnUi { publish(MessageEvent("Retrieving datas for ${champion.name}")) }

                        val fullDataChamp = getChampionAllDatas(champion)!!

                        lstChampions.add(fullDataChamp)

                        with(champSharedPref.edit()) {
                            putString(fullDataChamp.id, fullDataChamp.getJsonObj().toString())
                            commit()
                        }

                        executeOnUi { publish(LoadingEvent(index, it.size, false)) }
                        executeOnUi { publish(MessageEvent("Retrieved datas for ${champion.name}")) }
                    }

                    executeOnUi { publish(LoadingEvent(it.size, it.size, true)) }
                } ?: Log.w("DEBUG", "Unable to retrieve champions")
            }
            is ResultWrapper.Error -> {
                Log.e("DEBUG", result.throwable.message)
            }
        }
    }

    fun getChampionAllDatas(champion: ChampionEntity): ChampionEntity? {
        return when (val result = championsManager.retrieveChampion(champion.id!!)) {
            is ResultWrapper.Success -> result.data
            is ResultWrapper.Error -> {
                Log.e("DEBUG", result.throwable.message)
                null
            }
        }
    }

    private val publisher = PublishSubject.create<Any>()

    fun publish(event: Any) {
        publisher.onNext(event)
    }

    fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)


}