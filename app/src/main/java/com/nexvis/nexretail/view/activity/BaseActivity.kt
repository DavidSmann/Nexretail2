package com.nexvis.nexretail.view.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.danny.coremodule.CoreActivity
import com.danny.coremodule.CorePresenter
import com.danny.coremodule.CoreView
import com.google.android.material.snackbar.Snackbar
import com.nexvis.nexretail.R
import com.nexvis.nexretail.application.NexRetailApplication

abstract class BaseActivity<in V : CoreView, T : CorePresenter<V>> : CoreActivity(), CoreView {

    protected abstract var mPresenter: T
    protected var TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityEnterAnimation()
        setStatusBarColor(Color.TRANSPARENT)
        setContentView(getLayout())
        mPresenter.attachView(this, this as V)

    }

    abstract fun getLayout(): Int

    open fun getToolbar(): Toolbar? = null

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
        Log.i("Activity Lifecycle", javaClass.simpleName + "=======> onBackPressed")
        activityExitAnimation()
    }

    override fun onInternetDisconnect() {
        NexRetailApplication.isInternetConnected = false
        val snackBar = Snackbar.make(
                window.decorView.findViewById(android.R.id.content),
                getString(R.string.message_no_internet),
                Snackbar.LENGTH_INDEFINITE   // Offline issue
        )
        snackBar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.internet_disconnect))
        snackBar.show()
    }

    override fun onInternetConnect() {
        if (!NexRetailApplication.isInternetConnected) {
            NexRetailApplication.isInternetConnected = true
            val snackBar = Snackbar.make(
                    window.decorView.findViewById(android.R.id.content),
                    "Internet Connected.",
                    Snackbar.LENGTH_SHORT
            )
            snackBar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.internet_connect))
            snackBar.show()
        }
    }

    open fun isStatusBarDark(): Boolean {
        return false
    }

    open fun setStatusBarColor(color: Int) {
        if (isStatusBarDark() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        window.statusBarColor = color
    }

    open fun activityEnterAnimation() {
        overridePendingTransition(R.anim.activity_slide_in_right, R.anim.activity_slide_out_left)
    }

    open fun activityExitAnimation() {
        overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_right)
    }
}