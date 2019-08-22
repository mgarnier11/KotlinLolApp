package com.example.kotlinlolapp.core.dto

import android.net.Uri
import com.example.kotlinlolapp.logic.ImageEntity
import org.json.JSONObject


data class ImageDto(
    val name: String?,
    val url: Uri?
) {

    companion object {
        private val imagesRootuUrl = "http://ddragon.leagueoflegends.com/cdn"

        fun fromApi(imagePath: String, imageName: String): ImageDto =
            ImageDto(
                name = imageName,
                url = Uri.parse(imagesRootuUrl + imagePath + imageName)
            )

        fun fromJson(jsonObject: JSONObject): ImageDto = ImageDto(
            name = jsonObject.getString("name"),
            url = Uri.parse(jsonObject.getString("url"))
        )
    }

    fun toEntity(): ImageEntity {
        return ImageEntity(
            name,
            url
        )
    }
}