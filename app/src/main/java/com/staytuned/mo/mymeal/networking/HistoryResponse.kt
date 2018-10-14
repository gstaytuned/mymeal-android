package com.staytuned.mo.tngptutorial.networking

class HistoryResponse(val history_list : List<HistoryListResponse>) {
    class HistoryListResponse(
            val name: String,
            val price: String,
            val cust_name: String,
            val created_date: String,
            val txn_quantity: String,
            val cust_selling_id: String
    )
}