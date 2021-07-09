package com.nexvis.nexretail.view.activity.Book

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexvis.nexretail.R
import com.nexvis.nexretail.interactor.BookingInteractor
import com.nexvis.nexretail.presenter.BookingPresenter
import com.nexvis.nexretail.view.activity.BaseActivity
import com.nexvis.nexretail.view.adapter.BookingAdapter
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.custom_appbar.*
import org.json.JSONObject

class BookingActivity : BaseActivity<BookingInteractor.View, BookingInteractor.Presenter>(), BookingInteractor.View {

    override var mPresenter: BookingInteractor.Presenter = BookingPresenter()

    private val linearLayoutManager = LinearLayoutManager(this)

    private lateinit var adapter: BookingAdapter

    override fun getLayout(): Int = R.layout.activity_booking

    override fun onBookingResponse(data: JSONObject) {
        Log.d(TAG, "oonBookingResponse: "+data)
    }

    override fun onBookingFailed(message: String) {
        Log.d(TAG, "oonBookingResponse: "+message)
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
        txt_appBar_title.text = getString(R.string.booking)
    }

    private fun setupBookingRcy(){
        rcy_booking.layoutManager = linearLayoutManager
        adapter = BookingAdapter(this)
        rcy_booking.adapter = adapter
        adapter.notifyDataSetChanged()

    }


    companion object{

        fun launch(context: Context) {
            val intent = Intent(context, BookingActivity::class.java)
            context.startActivity(intent)
        }
    }
}