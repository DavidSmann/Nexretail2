package com.nexvis.nexretail.view.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.nexvis.nexretail.R
import com.nexvis.nexretail.custom.DrawerView
import com.nexvis.nexretail.custom.NexRetailAlertDialog
import com.nexvis.nexretail.interactor.HomeInteractor
import com.nexvis.nexretail.presenter.HomePresenter
import com.nexvis.nexretail.utils.Constant
import com.nexvis.nexretail.utils.NexRetailSharePreferences
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.NonCancellable.cancel

class HomeActivity :BaseActivity<HomeInteractor.View,HomeInteractor.Presenter>(),
        HomeInteractor.View, DrawerView.OnNavigationListener {

    var doubleBackToExitPressedOnce: Boolean = false

    override var mPresenter: HomeInteractor.Presenter = HomePresenter()

    override fun getLayout(): Int = R.layout.activity_home

    override fun initView() {
        setStatusBarColor(ActivityCompat.getColor(this, R.color.green))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        initEvent()
        btn_menu.setOnClickListener { openDrawer(layout_drawer) }
    }
    private fun initEvent() {
        drawer_view.setNavigationListener(this)
    }

    private fun logoutAction(){
        Log.d("Click","Click 222222===========")
        NexRetailAlertDialog.with(this,
                getString(R.string.logout_confirmation_message))
                .setPositiveButton(getString(R.string.logout), DialogInterface.OnClickListener { dialog, _ ->
                    LoginActivity.launchClearTask(this@HomeActivity)
                    NexRetailSharePreferences.getConstant(this@HomeActivity).clearAll()
                    dialog?.dismiss()
                }).setNegativeButton(getString(R.string.cancel), DialogInterface.OnClickListener { dialog, _ ->
                    dialog?.dismiss()
                }).show()
    }

    override fun activityEnterAnimation() {
        overridePendingTransition(R.anim.activity_slide_in_right, R.anim.activity_slide_out_left)
    }
    override fun onBackPressed() {

        //Checking for fragment count on backstack
        if (layout_drawer.isDrawerOpen(GravityCompat.START)) {
            layout_drawer.closeDrawer(GravityCompat.START)
        } else if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show()
            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        } else {
            super.onBackPressed()
            return
        }
    }



    companion object{
        fun launch(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }

        fun openDrawer(drawerLayout: DrawerLayout) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onItemClick(type: Constant.DataTypeMenu) {

        if (type == Constant.DataTypeMenu.BOOKNOW) doBookNow()
        if (type == Constant.DataTypeMenu.SERVENOW) doServeNow()
        if (type == Constant.DataTypeMenu.PROMOTION) doPromotion()
        if (type == Constant.DataTypeMenu.ORDERHISTORY) doOrderHistory()
        if (type == Constant.DataTypeMenu.SETTING) doSetting()
        if (type == Constant.DataTypeMenu.LOGOUT) logoutAction()
    }

    private fun doSetting() {
        TODO("Not yet implemented")
    }

    private fun doOrderHistory() {
        TODO("Not yet implemented")
    }

    private fun doPromotion() {
        TODO("Not yet implemented")
    }

    private fun doServeNow() {
        TODO("Not yet implemented")
    }

    private fun doBookNow() {
        TODO("Not yet implemented")
    }
}