package com.example.kotlinlolapp.logic

data class ChampionEntity (
    var id: String?,
    var key: Int?,
    var name: String?,
    var title: String?,
    var attack: Int?,
    var defense: Int?,
    var magic: Int?,
    var difficulty: Int?,
    var lore: String?,
    var baseImage: ImageEntity?,
    var tags: List<String?>,
    var spells: List<SpellEntity?>,
    var skins: List<SkinEntity?>
) {
    override fun toString(): String {
        return "$name : $title"
    }
}