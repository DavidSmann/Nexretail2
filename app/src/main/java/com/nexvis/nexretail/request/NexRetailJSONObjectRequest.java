package com.nexvis.nexretail.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.nexvis.nexretail.BuildConfig;
import com.nexvis.nexretail.request.AppRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public abstract class NexRetailJSONObjectRequest extends AppRequest<JSONObject> {
    public NexRetailJSONObjectRequest(Context context) {
        super(context);
    }

    @Override
    protected Response<JSONObject> onParseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

            try {
                if (BuildConfig.DEBUG) {
                    Log.i("RESPONSE<<<", this.getClass().getSimpleName()
                            + " (" + getContext().getClass().getSimpleName() + ") "
                            + response.statusCode + " " + new JSONObject(jsonString).toString(1));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    public void execute(Response.Listener onResponseListener) {
        super.execute(onResponseListener);
    }
}
