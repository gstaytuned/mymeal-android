package com.staytuned.mo.mymeal.adapter

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.staytuned.mo.mymeal.R
import com.staytuned.mo.tngptutorial.networking.FeedBackResponse
import com.staytuned.mo.tngptutorial.networking.RegisterResponse
import kotlinx.android.synthetic.main.feed_back_item.view.*
import kotlinx.android.synthetic.main.food_list_item.view.*

class FeedBackAdapter(val list: ArrayList<FeedBackResponse.FeedBackResponse>, val activity: FragmentActivity?) : RecyclerView.Adapter<FeedBackAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.feed_back_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvDate.text = list.get(position).created_date
        holder.tvMessage.text = list.get(position).message
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMessage = view.tv_message
        val tvDate = view.tv_created_date
    }

}
