package com.chheang.drequest;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DFileRequest extends DRequest<File> {

    /**
     * Decoding lock so that we don't decode more than one image at a time (to avoid OOM's)
     */
    private static final Object sDecodeLock = new Object();

    private String dirPath;

    private String fileName = System.currentTimeMillis() + ".tmp";

    private String url;

    public static DFileRequest with(Context context) {
        return new DFileRequest(context);
    }

    public DFileRequest loadUrl(String url) {
        setUrl(url);
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DFileRequest saveTo(String dirPath, String fileName) {
        setFileName(fileName);
        setDirPath(dirPath);
        return this;
    }

    public DFileRequest(Context context) {
        super(context);
    }

    public String getDirPath() {
        if (dirPath == null || dirPath.isEmpty())
            dirPath = getContext().getExternalCacheDir().getPath();
        return dirPath;
    }

    public DFileRequest setDirPath(String dirPath) {
        this.dirPath = dirPath;
        return this;
    }

    public String getFileName() {
        if (fileName == null || fileName.isEmpty())
            fileName = System.currentTimeMillis() + ".tmp";
        return fileName;
    }

    public DFileRequest setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    @Override
    public int getMethod() {
        return Request.Method.GET;
    }

    @Override
    public Map<String, String> onCreateHeader(Map<String, String> header) {
        header = new HashMap<>();
        header.put("Content-Type", "multipart/form-data");
        return super.onCreateHeader(header);
    }

    private File mkdir(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                return dir;
            } else {
                return null;
            }
        }
        return dir;
    }

    /**
     * The real guts of parseNetworkResponse. Broken out for readability.
     */
    private Response<File> doParse(NetworkResponse response) {
        try {
            File mFile = mkdir(getDirPath());
            if (mFile != null) {
                mFile = new File(mFile.getPath() + File.separator + getFileName());
                FileOutputStream stream = new FileOutputStream(mFile);
                stream.write(response.data);
                stream.close();
                return Response.success(mFile, HttpHeaderParser.parseCacheHeaders(response));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.error(new ParseError(response));
    }

    @Override
    protected Response<File> onParseNetworkResponse(NetworkResponse response) {
        synchronized (sDecodeLock) {
            try {
                return doParse(response);
            } catch (OutOfMemoryError e) {
                VolleyLog.e("Caught OOM for %d byte image, url=%s", response.data.length, getRequestUrl());
                return Response.error(new ParseError(e));
            }
        }
    }

    @Override
    public String getBaseUrl() {
        return getUrl();
    }
}