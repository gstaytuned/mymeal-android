package com.staytuned.mo.mymeal

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.staytuned.mo.tngptutorial.networking.RegisterResponse
import com.staytuned.mo.tngptutorial.networking.RestAPI
import kotlinx.android.synthetic.main.activity_register_form.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFormActivity : AppCompatActivity() {
    var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form)

        sign_in_button.setOnClickListener {
            validateData();
        }


    }

    private fun validateData() {

        if (text_input_username.text.isNotEmpty() && text_input_password.text.isNotEmpty()
                && text_input_cusname.text.isNotEmpty() && text_input_address.text.isNotEmpty()
                && text_input_address.text.isNotEmpty()
                && text_input_city.text.isNotEmpty() && text_input_tel.text.isNotEmpty()) {

            var requestBody: MutableMap<String, String> = mutableMapOf()
            requestBody.put("username", text_input_username.text.toString())
            requestBody.put("password", text_input_password.text.toString())
            requestBody.put("name", text_input_cusname.text.toString())
            requestBody.put("address", text_input_address.text.toString())
            requestBody.put("city", text_input_city.text.toString())
            requestBody.put("tel_no", text_input_tel.text.toString())
            register(requestBody)
        } else {
            Toast.makeText(this, "Please fill all form", Toast.LENGTH_LONG).show();
        }

    }

    private fun register(requestBody: MutableMap<String, String>) {
        progressDialog = ProgressDialog.show(this, "Register", "Loading...", true, false);
        RestAPI().create().register(requestBody).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>?, response: Response<RegisterResponse>?) {
                progressDialog?.dismiss()
                if (response!!.isSuccessful) {
                    val builder = AlertDialog.Builder(this@RegisterFormActivity)

                    // Set the alert dialog title
                    builder.setTitle("Info")

                    // Display a message on alert dialog
                    builder.setMessage("Registration successful")

                    // Set a positive button and its click listener on alert dialog
                    builder.setPositiveButton("Ok") { dialog, which ->
                        dialog.dismiss()
                        finish()
                    }
                    builder.show()

                }

            }

            override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {
            }

        })
    }
}
