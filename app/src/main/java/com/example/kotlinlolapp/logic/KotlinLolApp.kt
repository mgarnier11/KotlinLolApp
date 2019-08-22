package com.example.kotlinlolapp.logic

import com.example.kotlinlolapp.core.rest.ChampionsManager

class KotlinLolApp {
    val championsManager: ChampionsManager = ChampionsManager()

    private constructor()

    companion object {
        val instance = KotlinLolApp()
    }
}