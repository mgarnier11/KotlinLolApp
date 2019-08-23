package com.example.kotlinlolapp.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinlolapp.R
import com.example.kotlinlolapp.logic.SkinEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.skin_list_item.view.*


class SkinAdapter(var items : List<SkinEntity?>, val context: Context) : RecyclerView.Adapter<SkinAdapter.ViewHolder>() {
    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.skin_list_item, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val skin = items[position]

        //holder.itemView.vis
        holder?.tvName?.text = skin?.name
        Picasso.get().load(skin?.image?.url).resize(308, 560).centerCrop().into(holder?.ivSkin)
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.skin_list_item_tv_name
        val ivSkin = view.skin_list_item_iv_skin

    }
}

