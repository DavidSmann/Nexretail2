package com.nexvis.nexretail.presenter

import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.*
import com.chheang.drequest.Utils
import com.danny.coremodule.CoreImpl
import com.nexvis.nexretail.BuildConfig
import com.nexvis.nexretail.interactor.LoginInteractor
import com.nexvis.nexretail.utils.NexRetailSharePreferences
import com.nexvis.sunfixretail.request.DeleteOrUpdateLogin
import com.nexvis.sunfixretail.request.EndPoint
import org.json.JSONObject


class LoginPresenter : CoreImpl<LoginInteractor.View>(),
    LoginInteractor.Presenter {

    override fun onLoginPost(usernameP: String, passwordP: String) {
        Log.d("params=======", "$usernameP , $passwordP")

        val queue = Volley.newRequestQueue(mContext)
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            "BuildConfig.BASE_URL" + EndPoint.CONNECT_TOKEN,
            Response.Listener { response ->
                Log.i("response:", response)
                val jsonObject = JSONObject(response)
                val accessToken: String = jsonObject.getString("access_token")
                NexRetailSharePreferences.getConstant(mContext).accessToken = accessToken
                mView?.onLoginResponse(jsonObject)
            },

            Response.ErrorListener { error ->
                mView?.onLoginFailed(Utils.getErrorMessageFrom(error as VolleyError))
                error.printStackTrace()
            }) {
            override fun getBodyContentType(): String {
                return "application/x-www-form-urlencoded"
            }

            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> =
                    HashMap()
                headers["Content-Type"] = "application/x-www-form-urlencoded"
                headers["Accept"] = "application/json"
                headers["__tenant"] = "Store"
                return headers
            }

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> =
                    HashMap()
//                params["client_id"] = BuildConfig.CLIENT_ID
//                params["client_secret"] = BuildConfig.CLIENT_SECRET
                params["grant_type"] = "password"
                params["username"] = usernameP
                params["password"] = passwordP
                return params
            }
        }
        stringRequest.retryPolicy = DefaultRetryPolicy(
            20 * 1000,
            2,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        queue.add(stringRequest)
    }

    override fun deleteLogin(body: JSONObject) {
        DeleteOrUpdateLogin(mContext!!, JSONObject())
            .setOnErrorListener {
                mView?.onLoginFailed(Utils.getErrorMessageFrom(it as VolleyError))
            }.execute {
                mView?.onLoginResponse(it as JSONObject)
            }

    }

    override fun updateLogin(body: JSONObject) {
        DeleteOrUpdateLogin(mContext!!, JSONObject())
            .setOnErrorListener {
                mView?.onLoginFailed(Utils.getErrorMessageFrom(it as VolleyError))
            }.execute {
                mView?.onLoginResponse(it as JSONObject)
            }
    }


}