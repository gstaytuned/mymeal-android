package com.staytuned.mo.mymeal

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.staytuned.mo.mymeal.adapter.FoodAdapter
import com.staytuned.mo.mymeal.adapter.RecyclerItemClickListener
import com.staytuned.mo.tngptutorial.networking.RegisterResponse
import com.staytuned.mo.tngptutorial.networking.RestAPI
import kotlinx.android.synthetic.main.fragment_order.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.design.widget.Snackbar


class OrderFragment : Fragment() {
    var progressDialog: ProgressDialog? = null
    val list: ArrayList<RegisterResponse.foodListResponse> = ArrayList()

    companion object {

        fun newInstance(custName: String): OrderFragment {
            val fragment = OrderFragment()
            val args = Bundle()
            args.putString("cust_name", custName)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_order,
                container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv.adapter = FoodAdapter(list, activity)
        rv.layoutManager = LinearLayoutManager(activity)
        loadFoodList()
        tv_cust_name.text = arguments!!.getString("cust_name").toString()


        rv.addOnItemTouchListener(RecyclerItemClickListener(activity!!, rv, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onLongItemClick(view: View?, position: Int) {

            }

            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(activity, FoodDetailActivity::class.java)
                intent.putExtra("name",list.get(position).cust_name)
                intent.putExtra("image",list.get(position).food_img)
                intent.putExtra("detail",list.get(position).detail)
                intent.putExtra("price",list.get(position).price)
                intent.putExtra("quantity",list.get(position).quantity)
                intent.putExtra("food_id",list.get(position).food_id)
                intent.putExtra("food_name",list.get(position).name)
                intent.putExtra("selling_id",list.get(position).selling_id)
                startActivityForResult(intent,777)
            }
        }))
    }

    private fun loadFoodList() {
        var db = TinyDB(activity!!)
        var requestBody: MutableMap<String, String> = mutableMapOf()
        requestBody.put("cust_id", db.getString("cust_id"))
        progressDialog = ProgressDialog.show(activity!!, "Login", "Loading...", true, false);
        RestAPI().create().loadFoodList(requestBody).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>?, response: Response<RegisterResponse>?) {
                progressDialog?.dismiss()
                if (response!!.isSuccessful) {
                    list.clear()
                    list.addAll(response.body()!!.food_list)
                    rv.adapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {
                progressDialog?.dismiss()
                Log.d("ger", t.toString())
            }

        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 777){
            loadFoodList()
            Snackbar.make(layout, "Yay! It's finished!", Snackbar.LENGTH_SHORT).show()
        }
    }

}