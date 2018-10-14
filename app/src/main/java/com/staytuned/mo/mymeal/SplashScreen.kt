package com.staytuned.mo.mymeal

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.staytuned.mo.tngptutorial.networking.RegisterResponse
import com.staytuned.mo.tngptutorial.networking.RestAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashScreenActivity : AppCompatActivity() {
    private var handler: Handler? = null
    private var runnable: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen)

        handler = Handler()

        runnable = Runnable {
            var db = TinyDB(applicationContext)
            Log.d("TOKEN", db.getString("token"))
            if (!"".equals(db.getString("token")) &&!"null".equals(db.getString("token")) ) {
                var requestBody: MutableMap<String, String> = mutableMapOf()
                requestBody.put("token", db.getString("token"));
                token(requestBody)
            } else {
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun token(requestBody: MutableMap<String, String>) {
        RestAPI().create().token(requestBody).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>?, response: Response<RegisterResponse>?) {
                if (response!!.isSuccessful) {
                    val intent = Intent(this@SplashScreenActivity, DashboardActivity::class.java)
                    intent.putExtra("cust_name", response.body()?.cust_name)
                    intent.putExtra("cust_id", response.body()?.cust_id)
                    startActivity(intent)
                    finish()
                }

            }

            override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {
            }

        })
    }

    public override fun onResume() {
        super.onResume()
        handler?.postDelayed(runnable, 6000)
    }

    public override fun onStop() {
        super.onStop()
        handler?.removeCallbacks(runnable)
    }
}