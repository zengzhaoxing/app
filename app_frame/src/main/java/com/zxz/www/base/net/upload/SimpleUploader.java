package com.zxz.www.base.net.upload;//package com.zxz.www.base.net.upload;
//
//import android.graphics.Bitmap;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.google.gson.Gson;
//import com.zxz.www.base.model.BaseModel;
//import com.zxz.www.base.utils.FileUtil;
//
//import java.io.ByteArrayOutputStream;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by 曾宪梓 on 2017/12/31.
// */
//
//public class SimpleUploader<Resp extends BaseModel> extends Uploader<Resp>{
//
//
//
////    private String end = "\r\n";
////    private String twoHyphens = "--";
////    private String boundary = "---------------------------823928434";
////
////    private List<AsyncTask> mTask = new ArrayList<>();
////
////    private void initTasks() {
////        for (final Bitmap bitmap : mBitmap) {
////            AsyncTask task = new AsyncTask<Object, Float, Resp>() {
////
////                int respCode;
////
////                @Override
////                protected Resp doInBackground(Object... params) {
////                    Resp resp = null;
////                    try {
////                        URL url = new URL(mUrl);
////                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
////                        for (Object o : mHeader.entrySet()) {
////                            Map.Entry entry = (Map.Entry) o;
////                            String key = (String) entry.getKey();
////                            String val = (String) entry.getValue();
////                            httpURLConnection.setRequestProperty(key, val);
////                        }
////                        httpURLConnection.setDoInput(true);
////                        httpURLConnection.setDoOutput(true);
////                        httpURLConnection.setUseCaches(false);
////                        httpURLConnection.setRequestMethod("POST");
////                        OutputStream os = httpURLConnection.getOutputStream();
////                        DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
////                        dos.writeBytes(twoHyphens + boundary + end);
////                        dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"PROFILE.jpg\"" + end);
////                        dos.writeBytes("Content-Type: image/jpeg" + end);
////                        dos.writeBytes(end);
////                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
////                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
////                        byte[] buf = baos.toByteArray();
////                        dos.write(buf, 0, buf.length);
////                        dos.writeBytes(end);
////                        dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
////                        dos.flush();
////                        InputStream is = httpURLConnection.getInputStream();
////                        String resultData = null;
////                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
////                        byte[] data = new byte[512];
////                        int len = 0;
////                        while ((len = is.read(data)) != -1) {
////                            byteArrayOutputStream.write(data, 0, len);
////                        }
////                        resultData = new String(byteArrayOutputStream.toByteArray());
////                        Log.i(TAG, "respCode = " + respCode);
////                        Log.i(TAG, "" + resultData);
////                        respCode = httpURLConnection.getResponseCode();
////                        resp = new Gson().fromJson(resultData, mRespClass);
////                        os.close();
////                        dos.close();
////                        httpURLConnection.disconnect();
////                    } catch (Exception e) {
////                        Log.i(TAG, "" + e);
////                        e.printStackTrace();
////                    }
////                    return resp;
////                }
////
////                @Override
////                protected void onPostExecute(Resp resp) {
////                    if (resp != null) {
////                        mResps.add(resp);
////                    }
////                    if (mUploadListener != null && mResps.size() == mTask.size()) {
////                        mUploadListener.onUpLoad(mResps);
////                    }
////                }
////
////                @Override
////                protected void onProgressUpdate(Float... values) {
////
////                }
////            };
////            mTask.add(task);
////        }
////
////    }
//
//    public SimpleUploader(String url,Class<Resp> cls,Bitmap... bitmap) {
//        super(url,cls,bitmap);
////        mHeader.put("Connection", "Keep-Alive");
////        mHeader.put("Charset", "UTF-8");
////        mHeader.put("Content-Type", "multipart/form-data;boundary=" + boundary);
////        initTasks();
//    }
//
//    @Override
//    public void starUpload() {
////        for (AsyncTask t : mTask) {
////            t.execute();
////        }
//    }
//
//    @Override
//    public void stopUpload() {
////        for (AsyncTask t : mTask) {
////            t.cancel(true);
////        }
//    }
//
//
//}
