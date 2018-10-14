package com.staytuned.mo.mymeal.adapter

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.staytuned.mo.mymeal.R
import com.staytuned.mo.mymeal.TinyDB
import com.staytuned.mo.tngptutorial.networking.HistoryResponse
import kotlinx.android.synthetic.main.food_list_item.view.*

class HistoryAdapter(val list: ArrayList<HistoryResponse.HistoryListResponse>, val activity: FragmentActivity?) : RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.history_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var db = TinyDB(activity!!)
        if (db.getString("cust_id").equals(list.get(position).cust_selling_id)) {
            holder.detail.setTextColor(ContextCompat.getColor(activity,R.color.green))
            holder.total.setTextColor(ContextCompat.getColor(activity,R.color.green))
            holder.detail.text = "Someone purchased your " + list.get(position).name + " " + list.get(position).txn_quantity + " ea" + "\nFrom " + list.get(position).cust_name + " at " + list.get(position).created_date
            holder.total.text = "Total " + (Integer.parseInt((list.get(position).price)) * Integer.parseInt((list.get(position).txn_quantity))) + " Baht."
        } else {
            holder.detail.text = "You purchase " + list.get(position).name + " " + list.get(position).txn_quantity + " ea" + "\nFrom " + list.get(position).cust_name + " at " + list.get(position).created_date
            holder.total.text = "Total " + (Integer.parseInt((list.get(position).price)) * Integer.parseInt((list.get(position).txn_quantity))) + " Baht."
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val detail = view.tv_detail
        val total = view.tv_price
        val imvTheme = view.imv_theme

    }

}
