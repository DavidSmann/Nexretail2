package com.nexvis.sunfixretail.request

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.nexvis.nexretail.request.AppJSONObjectRequest
import com.nexvis.nexretail.BuildConfig
import org.json.JSONObject


class PostJSONObjectRequestLogin(
    context: Context?,
    private val functionName: String,
    private val body: JSONObject
) : AppJSONObjectRequest(context) {

    override fun onGetBodyContentType(): String {
        return "application/x-www-form-urlencoded"
    }

    override fun onGetBodyRequest(): String {
        Log.d("body","body==" + body.toString())
        return body.toString()
    }

    override fun getFunctionName(): String {
        return functionName
    }

    override fun getMethod(): Int {
        return Request.Method.POST
    }

    override fun getHeader(): MutableMap<String, String> {
        val headers: MutableMap<String, String> = HashMap()
        headers["Content-Type"] = "application/x-www-form-urlencoded"
        headers["Accept"] = "application/json"
        headers["__tenant"] = "Store"
        return headers
    }

//    override fun onGetParams(params: RequestParams?) {
//        val paramsNew = HashMap<String, String>()
//        paramsNew["grant_type"] = "password"
//        paramsNew["client_id"] = BuildConfig.CLIENT_ID
//        paramsNew["client_secret"] = BuildConfig.CLIENT_SECRET
//        paramsNew["username"] = "Admin-11"
//        paramsNew["password"] = "@Dmin123"
//    }

}