package com.nexvis.nexretail.view.activity.Book

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.nexvis.nexretail.R
import com.nexvis.nexretail.interactor.BookNowInteractor
import com.nexvis.nexretail.presenter.BookNowPresenter
import com.nexvis.nexretail.view.activity.BaseActivity
import kotlinx.android.synthetic.main.custom_appbar.*
import org.json.JSONObject

class BookNowActivity : BaseActivity<BookNowInteractor.View,BookNowInteractor.Presenter>(), BookNowInteractor.View{
    override var mPresenter: BookNowInteractor.Presenter = BookNowPresenter()

    override fun getLayout(): Int = R.layout.activity_book_now

    override fun onBookNowResponse(data: JSONObject) {
        Log.d(TAG, "onBookNowResponse: "+data)
    }

    override fun onBookNowFailed(message: String) {
        Log.d(TAG, "onBookNowFailed: "+message)
    }

    override fun initView() {
        setStatusBarColor(ActivityCompat.getColor(this, R.color.green_appbar))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setUpAppBar()
    }

    private fun setUpAppBar(){
        btn_back.setOnClickListener { finish() }
        txt_appBar_title.text = getString(R.string.book_now)
    }

    companion object{

        fun launch(context: Context) {
            val intent = Intent(context, BookNowActivity::class.java)
            context.startActivity(intent)
        }
    }
}