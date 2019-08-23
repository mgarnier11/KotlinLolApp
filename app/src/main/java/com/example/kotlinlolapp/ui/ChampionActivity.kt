package com.example.kotlinlolapp.ui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.kotlinlolapp.R
import com.example.kotlinlolapp.logic.ChampionEntity
import com.example.kotlinlolapp.logic.KotlinLolApp
import com.squareup.picasso.Picasso



class ChampionActivity : AppCompatActivity() {

    val project = KotlinLolApp.instance
    lateinit var champion: ChampionEntity

    companion object {

        private val INTENT_CHAMPION_ID = "champion_id"

        fun newIntent(context: Context, champion: ChampionEntity): Intent {
            val intent = Intent(context, ChampionActivity::class.java)
            intent.putExtra(INTENT_CHAMPION_ID, champion.id)
            return intent
        }
    }



    lateinit var tvName: TextView
    lateinit var tvTitle: TextView
    lateinit var tvTags: TextView
    lateinit var tvLore: TextView
    lateinit var ivImage: ImageView

    lateinit var pbAttack: ProgressBar
    lateinit var pbDefense: ProgressBar
    lateinit var pbMagic: ProgressBar
    lateinit var pbDifficulty: ProgressBar


    lateinit var ivSpell1: ImageView
    lateinit var tvNameSpell1: TextView
    lateinit var tvDescriptionSpell1: TextView

    lateinit var ivSpell2: ImageView
    lateinit var tvNameSpell2: TextView
    lateinit var tvDescriptionSpell2: TextView

    lateinit var ivSpell3: ImageView
    lateinit var tvNameSpell3: TextView
    lateinit var tvDescriptionSpell3: TextView

    lateinit var ivSpell4: ImageView
    lateinit var tvNameSpell4: TextView
    lateinit var tvDescriptionSpell4: TextView

    val skinAdapter = SkinAdapter(listOf(), this)
    lateinit var rvSkins: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_champion)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Champion Details"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        val championId = intent.getStringExtra(INTENT_CHAMPION_ID)
            ?: throw IllegalStateException("field $INTENT_CHAMPION_ID missing in Intent")

        champion = project.getChampionById(championId)

        tvName = findViewById(R.id.activity_champion_tv_champion_name)
        tvTitle = findViewById(R.id.activity_champion_tv_champion_title)
        tvTags = findViewById(R.id.activity_champion_tv_champion_tags)
        tvLore = findViewById(R.id.activity_champion_tv_champion_lore)
        ivImage = findViewById(R.id.activity_champion_iv_champion_image)

        pbAttack = findViewById(R.id.activity_champion_pb_attack)
        pbDefense = findViewById(R.id.activity_champion_pb_defense)
        pbMagic = findViewById(R.id.activity_champion_pb_magic)
        pbDifficulty = findViewById(R.id.activity_champion_pb_difficulty)

        ivSpell1 = findViewById(R.id.activity_champion_iv_spell_1)
        tvNameSpell1 = findViewById(R.id.activity_champion_tv_spell_name_1)
        tvDescriptionSpell1 = findViewById(R.id.activity_champion_tv_spell_description_1)

        ivSpell2 = findViewById(R.id.activity_champion_iv_spell_2)
        tvNameSpell2 = findViewById(R.id.activity_champion_tv_spell_name_2)
        tvDescriptionSpell2 = findViewById(R.id.activity_champion_tv_spell_description_2)

        ivSpell3 = findViewById(R.id.activity_champion_iv_spell_3)
        tvNameSpell3 = findViewById(R.id.activity_champion_tv_spell_name_3)
        tvDescriptionSpell3 = findViewById(R.id.activity_champion_tv_spell_description_3)

        ivSpell4 = findViewById(R.id.activity_champion_iv_spell_4)
        tvNameSpell4 = findViewById(R.id.activity_champion_tv_spell_name_4)
        tvDescriptionSpell4 = findViewById(R.id.activity_champion_tv_spell_description_4)

        rvSkins = findViewById(R.id.activity_champion_rv_skins)

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvSkins.layoutManager = layoutManager


        rvSkins.adapter = skinAdapter

        reloadUi()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    fun reloadUi() {
        tvName.text = champion.name
        tvTitle.text = champion.title
        tvTags.text = champion.tags[0] + if (champion.tags.size > 1) " / " + champion.tags[1] else ""
        tvLore.text = champion.lore

        Picasso.get().load(champion.baseImage?.url).resize(126, 126).centerCrop().into(ivImage)

        pbAttack.progress = champion.attack!!
        pbDefense.progress = champion.defense!!
        pbMagic.progress = champion.magic!!
        pbDifficulty.progress = champion.difficulty!!



        Picasso.get().load(champion.spells[0]?.image?.url).resize(90,90).centerCrop().into(ivSpell1)
        tvNameSpell1.text = champion.spells[0]?.name
        tvDescriptionSpell1.text =  champion.spells[0]?.description

        Picasso.get().load(champion.spells[1]?.image?.url).resize(90,90).centerCrop().into(ivSpell2)
        tvNameSpell2.text = champion.spells[1]?.name
        tvDescriptionSpell2.text =  champion.spells[1]?.description

        Picasso.get().load(champion.spells[2]?.image?.url).resize(90,90).centerCrop().into(ivSpell3)
        tvNameSpell3.text = champion.spells[2]?.name
        tvDescriptionSpell3.text =  champion.spells[2]?.description

        Picasso.get().load(champion.spells[3]?.image?.url).resize(90,90).centerCrop().into(ivSpell4)
        tvNameSpell4.text = champion.spells[3]?.name
        tvDescriptionSpell4.text =  champion.spells[3]?.description

        skinAdapter.items = champion.skins

        skinAdapter.notifyDataSetChanged()

    }
}
