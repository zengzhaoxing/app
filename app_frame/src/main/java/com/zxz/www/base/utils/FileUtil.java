package com.zxz.www.base.utils;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.zxz.www.base.net.download.Downloader;
import com.zxz.www.base.net.download.HttpDownloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by igola on 2017/9/19.
 */

public class FileUtil {

    private static final String TAG = "FileUtil";

    private static FileUtil _instance;

    public String getRootPath() {
        return rootPath;
    }

    private final String rootPath;

    public String getDevicesRootPath() {
        return devicesRootPath;
    }

    private final String devicesRootPath;

    public String getCachePath() {
        return cachePath;
    }

    public String getDownLoadPath() {
        return downLoadPath;
    }

    private final String cachePath;

    private final String downLoadPath;

    private FileUtil() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            devicesRootPath = Environment.getExternalStorageDirectory().toString();
        } else {
            devicesRootPath = Environment.getRootDirectory().toString();
        }
        rootPath = devicesRootPath +  "/" + AppInfoUtil.getPackageName();
        cachePath = rootPath + "/" + "缓存";
        downLoadPath = rootPath + "/" + "下载";
        File file1 = new File(cachePath);
        File file2 = new File(downLoadPath);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        if (!file2.exists()) {
             file2.mkdirs();
        }
    }

    public static FileUtil getInstance() {
        if (_instance == null) {
            _instance = new FileUtil();
        }
        File f = new File(_instance.rootPath);
        if (!f.exists()) {
            f.mkdirs();
        }
        return _instance;
    }

    public File createFile(String fileName) {
        File f = new File(rootPath + "/" + fileName);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    public synchronized Bitmap getBitmap(String imageName) {
        File tmpFile = new File(rootPath + "/" + imageName);
        if (tmpFile.exists()) {
            try {
                return createImageThumbnail(rootPath + "/" + imageName);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public void filterFile(File root,String[] suffixList,OnFileFilterListener listener) {
        if (root == null) {
            return;
        }
        File[] files = root.listFiles();
        for (int i = 0;files != null && i < files.length;i++) {
            File file = files[i];
            if (file.isDirectory()) {
                filterFile(file,suffixList,listener);
            }else if (file.isFile()) {
                if (suffixList == null) {
                    listener.onFileScanned(file.getPath());
                } else {
                    for (String suffix : suffixList) {
                        if (file.getName().endsWith(suffix.toLowerCase()) || file.getName().endsWith(suffix.toUpperCase())) {
                            listener.onFileScanned(file.getPath());
                            break;
                        }
                    }
                }
            }
        }
    }

    public interface OnFileFilterListener {
        void onFileScanned(String url);
    }

    public Bitmap createImageThumbnail(String filePath){
        Bitmap bitmap = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);

        opts.inSampleSize = computeSampleSize(opts, -1, 200*200);
        opts.inJustDecodeBounds = false;

        try {
            bitmap = BitmapFactory.decodeFile(filePath, opts);
        }catch (Exception e) {
            // TODO: handle exception
        }
        return bitmap;
    }

    public int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private int computeInitialSampleSize(BitmapFactory.Options options,int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :(int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public void saveImg(String url) {
        if (PermissionUtil.havePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Calendar calendar = Calendar.getInstance();
            final String fileName = DateUtil.calendarToString(calendar, "yyyy-MM-dd HH:mm:ss") + ".jpg";
            final HttpDownloader downloader = new HttpDownloader(url, fileName);
            downloader.setDownloadListener(new Downloader.DownLoadListener() {
                @Override
                public void onDownLoad(float progress) {
                    if (progress == -1) {
                        ToastUtil.toast("保存失败");
                    } else if (progress == 100) {
                        ToastUtil.toast("保存成功: " + downloader.getFileUrl());
                    }
                }
            });
            downloader.starDownload();
        } else {
            ToastUtil.toast("保存失败");
        }
    }

    public void saveImg(final Bitmap bitmap) {
        if (PermissionUtil.havePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Calendar calendar = Calendar.getInstance();
                        final String fileName = downLoadPath + DateUtil.calendarToString(calendar, "yyyy-MM-dd HH:mm:ss") + ".jpg";
                        FileOutputStream fis = new FileOutputStream(fileName);
                        fis.write(bitmap.getRowBytes());
                        fis.close();
                        ToastUtil.toast("保存成功: " + fileName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            ToastUtil.toast("保存失败");
        }
    }

}
