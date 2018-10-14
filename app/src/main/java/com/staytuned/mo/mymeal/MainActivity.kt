package com.staytuned.mo.mymeal

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.staytuned.mo.tngptutorial.networking.RegisterResponse
import com.staytuned.mo.tngptutorial.networking.RestAPI
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_no_account.setOnClickListener() {
            val intent = Intent(this@MainActivity, RegisterFormActivity::class.java)
            startActivity(intent)
        }

        sign_in_button.setOnClickListener {
            if (text_input_username.text.isNotEmpty() && text_input_password.text.isNotEmpty()) {
                var requestBody: MutableMap<String, String> = mutableMapOf()
                requestBody.put("username", text_input_username.text.toString());
                requestBody.put("password", text_input_password.text.toString());
                login(requestBody)
            }
        }
    }

    private fun login(requestBody: MutableMap<String, String>) {

        progressDialog = ProgressDialog.show(this, "Login", "Loading...", true, false);
        RestAPI().create().login(requestBody).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>?, response: Response<RegisterResponse>?) {
                progressDialog?.dismiss()
                if (response!!.isSuccessful) {
                    var db = TinyDB(applicationContext)
                    db.putString("token", response.body()?.token.toString())
                    db.putString("cust_id", response.body()?.cust_id.toString())
                    val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                    intent.putExtra("cust_name",response.body()?.cust_name)
                    intent.putExtra("cust_id",response.body()?.cust_id)
                    startActivity(intent)
                    finish()
                } else {
                    showDialog()

                }

            }

            override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {

            }

        })

    }

    fun showDialog() {
        val builder = AlertDialog.Builder(this@MainActivity)

        // Set the alert dialog title
        builder.setTitle("Info")

        // Display a message on alert dialog
        builder.setMessage("Username or password incorrect please try again..")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("Ok") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }
}
