package com.zxz.www.base.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.zxz.www.base.app.BaseActivity;
import com.zxz.www.base.app.BaseApp;
import com.zxz.www.base.net.download.Downloader;
import com.zxz.www.base.net.download.HttpDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by igola on 2017/9/27.
 */

public class IntentUtil {

    public static Uri getFileUri(File file,Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return  FileProvider.getUriForFile(activity, AppInfoUtil.getPackageName() + ".fileprovider", file);
        } else {
            return Uri.parse("file://" + file.toString());
        }
    }

    public static void openPermissionSetting() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", AppInfoUtil.getPackageName(), null));
        BaseApp.getContext().startActivity(intent);
    }

    public static void send(Uri uri) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("video/*");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            BaseApp.getContext().startActivity(intent);
        } catch (Exception e) {
        }
    }

    public static void send(ArrayList<Uri> uris) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("video/*");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra(Intent.EXTRA_STREAM, uris);
            BaseApp.getContext().startActivity(intent);
        } catch (Exception e) {
        }

    }

    public static void installApk(File file, Activity activity) {
        if (file != null && file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            Uri uri = getFileUri(file, activity);
            intent.setDataAndType(uri,"application/vnd.android.package-archive");
            activity.startActivity(intent);
        }
    }

    public static void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ActivityCompat.checkSelfPermission(BaseApp.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        BaseApp.getContext().startActivity(intent);
    }

    public static void sendText(String url,Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, url);
        intent.setType("text/*");
        if (hasMatchIntent(intent)) {
            activity.startActivity(intent);
        } else {
            ToastUtil.toast("分享失败");
        }
    }

    public static void sendImg(final String url, final BaseActivity activity) {
        if (PermissionUtil.havePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Calendar calendar = Calendar.getInstance();
            final String fileName = DateUtil.calendarToString(calendar, "yyyy-MM-dd HH:mm:ss") + ".jpg";
            final HttpDownloader downloader = new HttpDownloader(url, fileName,false,activity);
            downloader.setDownloadListener(new Downloader.DownLoadListener() {
                @Override
                public void onDownLoad(float progress,Downloader downloader1) {
                    if (progress == -1) {
                        ToastUtil.toast("分享失败");
                        activity.hideLoadingView();
                    } else if (progress == 1) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.setType("image/*");
                        File file = new File(downloader.getFileUrl());
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        Uri uri = getFileUri(file,activity);
                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        if (hasMatchIntent(intent)) {
                            activity.startActivity(intent);
                        } else {
                            ToastUtil.toast("分享失败");
                        }
                    }
                }
            });
            downloader.starDownload();
        } else {
            ToastUtil.toast("分享失败");
        }
    }

    public static boolean hasMatchIntent(Intent intent) {
        PackageManager packageManager = BaseApp.getContext().getPackageManager();
        List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;
        return isIntentSafe;
    }

    void takePhoto(String cameraPhotoPath,Activity activity) {
        File cameraPhoto = new File(cameraPhotoPath);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri photoUri = getFileUri(cameraPhoto,activity);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        activity.startActivity(takePhotoIntent);
    }

    public static void openDir(Activity activity,String dir) {
        File file = new File(dir);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = getFileUri(file,activity);
        intent.setDataAndType(uri, "*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivity(intent);
    }

    public static void openFile(Activity activity,File file) {
        Uri photoUri = getFileUri(file,activity);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        MediaFileUtil.MediaFileType type = MediaFileUtil.getFileType(file.getName());
        if (type != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N && type.fileType == MediaFileUtil.FILE_TYPE_APK) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            intent.setDataAndType(photoUri, type.mimeType);
        } else {
            intent.setDataAndType(photoUri, "*/*");
        }
        activity.startActivity(intent);
    }



}
