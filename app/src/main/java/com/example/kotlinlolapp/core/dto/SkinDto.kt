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

        fun fromJson(jsonObject: JSONObject, championId: String ): SkinDto =
            SkinDto(
                id = jsonObject.getString("id"),
                name = jsonObject.getString("name"),
                num = jsonObject.getInt("num"),
                image  = ImageDto.fromJson("/img/champion/loading/", championId + "_" + jsonObject.getInt("num")+ ".jpg")
            )
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