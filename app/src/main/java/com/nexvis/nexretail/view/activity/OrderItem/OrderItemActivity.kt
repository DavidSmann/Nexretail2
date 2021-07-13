package com.nexvis.nexretail.view.activity.OrderItem

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.nexvis.nexretail.R
import com.nexvis.nexretail.interactor.OrderItemInteractor
import com.nexvis.nexretail.presenter.OrderItemPresenter
import com.nexvis.nexretail.view.activity.BaseActivity
import com.nexvis.nexretail.view.activity.Book.BookNowActivity
import com.nexvis.nexretail.view.adapter.OrderItemTabAdapter
import kotlinx.android.synthetic.main.activity_book_now.*
import kotlinx.android.synthetic.main.activity_book_now.vp_book_now
import kotlinx.android.synthetic.main.activity_order_item.*
import kotlinx.android.synthetic.main.custom_appbar.*
import org.json.JSONObject

class OrderItemActivity : BaseActivity<OrderItemInteractor.View, OrderItemInteractor.Presenter>(),
        OrderItemInteractor.View {
    override var mPresenter: OrderItemInteractor.Presenter = OrderItemPresenter()

    override fun getLayout(): Int  = R.layout.activity_order_item

    override fun onOrderItemResponse(data: JSONObject) {
        Log.d(TAG, "onOrderItemResponse: "+data)
    }

    override fun onOrderItemFailed(message: String) {
        Log.d(TAG, "onOrderItemFailed: "+message)
    }

    override fun initView() {

        setStatusBarColor(ActivityCompat.getColor(this, R.color.green_appbar))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        setUpOrderItemTab(OrderItemTabAdapter(this))
        setUpAppBar()
    }

    private fun setUpAppBar() {
        btn_back.setOnClickListener { finish() }
        txt_appBar_title.text = getString(R.string.order_item)
    }

    private fun setUpOrderItemTab(orderItemTabAdapter: OrderItemTabAdapter){
        vp_order_item.adapter = orderItemTabAdapter
        val tabLayoutMediator = TabLayoutMediator(
                tabLayout_order_item, vp_order_item,
                TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                    when (position) {
                        0->  tab.text = "All"
                        1->  tab.text = "Starter"
                        2->  tab.text = "Desert"
                        3->  tab.text = "Beer"
                    }
                }
        )
        tabLayoutMediator.attach()
    }

    companion object{

        fun launch(context: Context) {
            val intent = Intent(context, OrderItemActivity::class.java)
            context.startActivity(intent)
        }
    }
}