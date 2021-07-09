package com.nexvis.nexretail.view.activity.Kitchen

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexvis.nexretail.R
import com.nexvis.nexretail.interactor.KitchenInteractor
import com.nexvis.nexretail.presenter.KitchenPresenter
import com.nexvis.nexretail.view.activity.BaseActivity
import com.nexvis.nexretail.view.activity.Home.HomeActivity
import com.nexvis.nexretail.view.adapter.BookingAdapter
import com.nexvis.nexretail.view.adapter.KitchenAdapter
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_kitchen.*
import kotlinx.android.synthetic.main.custom_appbar.*
import org.json.JSONObject

class KitchenActivity : BaseActivity<KitchenInteractor.View,KitchenInteractor.Presenter>(), KitchenInteractor.View{

    private var linearLayoutManager = LinearLayoutManager(this)

    private lateinit var adapter : KitchenAdapter

    override var mPresenter: KitchenInteractor.Presenter = KitchenPresenter()

    override fun getLayout(): Int = R.layout.activity_kitchen

    override fun onKitchenResponse(data: JSONObject) {
        Log.d(TAG, "onKitchenResponse: "+ data)
    }

    override fun onKitchenFailed(message: String) {
        Log.d(TAG, "onKitchenFailed: "+ message)
    }

    override fun initView() {
        setStatusBarColor(ActivityCompat.getColor(this, R.color.green_appbar))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setUpAppBar()
        setupKitchenRcy()

    }

    private fun setUpAppBar(){
        btn_back.setOnClickListener { finish() }
        txt_appBar_title.text = getString(R.string.kitchen)
    }

    private fun setupKitchenRcy(){
        rcy_kitchen.layoutManager = linearLayoutManager
        adapter = KitchenAdapter(this)
        rcy_kitchen.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    companion object{
        fun launch(context: Context) {
            val intent = Intent(context, KitchenActivity::class.java)
            context.startActivity(intent)
        }


    }
}