package com.chheang.drequest;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public abstract class DRequest<T> {
    protected static final String PROTOCOL_CHARSET = "utf-8";
    private static RequestQueue requestQueue;

    private Context context;

    private int mTimeOut = 30000;

    private Response.ErrorListener mErrorListener;

    private Response.Listener<T> onResponseListener;
    private Request request;

    private NetworkResponse networkResponse;
    private VolleyError volleyError;

    public DRequest(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getContentType() {
        //return "application/json; charset=utf-8";
        return "application/x-www-form-urlencoded";
    }

    public NetworkResponse getNetworkResponse() {
        return networkResponse;
    }

    public void setNetworkResponse(NetworkResponse networkResponse) {
        this.networkResponse = networkResponse;
    }

    public VolleyError getVolleyError() {
        return volleyError;
    }

    public void setVolleyError(VolleyError volleyError) {
        this.volleyError = volleyError;
    }

    public Response.Listener getOnResponseListener() {
        return onResponseListener;
    }

    public DRequest<T> setOnResponseListener(Response.Listener onResponseListener) {
        this.onResponseListener = onResponseListener;
        return this;
    }

    public Response.ErrorListener getOnErrorListener() {
        return mErrorListener;
    }

    public DRequest<T> setOnErrorListener(Response.ErrorListener onErrorListener) {
        this.mErrorListener = onErrorListener;
        return this;
    }

    public static RequestQueue getRequestQueue(Context context) {
        if (requestQueue == null) {
            Cache cache = new DiskBasedCache(context.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            // Don't forget to start the volley request queue
            requestQueue.start();
        }
        return requestQueue;
    }

    public void execute(Response.Listener onResponseListener) {
        setOnResponseListener(onResponseListener);
        execute();
    }

    public void execute() {
        if (getContext() != null) {
            request = onMakeRequest();
            if (request != null) {
                getRequestQueue(getContext()).add(request);
                if (isCache()) {
                    Cache.Entry data = getRequestQueue(getContext()).getCache().get(getCacheKey());
                    if (data != null)
                        onResponse(onParseNetworkResponse(new NetworkResponse(200, data.data, data.responseHeaders, true)).result);
                }
            }
            try {
                Log.d("EXECUTE:>>>", this.getClass().getSimpleName() + "(" + getContext().getClass().getSimpleName() + ") : "
                        + Utils.getMethodName(getMethod()) + " " + getRequestUrl());
                if (!TextUtils.isEmpty(onGetBodyRequest()))
                    Log.d("EXECUTE:>>>", this.getClass().getSimpleName() + " JSON data " + new JSONObject(onGetBodyRequest()).toString(1));
                else
                    Log.d("EXECUTE:>>>", this.getClass().getSimpleName() + " JSON data null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e(this.getClass().getSimpleName(), "Error with context = null!");
        }
    }

    public void getCache() {
        request = onMakeRequest();
        Cache.Entry data = getRequestQueue(getContext()).getCache().get(getCacheKey());
        if (data != null)
            onResponse(onParseNetworkResponse(new NetworkResponse(200, data.data, data.responseHeaders, true)).result);
    }

    public String getCacheKey() {
        if (request != null)
            return getRequest().getCacheKey();
        return "";
    }

    public Request getRequest() {
        return request;
    }

    public int getMethod() {
        return Request.Method.GET;
    }

    public String getRequestUrl() {
        String url = getBaseUrl();
        RequestParams urlParams = new RequestParams();
        onGetUrlParams(urlParams);
        url += urlParams.getParamsString();
        return url;
    }

    public void onGetUrlParams(RequestParams requestParams) {
    }


    public Request onMakeRequest() {
        Request<T> request = new Request<T>(getMethod(), getRequestUrl(), null) {
            @Override
            protected Response<T> parseNetworkResponse(NetworkResponse response) {
                setNetworkResponse(response);
                return onParseNetworkResponse(response);
            }

            public void deliverError(VolleyError error) {
                setVolleyError(error);
                onError(error);
            }

            @Override
            public String getBodyContentType() {
                try {
                    return onGetBodyContentType();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void deliverResponse(T response) {
                onResponse(response);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = onCreateHeader(super.getHeaders());
 //               Map<String, String> header = new HashMap<String, String>();
//                Log.d("HEAD 1 ===:>>>", header.toString());
                header.put("Content-Type", getContentType());

//                Log.d("HEADER:>>>", context.getClass().getSimpleName() + " : " + header.toString());
                return header;
            }

            @Override
            public byte[] getBody() {
                return onGetBody();
            }

            @Override
            protected Map<String, String> getParams() {
                RequestParams params = new RequestParams();
                onGetParams(params);
                return params.getParams();
            }
        };
        request.setRetryPolicy(getRetryPolicy());
        request.setShouldCache(isCache());
        request.setTag(getRequestTag());
        return request;
    }

    public byte[] onGetBody() {
        try {
            return onGetBodyRequest().getBytes(PROTOCOL_CHARSET);
        } catch (Exception e) {
            return null;
        }
    }

    public String onGetBodyContentType() {
        return null;
    }

    public void onGetParams(RequestParams params) {
    }

    public void onError(VolleyError error) {
        try {
            if (error != null)
                error.printStackTrace();
            Log.e("REQUEST_ERROR:>>>", this.getClass().getSimpleName() + " (" + getContext().getClass().getSimpleName() + ") : " + getRequestUrl());
            if (error != null) {
                Log.e("REQUEST_ERROR:>>>", "Cause by : " + new String(error.networkResponse.data));
                Log.e("REQUEST_ERROR:>>>", "Status Code : " + error.networkResponse.statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Response.ErrorListener mErrorListener = getOnErrorListener();
        if (mErrorListener != null) {
            mErrorListener.onErrorResponse(error);
        }
    }

    public String onGetBodyRequest() {
        return null;
    }

    public Map<String, String> onCreateHeader(Map<String, String> header) {
        return header;
    }

    abstract protected Response<T> onParseNetworkResponse(NetworkResponse response);

    public void onResponse(T response) {
        if (getContext() != null) {
            Response.Listener callBack = getOnResponseListener();
            if (callBack != null)
                callBack.onResponse(response);
        }
    }


    public void setTimeOut(int mTimeOut) {
        this.mTimeOut = mTimeOut;
    }

    public Object getRequestTag() {
        return this;
    }

    public RetryPolicy getRetryPolicy() {
        return new DefaultRetryPolicy(mTimeOut, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    public void cancel() {
        if (requestQueue != null && getRequestTag() != null) {
            requestQueue.cancelAll(getRequestTag());
        }
    }

    public void clearCache() {
        if (requestQueue != null) {
            requestQueue.getCache().remove(request.getCacheKey());
        }
    }

    public static void cancelAll() {
        if (requestQueue != null) {
            requestQueue.stop();
        }
    }

    public static void clearAllCache() {
        if (requestQueue != null) {
            requestQueue.getCache().clear();
        }
    }

    private boolean isCache;

    public boolean isCache() {
        return isCache;
    }

    public void setShouldCache(boolean cache) {
        isCache = cache;
    }

    public abstract String getBaseUrl();

    public static String getErrorMessageFrom(VolleyError error, String s) {
        return null;
    }

    public class RequestParams {
        private HashMap<String, String> params = new HashMap<>();

        public void put(String key, String value) {
            if (key == null || value == null || key.isEmpty())
                return;
            key = key.trim();
            value = value.trim();
            getParams().put(key, value);
        }

        public void put(String key, int value) {
            put(key, String.valueOf(value));
        }

        public void put(String key, double value) {
            put(key, String.valueOf(value));
        }

        public void put(String key, float value) {
            put(key, String.valueOf(value));
        }

        public void put(String key, boolean value) {
            put(key, String.valueOf(value));
        }

        public HashMap<String, String> getParams() {
            return params;
        }

        public void setParams(HashMap<String, String> params) {
            this.params = params;
        }

        public String getParamsString() {
            String paramsString = "";
            for (String key : params.keySet()) {
                paramsString = paramsString + (paramsString.isEmpty() ? "" : "&") + key + "=" + params.get(key);
            }
            return (paramsString.isEmpty() ? "" : "?") + paramsString;
        }
    }
}
