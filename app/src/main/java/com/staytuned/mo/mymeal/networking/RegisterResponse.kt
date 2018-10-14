package com.staytuned.mo.tngptutorial.networking

import org.parceler.Parcel

class RegisterResponse(val token: String,val cust_name: String,val cust_id: String,val address:String,val food_list : List<foodListResponse>) {
    class foodListResponse(
            val selling_id: String,
            val name: String,
            val detail: String,
            val price: String,
            val cust_name: String,
            val food_img: String,
            val created_date: String,
            val quantity: String,
            val food_id: String
    )
}