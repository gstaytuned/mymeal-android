package com.staytuned.mo.mymeal

import android.app.Activity
import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.staytuned.mo.tngptutorial.networking.RegisterResponse
import com.staytuned.mo.tngptutorial.networking.RestAPI
import kotlinx.android.synthetic.main.activity_selling_food_form.*
import kotlinx.android.synthetic.main.fragment_order.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellingFoodFormActivity : AppCompatActivity() {
    var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selling_food_form)
        sell_button.setOnClickListener {

            validateData()
        }
    }

    private fun validateData() {

        if (text_input_food_name.text.isNotEmpty() && text_input_food_detail.text.isNotEmpty()
                && text_input_food_image_url.text.isNotEmpty() && text_input_food_image_url.text.isNotEmpty()
                && text_input_price.text.isNotEmpty()
                && text_input_quantity.text.isNotEmpty()) {
            var db = TinyDB(this!!)
            var requestBody: MutableMap<String, String> = mutableMapOf()
            requestBody.put("cust_id", db.getString("cust_id"))
            requestBody.put("food_name", text_input_food_name.text.toString())
            requestBody.put("detail", text_input_food_detail.text.toString())
            requestBody.put("amount", text_input_price.text.toString())
            requestBody.put("quantity", text_input_quantity.text.toString())
            requestBody.put("food_img", text_input_food_image_url.text.toString())
            sell(requestBody)
        } else {
            Toast.makeText(this, "Please fill all form", Toast.LENGTH_LONG).show();
        }

    }

    private fun sell(requestBody: MutableMap<String, String>) {
        progressDialog = ProgressDialog.show(this!!, "Info", "Loading...", true, false);
        RestAPI().create().sell(requestBody).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>?, response: Response<RegisterResponse>?) {
                progressDialog?.dismiss()
                if (response!!.isSuccessful) {
                    setResult(Activity.RESULT_OK);
                    finish();
                }

            }

            override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {
                progressDialog?.dismiss()
                Log.d("ger", t.toString())
            }

        })
    }

}
