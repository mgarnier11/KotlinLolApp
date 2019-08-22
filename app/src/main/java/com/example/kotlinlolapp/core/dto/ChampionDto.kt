package com.example.kotlinlolapp.core.dto


import com.example.kotlinlolapp.logic.ChampionEntity
import org.json.JSONObject

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
            val tags = jsonObject.optJSONArray("tags")
            val spells = jsonObject.optJSONArray("spells")
            val skins = jsonObject.optJSONArray("skins")


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
                baseImage = ImageDto.fromJson("/9.3.1/img/champion/", jsonObject.getString("id") + ".png"),
                tags = if (tags !== null) List(tags.length()) { tags.optString(it) } else listOf(),
                spells = if (spells !== null) List(spells.length()) { SpellDto.fromJson(spells.optJSONObject(it)) } else listOf(),
                skins = if (skins !== null) List(skins.length()) { SkinDto.fromJson(skins.optJSONObject(it), jsonObject.getString("id")) } else listOf()
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
            spells = spells.map{ it?.toEntity() },
            skins = skins.map{ it?.toEntity() }

        )
    }
}