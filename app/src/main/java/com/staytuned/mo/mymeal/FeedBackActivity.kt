package com.staytuned.mo.mymeal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.staytuned.mo.mymeal.adapter.FeedBackAdapter
import com.staytuned.mo.tngptutorial.networking.FeedBackResponse
import com.staytuned.mo.tngptutorial.networking.RegisterResponse
import com.staytuned.mo.tngptutorial.networking.RestAPI
import kotlinx.android.synthetic.main.activity_feed_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.widget.Toast
import android.widget.EditText


class FeedBackActivity : AppCompatActivity() {
    var foodId: String = "";
    val list: ArrayList<FeedBackResponse.FeedBackResponse> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back)

        foodId = intent.getStringExtra("food_id");
        fab_add.setOnClickListener {
            val builder = AlertDialog.Builder(this@FeedBackActivity)
            builder.setView(layoutInflater.inflate(R.layout.dialog_submit_feedback, null))
            builder.setMessage("What's on your mind ?")
            builder.setPositiveButton("Submit", DialogInterface.OnClickListener { dialog, id ->
                val edit = (dialog as AlertDialog).findViewById(R.id.tv_message) as EditText?
                if (!edit!!.text.toString().equals("")) {
                    submit(foodId, edit!!.text.toString());
                }
            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                //dialog.dismiss();
            })
            builder.show()
        }
        rv.adapter = FeedBackAdapter(list, this)
        rv.layoutManager = LinearLayoutManager(this!!)
        loadFeedBack(foodId)
    }

    private fun loadFeedBack(foodId: String) {
        var requestBody: MutableMap<String, String> = mutableMapOf()

        requestBody.put("food_id", foodId)
        RestAPI().create().getFeedBack(requestBody).enqueue(object : Callback<FeedBackResponse> {
            override fun onResponse(call: Call<FeedBackResponse>?, response: Response<FeedBackResponse>?) {
                if (response!!.isSuccessful) {
                    list.clear()

                    list.addAll(response.body()!!.feed_back)
                    rv.adapter.notifyDataSetChanged()

                }

            }

            override fun onFailure(call: Call<FeedBackResponse>?, t: Throwable?) {
                Log.d("ger", t.toString())
            }

        })
    }

    private fun submit(foodId: String, message: String) {
        var requestBody: MutableMap<String, String> = mutableMapOf()

        requestBody.put("food_id", foodId)
        requestBody.put("message", message)
        RestAPI().create().review(requestBody).enqueue(object : Callback<FeedBackResponse> {
            override fun onResponse(call: Call<FeedBackResponse>?, response: Response<FeedBackResponse>?) {
                loadFeedBack(foodId)
            }

            override fun onFailure(call: Call<FeedBackResponse>?, t: Throwable?) {
                Log.d("ger", t.toString())
            }

        })
    }
}
