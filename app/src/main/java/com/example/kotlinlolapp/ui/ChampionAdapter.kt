package com.example.kotlinlolapp.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinlolapp.R
import com.example.kotlinlolapp.logic.ChampionEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.champion_list_item.view.*

class ChampionAdapter(var items : List<ChampionEntity>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

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
        holder?.tvName?.text = items[position].name
        holder?.tvTitle?.text = items[position].title
        holder?.tvTags?.text =  items[position].tags[0] + if (items[position].tags.size > 1) " / " + items[position].tags[1] else ""
        Picasso.get().load(items[position].baseImage?.url).resize(126, 126).centerCrop().into(holder?.ivImage)
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvName = view.champion_list_item_tv_champion_name
    var tvTitle = view.champion_list_item_tv_champion_title
    var tvTags = view.champion_list_item_tv_champion_tags
    var ivImage = view.champion_list_item_iv_champion_image
}