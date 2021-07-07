package com.nexvis.sunfixretail.request

import android.content.Context
import android.util.Log
import com.nexvis.nexretail.request.AppStringRequest
import org.json.JSONObject
import java.util.HashMap

class PostStringRequest(
    context: Context?,
    private val functionName: String,
    private val body: JSONObject,
    private val token: String
): AppStringRequest(context) {
    override fun getFunctionName(): String {
        return functionName
    }

    override fun getContentType(): String {
        return "application/json"
    }

    override fun onGetBody(): ByteArray {
        return body.toString().toByteArray()
    }

    override fun getHeader(): MutableMap<String, String> {
        val params: MutableMap<String, String> = HashMap()
        params["Content-Type"] = "application/json"
        params["Authorization"] = "Bearer "+ token
        Log.d("Authorization=======", params.toString())
        return params
    }

}