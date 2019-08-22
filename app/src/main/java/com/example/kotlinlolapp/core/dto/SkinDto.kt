package com.example.kotlinlolapp.core.dto

import com.example.kotlinlolapp.logic.SkinEntity
import org.json.JSONObject


data class SkinDto(
    val id: String?,
    val name: String?,
    val num: Int?,
    val image: ImageDto?
) {
    companion object {

        fun fromJson(jsonObject: JSONObject, championId: String): SkinDto {
            val imgString = jsonObject.optString("image", null)

            return SkinDto(
                id = jsonObject.getString("id"),
                name = jsonObject.getString("name"),
                num = jsonObject.getInt("num"),
                image = if (imgString == null) ImageDto.fromApi(
                    "/img/champion/loading/",
                    championId + "_" + jsonObject.getInt("num") + ".jpg"
                ) else ImageDto.fromJson(JSONObject(imgString))
            )
        }

    }

    fun toEntity(): SkinEntity {
        return SkinEntity(
            id,
            name,
            num,
            image = image?.toEntity()
        )
    }
}