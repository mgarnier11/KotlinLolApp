package com.example.kotlinlolapp.core.dto


import android.util.Log
import com.example.kotlinlolapp.logic.ChampionEntity
import com.example.kotlinlolapp.logic.KotlinLolApp
import org.json.JSONObject
import java.lang.Exception

data class ChampionDto(
    val id: String?,
    val key: Int?,
    val name: String?,
    val title: String?,
    val attack: Int?,
    val defense: Int?,
    val magic: Int?,
    val difficulty: Int?,
    val lore: String?,
    val baseImage: ImageDto?,
    val tags: List<String?>,
    val spells: List<SpellDto?>,
    val skins: List<SkinDto?>
) {
    companion object {
        fun fromJson(jsonObject: JSONObject): ChampionDto {
            val tags: MutableList<String> = mutableListOf()
            val spells: MutableList<SpellDto> = mutableListOf()
            val skins: MutableList<SkinDto> = mutableListOf()

            try {
                val jsonTags = jsonObject.optJSONArray("tags")
                jsonTags?.let {
                    for (i in 0..(jsonTags.length() - 1)) {
                        tags.add(jsonTags.getString(i))
                    }
                }
                val jsonSpells = jsonObject.optJSONArray("spells")
                jsonSpells?.let {
                    for (i in 0..(jsonSpells.length() - 1)) {
                        spells.add(SpellDto.fromJson(JSONObject(jsonSpells.getString(i))))
                    }
                }
                val jsonSkins = jsonObject.optJSONArray("skins")
                jsonSkins?.let {
                    for (i in 0..(jsonSkins.length() - 1)) {
                        skins.add(SkinDto.fromJson(JSONObject(jsonSkins.getString(i)), jsonObject.getString("id")))
                    }
                }
            } catch (ex: Throwable) {
                Log.w("DEBUG", ex.toString(), ex)
            }


            return ChampionDto(
                id = jsonObject.getString("id"),
                key = jsonObject.getInt("key"),
                name = jsonObject.getString("name"),
                title = jsonObject.getString("title"),
                attack = jsonObject.getJSONObject("info").getInt("attack"),
                defense = jsonObject.getJSONObject("info").getInt("defense"),
                magic = jsonObject.getJSONObject("info").getInt("magic"),
                difficulty = jsonObject.getJSONObject("info").getInt("difficulty"),
                lore = jsonObject.optString("lore"),
                baseImage = ImageDto.fromApi(
                    "/${KotlinLolApp.latestApiVersion}/img/champion/",
                    jsonObject.getString("id") + ".png"
                ),
                tags = tags.toList(),
                spells = spells.toList(),
                skins = skins.toList()
            )

        }


    }

    fun toEntity(): ChampionEntity {
        return ChampionEntity(
            id,
            key,
            name,
            title,
            attack,
            defense,
            magic,
            difficulty,
            lore,
            baseImage = baseImage?.toEntity(),
            tags = tags,
            spells = spells.map { it?.toEntity() },
            skins = skins.map { it?.toEntity() }

        )
    }
}