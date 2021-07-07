package com.nexvis.nexretail.interactor

import com.danny.coremodule.CorePresenter
import com.danny.coremodule.CoreView
import org.json.JSONObject

interface LoginInteractor {
    interface View : CoreView {
        fun onLoginResponse(data: JSONObject)
        fun onLoginFailed(message: String)
        fun onLogin()
        fun onCreateAcc()
    }

    interface Presenter : CorePresenter<View> {
        fun onLoginPost(usernameP: String, passwordP: String)
        fun updateLogin(body: JSONObject)
        fun deleteLogin(body: JSONObject)
    }

}