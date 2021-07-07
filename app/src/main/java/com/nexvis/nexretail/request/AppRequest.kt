package com.nexvis.nexretail.request

import android.content.Context
import com.android.volley.VolleyError
import com.chheang.drequest.DRequest
import com.danny.coremodule.Devices
import com.nexvis.nexretail.utils.NexRetailSharePreferences

abstract class AppRequest<T>(context: Context?) : DRequest<T>(context) {
    override fun getBaseUrl(): String {
//        return BuildConfig.BASE_URL + getFunctionName()
        return  getFunctionName()
    }

    open fun isErrorShouldBack(): Boolean {
        return false
    }

    override fun onCreateHeader(header: MutableMap<String, String>): MutableMap<String, String> {
        val mHeader = getHeader()
        mHeader.putAll(appendHeader())
        return mHeader
    }

    open fun appendHeader(): MutableMap<String, String> {
        return hashMapOf()
    }

    open fun getHeader(): MutableMap<String, String> {
        val header = HashMap<String, String>()
        val accessToken = NexRetailSharePreferences.getConstant(context).accessToken
//        val token = SunFixSharePreferences.getConstant(context).token

        header["__tenant"] = "Store"
//        header["token"] = "Bearer $token"
        header["Authorization"] = "Bearer $accessToken"

        header["X-App-Version"] = Devices.getAppVersionName(context)
        header["X-Platform"] = "android"
        header["X-Model"] = Devices.getDeviceModel()
        header["X-Device-Name"] = Devices.getDeviceName()
        header["X-User-Agent"] = System.getProperty("http.agent").toString()
        header["X-OS-Version"] = Devices.getDeviceOSVersion()
        header["X-UDID"] = Devices.getDeviceID(context)
        return header
    }

    abstract fun getFunctionName(): String


    override fun onError(error: VolleyError) {
        if (context != null && error.networkResponse != null && (error.networkResponse.statusCode == 401
                        || error.networkResponse.statusCode == 403)) run {
            if (error.networkResponse.statusCode == 401) {

            } else super.onError(error)
        } else if (context != null && error.networkResponse != null && error.networkResponse.statusCode == 502) {
            super.onError(error)
        } else {
            super.onError(error)
        }
    }
}