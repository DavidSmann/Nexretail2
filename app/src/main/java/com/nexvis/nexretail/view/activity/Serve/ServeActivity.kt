package com.nexvis.nexretail.view.activity.Serve

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexvis.nexretail.R
import com.nexvis.nexretail.interactor.ServeInteractor
import com.nexvis.nexretail.presenter.ServePresenter
import com.nexvis.nexretail.view.activity.BaseActivity
import com.nexvis.nexretail.view.adapter.ServeAdapter
import kotlinx.android.synthetic.main.activity_serve.*
import kotlinx.android.synthetic.main.custom_appbar.*
import org.json.JSONObject

class ServeActivity : BaseActivity<ServeInteractor.View, ServeInteractor.Presenter>(),
    ServeInteractor.View {

    private val linearLayoutManager = LinearLayoutManager(this)

    private lateinit var adapter : ServeAdapter

    override var mPresenter: ServeInteractor.Presenter = ServePresenter()

    override fun getLayout(): Int = R.layout.activity_serve

    override fun onServeResponse(data: JSONObject) {
        Log.d(TAG, "onServeResponse: "+data)
    }

    override fun onServeFailed(message: String) {
        Log.d(TAG, "onServeFailed: "+ message)
    }

    override fun initView() {
        setStatusBarColor(ActivityCompat.getColor(this, R.color.green_appbar))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setUpAppBar()
        setupBookingRcy()
    }
    private fun setUpAppBar(){
        btn_back.setOnClickListener { finish() }
        txt_appBar_title.text = getString(R.string.serve)
    }

    private fun setupBookingRcy(){
        rcy_serve.layoutManager = linearLayoutManager
        adapter = ServeAdapter(this)
        rcy_serve.adapter = adapter
        adapter.notifyDataSetChanged()

    }
    companion object{

        fun launch(context: Context) {
            val intent = Intent(context, ServeActivity::class.java)
            context.startActivity(intent)
        }
    }
}