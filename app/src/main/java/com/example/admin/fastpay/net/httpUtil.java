package com.example.admin.fastpay.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.admin.fastpay.model.response.QuickPayResp;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class httpUtil {

    private String charset = "utf-8";
    private Integer connectTimeout = null;
    private Integer socketTimeout = null;
    private String proxyHost = null;
    private Integer proxyPort = null;

    /**
     * Do GET request
     *
     * @param url
     * @return
     * @throws Exception
     * @throws IOException
     */
    public String doGet(String url) throws Exception {

        URL localURL = new URL(url);

        URLConnection connection = openConnection(localURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

        httpURLConnection.setRequestProperty("Accept-Charset", charset);
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("authenticate", "jxsshbzkglzx_8s09heji2");
        httpURLConnection.setRequestProperty("version", "ems_track_cn_1.0");


        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        if (httpURLConnection.getResponseCode() >= 300) {
            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
        }

        try {
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);

            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }

        } finally {

            if (reader != null) {
                reader.close();
            }

            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }

        }

        return resultBuffer.toString();
    }


    public void doPost(final String url, final String body, final Handler handler, final String sessionId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder resultBuffer = new StringBuilder();
                try {
                    URL localURL = new URL(url);
                    URLConnection connection = openConnection(localURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Accept-Charset", charset);
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(body.getBytes().length));
                    if (sessionId != null) {
                        httpURLConnection.setRequestProperty("Cookie", sessionId);
                    }
                    OutputStream outputStream = null;
                    OutputStreamWriter outputStreamWriter = null;
                    InputStream inputStream = null;
                    InputStreamReader inputStreamReader = null;
                    BufferedReader reader = null;
                    String tempLine = null;
                    outputStream = httpURLConnection.getOutputStream();
                    outputStreamWriter = new OutputStreamWriter(outputStream);
                    outputStreamWriter.write(body);
                    outputStreamWriter.flush();
                    if (httpURLConnection.getResponseCode() >= 300) {
                        throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
                    }
                    inputStream = httpURLConnection.getInputStream();
                    inputStreamReader = new InputStreamReader(inputStream);
                    reader = new BufferedReader(inputStreamReader);
                    while ((tempLine = reader.readLine()) != null) {
                        resultBuffer.append(tempLine);
                    }
                    Message message = Message.obtain();
                    message.obj = resultBuffer.toString();
                    handler.sendMessage(message);
                } catch (Exception e) {
                    handler.sendMessage(Message.obtain());
                }

            }
        }).start();
    }

    private URLConnection openConnection(URL localURL) throws IOException {
        URLConnection connection;
        if (proxyHost != null && proxyPort != null) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            connection = localURL.openConnection(proxy);
        } else {
            connection = localURL.openConnection();
        }
        return connection;
    }


    private void renderRequest(URLConnection connection) {

        if (connectTimeout != null) {
            connection.setConnectTimeout(connectTimeout);
        }

        if (socketTimeout != null) {
            connection.setReadTimeout(socketTimeout);
        }

    }

    /*
     * Getter & Setter
     */
    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}

