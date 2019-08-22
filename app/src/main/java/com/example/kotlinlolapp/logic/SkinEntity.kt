package com.example.kotlinlolapp.logic

import org.json.JSONObject

data class SkinEntity(
    var id: String?,
    var name: String?,
    var num: Int?,
    var image: ImageEntity?
) {
    fun getJsonObj(): JSONObject {
        val json =  JSONObject()

        json.put("id", id)
        json.put("name", name)
        json.put("num", num)
        json.put("image", image?.getJsonObj().toString())

        return json
    }
}