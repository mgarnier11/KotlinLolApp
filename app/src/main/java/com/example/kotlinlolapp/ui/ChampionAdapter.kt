package com.example.kotlinlolapp.ui

import android.content.Context
import android.opengl.Visibility
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinlolapp.R
import com.example.kotlinlolapp.logic.ChampionEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.champion_list_item.view.*
import kotlin.reflect.jvm.internal.impl.utils.DFS

class ChampionAdapter(var items : List<ChampionEntity>, val context: Context) : RecyclerView.Adapter<ChampionAdapter.ViewHolder>() {
    var onItemClick: ((ChampionEntity) -> Unit)? = null


    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.champion_list_item, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val champion = items[position]

        //holder.itemView.vis
        holder?.tvName?.text = champion.name
        holder?.tvTitle?.text = champion.title
        holder?.tvTags?.text =  champion.tags[0] + if (champion.tags.size > 1) " / " + champion.tags[1] else ""
        Picasso.get().load(champion.baseImage?.url).resize(126, 126).centerCrop().into(holder?.ivImage)
    }

    fun filter(filters: List<Pair<String, Boolean>>) {

    }



    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val layout = view.champion_list_item_constraint_layout

        val tvName = view.champion_list_item_tv_champion_name
        var tvTitle = view.champion_list_item_tv_champion_title
        var tvTags = view.champion_list_item_tv_champion_tags
        var ivImage = view.champion_list_item_iv_champion_image
        init {
            view.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }


    }
}

