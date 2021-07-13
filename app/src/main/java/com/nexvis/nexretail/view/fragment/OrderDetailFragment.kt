package com.nexvis.nexretail.view.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.nexvis.nexretail.R
import com.nexvis.nexretail.view.activity.OrderItem.OrderItemActivity
import com.nexvis.nexretail.view.adapter.OrderDetailAdapter
import kotlinx.android.synthetic.main.fragmen_order_detail.*

class OrderDetailFragment : BaseFragment(){
    private var linearLayoutManager = LinearLayoutManager(context)
    private lateinit var adapter : OrderDetailAdapter

    override fun onViewCreated() {
        setUpRcy()
        btn_add.setOnClickListener { OrderItemActivity.Companion.launch(context!!) }
    }

    override fun getFragmentLayoutId(): Int {
        return R.layout.fragmen_order_detail
    }

    private fun setUpRcy(){
        rcy_foods_order.layoutManager = linearLayoutManager
        adapter = OrderDetailAdapter(context!!)
        rcy_foods_order.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}