package com.example.kotlinlolapp.logic

import org.json.JSONObject

data class SpellEntity (
    var id: String?,
    var name: String?,
    var description: String?,
    var image: ImageEntity?
) {
    fun getJsonObj(): JSONObject {
        val json =  JSONObject()

        json.put("id", id)
        json.put("name", name)
        json.put("description", description)
        json.put("image", image?.getJsonObj().toString())

        return json
    }
}