package com.quinn.httpknife;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

public class Response {

    private int statusCode;
    private String reasonPhrase;
    private byte[] contentBytes;
    private String contentString;
    private Map<String, String> headers;
    private String charset;
    private boolean hasParseHeader = false;
    private HttpResponse response;

    private boolean requestSuccess;// network problem


    public Response() {
        this.requestSuccess = true;
    }

    public Response(HttpResponse response) throws IOException{
        this();
        this.response = response;
        parseStatusCode().parseReasonPhrase().parseHeaders().parseContent();
    }

    private Response parseStatusCode() {
        this.statusCode = response.getStatusLine().getStatusCode();
        return this;
    }

    private Response parseReasonPhrase() {
        this.reasonPhrase = response.getStatusLine().getReasonPhrase();
        return this;
    }

    private Response parseCharset() {
        if (hasParseHeader == false) {
            throw new IllegalStateException("You have not parse headers");
        }
        String contentType = headers.get(HTTP.CONTENT_TYPE);
        this.charset = HTTP.DEFAULT_CONTENT_CHARSET;
        if (contentType != null) {
            String[] params = contentType.split(";");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        this.charset = pair[1];
                        return this;
                    }
                }
            }
        }
        return this;
    }

    /**
     * turn reponse content into byte array
     */
    private Response parseContent() throws IOException{
        HttpEntity entity = response.getEntity();
        ByteArrayPool mPool = new ByteArrayPool(4096);

        PoolingByteArrayOutputStream bytes =
                new PoolingByteArrayOutputStream(mPool, (int) entity.getContentLength());
        byte[] buffer = null;
        try {
            InputStream in = entity.getContent();
            if (in == null) {
            }
            buffer = mPool.getBuf(1024);
            int count;
            while ((count = in.read(buffer)) != -1) {
                bytes.write(buffer, 0, count);
            }
            this.contentBytes = bytes.toByteArray();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                // Close the InputStream and release the resources by "consuming the content".
                entity.consumeContent();
            } catch (IOException e) {
                // This can happen if there was an exception above that left the entity in
                // an invalid state.
            }
            mPool.returnBuf(buffer);
            try {
                bytes.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (this.charset == null || this.contentBytes == null) {
            throw new IOException();
        }
        try {
            this.contentString = new String(this.contentBytes, this.charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return this;
    }

    private Response parseHeaders() {
        HttpLog.v("Begin ParseHeaders");
        Header[] rawHeaders = response.getAllHeaders();
        headers = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < rawHeaders.length; i++) {
            headers.put(rawHeaders[i].getName(), rawHeaders[i].getValue());
        }
        hasParseHeader = true;
        parseCharset();
        HttpLog.v("Headers = " + headers);
        return this;
    }

    public int statusCode() {
        return statusCode;
    }

    public String reasonPhrase() {
        return reasonPhrase;
    }

    public byte[] contentBytes() {
        return contentBytes;
    }

    public String body() {
        return contentString;
    }

    public JSONObject json() {
        try {
            return new JSONObject(contentString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray jsonArray() {
        try {
            return new JSONArray(contentString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> headers() {
        return headers;
    }

    public String charset() {
        return charset;
    }

    public boolean isHasParseHeader() {
        return hasParseHeader;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setSuccess(boolean isSuccess) {
        this.requestSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return this.requestSuccess;
    }


}
