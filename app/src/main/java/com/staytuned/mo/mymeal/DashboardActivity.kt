package com.staytuned.mo.mymeal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dashboard.*


class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, OrderFragment.newInstance(intent.getStringExtra("cust_name"))
                    )
                    .commit()
        }

        var fragment: Fragment? = null
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_order -> {
                    fragment = OrderFragment.newInstance(intent.getStringExtra("cust_name"))
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit()
                    true

                }
                R.id.action_sale -> {
                    fragment = SaleFragment.newInstance(intent.getStringExtra("cust_name"))
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit()
                    true
                }
                R.id.action_history -> {
                    fragment = HistoryFragment.newInstance(intent.getStringExtra("cust_name"))
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit()
                    true
                }
                else -> {
                    true
                }

            }
        }
    }
}
