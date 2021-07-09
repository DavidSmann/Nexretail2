package com.nexvis.nexretail.view.activity.OpenBill

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexvis.nexretail.R
import com.nexvis.nexretail.interactor.OpenBillInteractor
import com.nexvis.nexretail.presenter.OpenBillPresenter
import com.nexvis.nexretail.view.activity.BaseActivity
import com.nexvis.nexretail.view.adapter.OpenBillAdapter
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_openbill.*
import kotlinx.android.synthetic.main.custom_appbar.*
import org.json.JSONObject

class OpenBillActivity : BaseActivity<OpenBillInteractor.View,OpenBillInteractor.Presenter>(),
        OpenBillInteractor.View {

    private lateinit var adapter : OpenBillAdapter

    private val linearLayoutManager = LinearLayoutManager(this)
    override var mPresenter: OpenBillInteractor.Presenter = OpenBillPresenter()

    override fun getLayout(): Int = R.layout.activity_openbill

    override fun onOpenBillResponse(data: JSONObject) {
        Log.d(TAG, "onOpenBillResponse: "+data)
    }

    override fun onOpenBillFailed(message: String) {
        Log.d(TAG, "onOpenBillFailed: "+message)
    }

    override fun initView() {
        setStatusBarColor(ActivityCompat.getColor(this, R.color.green_appbar))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setUpAppBar()
        setupOpenBillRcy()
    }

    private fun setupOpenBillRcy() {
        rcy_open_bill.layoutManager = linearLayoutManager
        adapter = OpenBillAdapter(this)
        rcy_open_bill.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun setUpAppBar() {
        btn_back.setOnClickListener { finish() }
        txt_appBar_title.text = getString(R.string.open_bill)
    }

    companion object{

        fun launch(context: Context) {
            val intent = Intent(context, OpenBillActivity::class.java)
            context.startActivity(intent)
        }
    }
}