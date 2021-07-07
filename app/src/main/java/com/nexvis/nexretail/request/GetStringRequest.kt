package com.nexvis.sunfixretail.request

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.nexvis.nexretail.request.AppStringRequest
import java.util.HashMap

class GetStringRequest(
    context: Context?,
    private val functionName : String
): AppStringRequest(context) {
    override fun getFunctionName(): String {
        return functionName
    }


    override fun getMethod(): Int {
        return Request.Method.GET
    }

}