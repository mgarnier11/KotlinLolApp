package com.example.kotlinlolapp.logic

import android.net.Uri
import org.json.JSONObject

data class ImageEntity (
    var name: String?,
    var url: Uri?
) {
    fun getJsonObj() : JSONObject {
        val json =  JSONObject()

        json.put("name", name)
        json.put("url", url.toString())

        return json
    }
}