package com.example.kotlinlolapp.logic

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

data class ChampionEntity(
    var id: String?,
    var key: Int?,
    var name: String?,
    var title: String?,
    var attack: Int?,
    var defense: Int?,
    var magic: Int?,
    var difficulty: Int?,
    var lore: String?,
    var baseImage: ImageEntity?,
    var tags: List<String?>,
    var spells: List<SpellEntity?>,
    var skins: List<SkinEntity?>
) {
    override fun toString(): String {
        return "$name : $title"
    }

    fun getJsonObj(): JSONObject {
        val json = JSONObject()

        json.put("id", id)
        json.put("key", key)
        json.put("name", name)
        json.put("title", title)
        val info = JSONObject()
        info.put("attack", attack)
        info.put("defense", defense)
        info.put("magic", magic)
        info.put("difficulty", difficulty)
        json.put("info", info)
        json.put("lore", lore)
        json.put("baseImage", baseImage?.getJsonObj().toString())
        json.put("tags", JSONArray(tags))
        json.put("spells", JSONArray(spells.map { it?.getJsonObj().toString() }))
        json.put("skins", JSONArray(skins.map { it?.getJsonObj().toString() }))

        return json
    }
}