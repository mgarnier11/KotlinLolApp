package com.example.kotlinlolapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.example.kotlinlolapp.R
import com.example.kotlinlolapp.logic.KotlinLolApp
import com.example.kotlinlolapp.logic.ResultWrapper
import com.example.kotlinlolapp.utils.executeOnBackground
import com.example.kotlinlolapp.utils.executeOnUi
import android.support.v7.widget.DividerItemDecoration



class MainActivity : AppCompatActivity() {

    val project = KotlinLolApp.instance

    val championAdapter = ChampionAdapter(listOf(), this)
    lateinit var rvChampions: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvChampions = findViewById(R.id.activity_main_rv_champions)

        rvChampions.layoutManager = LinearLayoutManager(this)
        //rvChampions.addItemDecoration(DividerItemDecoration( rvChampions.getContext(), requestedOrientation))

        rvChampions.adapter = championAdapter

        getAllChampions()
    }

    fun getAllChampions() {
        executeOnBackground {
            // Retrieve breweries
            when (val result = project.championsManager.retrieveChampions()) {
                is ResultWrapper.Success -> {
                    executeOnUi {
                        result.data?.let {
                            championAdapter.items = it
                            championAdapter.notifyDataSetChanged()

                        } ?: Log.w("DEBUG", "Unable to retrieve champions")
                    }
                }
                is ResultWrapper.Error -> {
                    Log.e("DEBUG", result.throwable.message)
                }
            }
        }
    }
}
