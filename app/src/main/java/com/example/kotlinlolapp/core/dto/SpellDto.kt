package com.example.kotlinlolapp.core.dto

import com.example.kotlinlolapp.logic.KotlinLolApp
import com.example.kotlinlolapp.logic.SpellEntity
import org.json.JSONObject


data class SpellDto(
    val id: String?,
    val name: String?,
    val description: String?,
    val image: ImageDto?
) {
    companion object {

        fun fromJson(jsonObject: JSONObject): SpellDto {
            val imageObj = JSONObject(jsonObject.getString("image"))

            return SpellDto(
                id = jsonObject.getString("id"),
                name = jsonObject.getString("name"),
                description = jsonObject.getString("description"),
                image = if (imageObj.optString("full", null) != null)
                    ImageDto.fromApi(
                        "/${KotlinLolApp.latestApiVersion}/img/spell/",
                        imageObj.getString("full")
                    )
                else ImageDto.fromJson(imageObj)
            )
        }

    }

    fun toEntity(): SpellEntity {
        return SpellEntity(
            id,
            name,
            description,
            image = image?.toEntity()
        )
    }
}