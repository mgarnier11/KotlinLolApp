package com.example.kotlinlolapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.kotlinlolapp.R
import com.example.kotlinlolapp.logic.KotlinLolApp
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    val project = KotlinLolApp.instance

    val championAdapter = ChampionAdapter(project.lstChampions, this)
    lateinit var rvChampions: RecyclerView


    var filters: MutableList<Pair<String, Boolean>> = mutableListOf(
        Pair("Assassin", true),
        Pair("Support", true),
        Pair("Fighter", true),
        Pair("Marksman", true),
        Pair("Mage", true),
        Pair("Tank", true)
    )

    fun swapFilter(name: String): Boolean {
        val tmp = filters.find{ i -> i.first == name }!!

        filters.remove(tmp)
        filters.add(Pair(tmp.first, !tmp.second))

        return !tmp.second
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvChampions = findViewById(R.id.activity_main_rv_champions)

        val layoutManager = LinearLayoutManager(this)
        rvChampions.layoutManager = layoutManager
        rvChampions.addItemDecoration(DividerItemDecoration(rvChampions.getContext(), layoutManager.orientation))

        championAdapter.onItemClick = { champion ->
            startActivity(ChampionActivity.newIntent(this, champion))
        }

        rvChampions.adapter = championAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater = MenuInflater(this)

        inflater.inflate(R.menu.menu_main_activity, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_main_activity_support -> {
                item.setChecked(!item.isChecked)
                swapFilter("Support")

            }
            R.id.menu_main_activity_marksman -> {
                item.setChecked(!item.isChecked)
                swapFilter("Marksman")

            }
            R.id.menu_main_activity_assassin -> {
                item.setChecked(!item.isChecked)
                swapFilter("Assassin")

            }
            R.id.menu_main_activity_mage -> {
                item.setChecked(!item.isChecked)
                swapFilter("Mage")

            }
            R.id.menu_main_activity_tank -> {
                item.setChecked(!item.isChecked)
                swapFilter("Tank")

            }
            R.id.menu_main_activity_fighter -> {
                item.setChecked(!item.isChecked)
                swapFilter("Fighter")
            }
            else -> return super.onOptionsItemSelected(item)
        }

        championAdapter.items = project.lstChampions.filter{
            var displayed = false

            it.tags.forEach {
                if (filters.contains(Pair(it, true))) displayed = true
            }

            displayed
        }


        championAdapter.notifyDataSetChanged()

        return true
    }
}

/*


<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:app="http://schemas.android.com/apk/res-auto" xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:title="Support" android:id="@+id/menu_main_activity_support" android:checkable="true"/>
    <item android:title="Marksman" android:id="@+id/menu_main_activity_marksman" android:checkable="true"/>
    <item android:title="Assassin" android:id="@+id/menu_main_activity_assassin" android:checkable="true"/>
    <item android:title="Mage" android:id="@+id/menu_main_activity_mage" android:checkable="true"/>
    <item android:title="Tank" android:id="@+id/menu_main_activity_tank" android:checkable="true"/>
    <item android:checkable="true" android:id="@+id/menu_main_activity_fighter" android:title="Fighter"/>
</menu>
 */
