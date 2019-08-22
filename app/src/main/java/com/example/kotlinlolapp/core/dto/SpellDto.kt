package com.example.kotlinlolapp.core.dto

import com.example.kotlinlolapp.logic.SpellEntity
import org.json.JSONObject


data class SpellDto(
    val id: String?,
    val name: String?,
    val description: String?,
    val image: ImageDto?
) {
    companion object {

        fun fromJson(jsonObject: JSONObject): SpellDto =
            SpellDto(
                id = jsonObject.getString("id"),
                name = jsonObject.getString("name"),
                description = jsonObject.getString("description"),
                image  = ImageDto.fromJson("/9.3.1/img/spell/", jsonObject.getJSONObject("image").getString("full"))
            )
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