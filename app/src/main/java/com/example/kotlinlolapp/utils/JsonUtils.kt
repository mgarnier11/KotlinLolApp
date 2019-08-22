package com.example.kotlinlolapp.utils

import org.json.JSONArray
import org.json.JSONObject

/**
 * Transforms a [JSONArray] to a [List] of [JSONObject].
 * @param jsonArray The array to transform into List.
 */
internal fun jsonToList(jsonArray: JSONArray): List<JSONObject>? =
    jsonArray.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } }

/**
 * Retrieve "data" array from a json as String.
 * @param json The json as String.
 */
internal fun dataArrayFromJson(json: String): JSONArray? =
    JSONArray(json)

/**
 * Retrieve "data" array from a json as String.
 * @param json The json as String.
 */
internal fun jsonArrayFromData(json: String): JSONArray? {
    val obj = JSONObject(json).getJSONObject("data")
    val i = obj.keys()
    val returnArray = JSONArray()

    while(i.hasNext()){
        val key = i.next()
        returnArray.put(obj.get(key))
    }

    return returnArray
}

/**
 * Retrieve "data" object from a json as String.
 * @param json The json as String.
 */
internal fun dataObjectFromJson(json: String): JSONObject? =
    JSONObject(json).getJSONObject("data")