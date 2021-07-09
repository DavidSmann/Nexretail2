package com.nexvis.nexretail.view.activity.Room

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.nexvis.nexretail.R
import com.nexvis.nexretail.interactor.RoomInteractor
import com.nexvis.nexretail.presenter.RoomPresenter
import com.nexvis.nexretail.view.activity.BaseActivity
import com.nexvis.nexretail.view.activity.Book.BookingActivity
import com.nexvis.nexretail.view.adapter.BookingAdapter
import com.nexvis.nexretail.view.adapter.RoomAdapter
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.android.synthetic.main.custom_appbar.*
import org.json.JSONObject

class RoomActivity:BaseActivity<RoomInteractor.View,RoomInteractor.Presenter>(), RoomInteractor.View {

    override var mPresenter: RoomInteractor.Presenter = RoomPresenter()

    private lateinit var adapter :RoomAdapter

    private val gridLayoutManager = GridLayoutManager(this, 3)

    override fun getLayout(): Int = R.layout.activity_room

    override fun onRoomResponse(data: JSONObject) {
        Log.d(TAG, "onRoomResponse: "+ data)
    }

    override fun onRoomFailed(message: String) {
        Log.d(TAG, "onRoomFailed: "+ message)
    }

    override fun initView() {
        setStatusBarColor(ActivityCompat.getColor(this, R.color.green_appbar))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        setUpAppBar()
        setupRoomRcy()
    }

    private fun setUpAppBar(){
        btn_back.setOnClickListener { finish() }
        txt_appBar_title.text = getString(R.string.room)

        SV_search.isIconified = false
    }

    private fun setupRoomRcy(){
        rcy_room.layoutManager = gridLayoutManager
        adapter = RoomAdapter(this)
        rcy_room.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    companion object{
        fun launch(context: Context) {
            val intent = Intent(context, RoomActivity::class.java)
            context.startActivity(intent)
        }
    }

}