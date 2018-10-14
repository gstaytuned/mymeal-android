package com.staytuned.mo.mymeal.adapter

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.staytuned.mo.mymeal.R
import com.staytuned.mo.tngptutorial.networking.RegisterResponse
import kotlinx.android.synthetic.main.food_list_item.view.*

class SaleFoodAdapter(val list: ArrayList<RegisterResponse.foodListResponse>, val activity: FragmentActivity?) : RecyclerView.Adapter<SaleFoodAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.sale_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.food_name.text = list.get(position).name
        holder.food_detail.text = list.get(position).detail
        holder.seller.text = "Seller: " + list.get(position).cust_name
        holder.quantity.text = "Quantity: " + list.get(position).quantity
        holder.price.text = list.get(position).price + " Baht"
        Glide.with(activity!!).load(list.get(position).food_img).into(holder.food_image)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val food_name = view.tv_name
        val food_image = view.image
        val food_detail = view.tv_detail
        val seller = view.tv_seller
        val price = view.tv_price
        val quantity = view.tv_quantity
    }

}
