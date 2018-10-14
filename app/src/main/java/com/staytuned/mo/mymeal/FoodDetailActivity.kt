package com.staytuned.mo.mymeal

import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.staytuned.mo.tngptutorial.networking.RegisterResponse
import com.staytuned.mo.tngptutorial.networking.RestAPI
import kotlinx.android.synthetic.main.activity_food_detail.*
import kotlinx.android.synthetic.main.fragment_order.*
import org.parceler.Parcels
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.design.widget.Snackbar



class FoodDetailActivity : AppCompatActivity() {
    var progressDialog: ProgressDialog? = null
    private var handler: Handler? = null
    private var runnable: Runnable? = null
    var name: String = ""
    var detail: String = ""
    var price: String = ""
    var image: String = ""
    var quantity: String = ""
    var foodName: String = ""
    var address: String = ""
    var sellingId: String = ""
    var foodId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)
        loadUser()
        name = intent.getStringExtra("name");
        detail = intent.getStringExtra("detail");
        price = intent.getStringExtra("price");
        quantity = intent.getStringExtra("quantity");
        image = intent.getStringExtra("image");
        foodName = intent.getStringExtra("food_name");
        sellingId = intent.getStringExtra("selling_id");
        foodId = intent.getStringExtra("food_id");

        Glide.with(this!!).load(image).into(iv_food)
        tv_name.text = foodName
        tv_detail.text = detail
        tv_price.text = price + " Baht"
        tv_quantity.text = quantity + " Item remaining"

        tv_feedback.setOnClickListener {
            val intent = Intent(this, FeedBackActivity::class.java)
            intent.putExtra("food_id",foodId)
            startActivityForResult(intent,666)
        }
        button_buy.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirm your purchase")
            builder.setMessage("Do you want to buy one " + foodName + " for " + price + " Baht?\n"+"Shipping address " +address)
            builder.apply {
                setPositiveButton("Buy",
                        DialogInterface.OnClickListener { dialog, id ->
                            // User clicked OK button
                            buy()
                        })
                setNegativeButton("Cancel ",
                        DialogInterface.OnClickListener { dialog, id ->
                            // User cancelled the dialog
                        })
            }
            builder.create().show()
        }

    }
    private fun loadUser() {
        progressDialog = ProgressDialog.show(this, "Info", "Loading...", true, false);
        var db = TinyDB(this!!)
        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody.put("cust_id", db.getString("cust_id"))
        RestAPI().create().getUser(requestBody).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>?, response: Response<RegisterResponse>?) {
                if (response!!.isSuccessful) {
                    address = response.body()!!.address;
                }
                progressDialog?.dismiss()

            }

            override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {
                Log.d("ger", t.toString())
                progressDialog?.dismiss()
            }

        })

    }

    private fun buy() {
        progressDialog = ProgressDialog.show(this, "Info", "Loading...", true, false);
        var db = TinyDB(this!!)
        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody.put("cust_id", db.getString("cust_id"))
        requestBody.put("selling_id", sellingId)
        requestBody.put("amount", price)
        RestAPI().create().buy(requestBody).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>?, response: Response<RegisterResponse>?) {
                progressDialog!!.dismiss()
                if (response!!.isSuccessful) {
                    setResult(Activity.RESULT_OK);
                    finish();
                }


            }

            override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {
                Log.d("ger", t.toString())
                progressDialog?.dismiss()
            }

        })

    }

}
