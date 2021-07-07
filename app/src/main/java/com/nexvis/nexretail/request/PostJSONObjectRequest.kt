package com.nexvis.sunfixretail.request

import android.content.Context
import android.util.JsonToken
import android.util.Log
import com.android.volley.Request
import com.nexvis.nexretail.request.AppJSONObjectRequest
import org.json.JSONObject
import java.util.HashMap

class PostJSONObjectRequest(
        context: Context?,
        private val functionName: String,
        private val body: JSONObject
) : AppJSONObjectRequest(context) {

    override fun getFunctionName(): String {
        return functionName
    }

    override fun getMethod(): Int {
        return Request.Method.POST
    }

//    override fun appenHeader(): MutableMap<String, String> {
//        val header = hashMapOf<String, String>()
//        header["Content-Type"] = "application/x-www-form-urlencoded"
//        header["Accept"] = "application/json"
//        return header
//    }

    override fun onGetBodyContentType(): String {
        return "application/x-www-form-urlencoded"
    }

    override fun onGetBodyRequest(): String {
        return body.toString()
    }
}