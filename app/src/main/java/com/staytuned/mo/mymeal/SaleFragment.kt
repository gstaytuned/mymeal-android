package com.staytuned.mo.mymeal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.staytuned.mo.mymeal.adapter.SaleFoodAdapter
import com.staytuned.mo.tngptutorial.networking.RegisterResponse
import com.staytuned.mo.tngptutorial.networking.RestAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.staytuned.mo.mymeal.adapter.RecyclerItemClickListener
import kotlinx.android.synthetic.main.fragment_sale.*
import org.parceler.Parcels


class SaleFragment : Fragment() {
    val list: ArrayList<RegisterResponse.foodListResponse> = ArrayList()

    companion object {

        fun newInstance(custName: String): SaleFragment {
            val fragment = SaleFragment()
            val args = Bundle()
            args.putString("cust_name", custName)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_sale,
                container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv.adapter = SaleFoodAdapter(list, activity)
        rv.layoutManager = LinearLayoutManager(activity!!)
        loadFoodList()
        tv_cust_name.text = arguments!!.getString("cust_name").toString()
        fab_sell.setOnClickListener {
            val intent = Intent(activity, SellingFoodFormActivity::class.java)
            startActivityForResult(intent, 999)
        }
    }

    private fun loadFoodList() {
        var db = TinyDB(activity!!)
        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody.put("cust_id", db.getString("cust_id"))
        RestAPI().create().loadMySaleList(requestBody).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>?, response: Response<RegisterResponse>?) {
                if (response!!.isSuccessful) {
                    list.clear()
                    list.addAll(response.body()!!.food_list)
                    rv.adapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {
                Log.d("ger", t.toString())
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 999) {
            loadFoodList();
            Snackbar.make(layout, "Yay! It's finished!", Snackbar.LENGTH_SHORT).show()
        }
    }

}