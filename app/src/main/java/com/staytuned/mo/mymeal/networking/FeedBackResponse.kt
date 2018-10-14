package com.staytuned.mo.tngptutorial.networking

class FeedBackResponse(val feed_back : List<FeedBackResponse>) {
    class FeedBackResponse(
            val message: String,
            val created_date: String
    )
}