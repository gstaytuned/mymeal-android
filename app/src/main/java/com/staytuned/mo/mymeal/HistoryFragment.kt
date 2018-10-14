package com.staytuned.mo.mymeal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.staytuned.mo.mymeal.adapter.HistoryAdapter
import com.staytuned.mo.tngptutorial.networking.HistoryResponse
import com.staytuned.mo.tngptutorial.networking.RestAPI
import kotlinx.android.synthetic.main.fragment_order.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryFragment : Fragment() {
    val list: ArrayList<HistoryResponse.HistoryListResponse> = ArrayList()

    companion object {

        fun newInstance(custName: String): HistoryFragment {
            val fragment = HistoryFragment()
            val args = Bundle()
            args.putString("cust_name", custName)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_history,
                container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv.adapter = HistoryAdapter(list, activity)
        rv.layoutManager = LinearLayoutManager(activity)
        loadFoodList()
        tv_cust_name.text = arguments!!.getString("cust_name").toString()
    }

    private fun loadFoodList() {
        var db = TinyDB(activity!!)
        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody.put("cust_id", db.getString("cust_id"))
        RestAPI().create().loadHistoryBuy(requestBody).enqueue(object : Callback<HistoryResponse> {
            override fun onResponse(call: Call<HistoryResponse>?, response: Response<HistoryResponse>?) {
                if (response!!.isSuccessful) {
                    list.clear()
                    list.addAll(response.body()!!.history_list)
                    rv.adapter.notifyDataSetChanged()
                }
                RestAPI().create().loadHistorySell(requestBody).enqueue(object : Callback<HistoryResponse> {
                    override fun onResponse(call: Call<HistoryResponse>?, response: Response<HistoryResponse>?) {
                        if (response!!.isSuccessful) {
                            list.addAll(response.body()!!.history_list)
                            rv.adapter.notifyDataSetChanged()
                        }

                    }

                    override fun onFailure(call: Call<HistoryResponse>?, t: Throwable?) {
                        Log.d("ger", t.toString())
                    }

                })

            }

            override fun onFailure(call: Call<HistoryResponse>?, t: Throwable?) {
                Log.d("ger", t.toString())
            }

        })

    }

}