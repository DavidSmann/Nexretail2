package com.nexvis.nexretail.view.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.nexvis.nexretail.R
import com.nexvis.nexretail.custom.NexRetailAlertDialog
import com.nexvis.nexretail.interactor.LoginInteractor
import com.nexvis.nexretail.presenter.LoginPresenter
import com.nexvis.nexretail.custom.ErrorHandleView
import kotlinx.android.synthetic.main.activity_login_screen.*

import org.json.JSONObject


class LoginActivity : BaseActivity<LoginInteractor.View, LoginInteractor.Presenter>(),
    LoginInteractor.View {

    override var mPresenter: LoginInteractor.Presenter = LoginPresenter()

    override fun getLayout(): Int = R.layout.activity_login_screen

    override fun getToolbar(): Toolbar? = null

    override fun onLoginResponse(data: JSONObject) {
       // HomeActivity.launch(this)
        error_handle_view.setViewMode(ErrorHandleView.Mode.HIDE)
        Handler().postDelayed({
            finish()
        }, 1000)
    }

    override fun onLoginFailed(message: String) {
        NexRetailAlertDialog.with(this,message).show()
        error_handle_view.setViewMode(ErrorHandleView.Mode.HIDE)
    }

    override fun onLogin() {
        ed_username.setText("Admin-11")
        ed_password.setText("@Dmin123")
        btn_login.setOnClickListener{
           // error_handle_view.setViewMode(ErrorHandleView.Mode.LOADING)
            //mPresenter.onLoginPost(ed_username.text.toString(),ed_password.text.toString())
            HomeActivity.Companion.launch(this)
        }
    }

    override fun onCreateAcc() {
//        btn_createAcc.setOnClickListener{
//            redirectActivity(this, RegisterAccountActivity::class.java)}

    }

    //No Extend from BaseActivity

    override fun initView() {
//        setSupportActionBar(toolbar_login)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
 //       supportActionBar!!.setDisplayShowTitleEnabled(false)
        setStatusBarColor(ActivityCompat.getColor(this, R.color.green))
//        toolbar_login.setNavigationOnClickListener { onBackPressed() }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        onLogin()
        onCreateAcc()

    }

    override fun activityEnterAnimation() {
        overridePendingTransition(R.anim.activity_slide_up, R.anim.activity_slide_down)
    }

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
        fun launchClearTask(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("RESTART", true)
            context.startActivity(intent)
        }
    }

}
