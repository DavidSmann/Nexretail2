package com.chheang.drequest;

import android.text.TextUtils;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class Utils {

    public static String getMethodName(int method) {
        if (method == Request.Method.GET)
            return "GET";
        if (method == Request.Method.POST)
            return "POST";
        if (method == Request.Method.PUT)
            return "PUT";
        if (method == Request.Method.DELETE)
            return "DELETE";
        if (method == Request.Method.HEAD)
            return "HEAD";
        if (method == Request.Method.OPTIONS)
            return "OPTIONS";
        if (method == Request.Method.TRACE)
            return "TRACE";
        if (method == Request.Method.PATCH)
            return "PATCH";
        return "UNKNOWN METHOD";
    }

    public static String getErrorMessageFrom(VolleyError volleyError, String defaultMsg) {
        if (volleyError instanceof NoConnectionError)
            return "There is a connection issue";
        else if (volleyError instanceof TimeoutError)
            return "There is a connection issue";
        try {
            return new JSONObject(new String(volleyError.networkResponse.data)).optString("message", defaultMsg);
        } catch (Exception e) {
            try {
                String msg = new String(volleyError.networkResponse.data);
                if (TextUtils.isEmpty(msg)) {
                    msg = defaultMsg;
                }
                return msg;
            } catch (Exception ee) {
                return defaultMsg;
            }
        }
    }

    public static String getErrorMessageFrom(VolleyError volleyError) {
        return getErrorMessageFrom(volleyError, "");
    }
}
