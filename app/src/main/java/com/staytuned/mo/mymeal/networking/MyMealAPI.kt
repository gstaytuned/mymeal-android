package com.staytuned.mo.tngptutorial.networking

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RedditApi {
    @POST("/token")
    fun token(@Body params: MutableMap<String, String>)
            : Call<RegisterResponse>

    @POST("/register")
    fun register(@Body params: MutableMap<String, String>)
            : Call<RegisterResponse>

    @POST("/getUser")
    fun getUser(@Body params: MutableMap<String, String>)
            : Call<RegisterResponse>

    @POST("/buy")
    fun buy(@Body params: MutableMap<String, String>)
            : Call<RegisterResponse>

    @POST("/sell")
    fun sell(@Body params: MutableMap<String, String>)
            : Call<RegisterResponse>

    @POST("/login")
    fun login(@Body params: MutableMap<String, String>)
            : Call<RegisterResponse>

    @POST("/getFoodList")
    fun loadFoodList(@Body params: MutableMap<String, String>)
            : Call<RegisterResponse>

    @POST("/mySaleList")
    fun loadMySaleList(@Body params: MutableMap<String, String>)
            : Call<RegisterResponse>

    @POST("/getHistoryBuy")
    fun loadHistoryBuy(@Body params: MutableMap<String, String>)
            : Call<HistoryResponse>

    @POST("/getHistorySell")
    fun loadHistorySell(@Body params: MutableMap<String, String>)
            : Call<HistoryResponse>

    @POST("/getFeedBack")
    fun getFeedBack(@Body params: MutableMap<String, String>)
            : Call<FeedBackResponse>

    @POST("/review")
    fun review(@Body params: MutableMap<String, String>)
            : Call<FeedBackResponse>
}