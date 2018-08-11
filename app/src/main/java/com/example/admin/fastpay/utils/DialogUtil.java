package com.example.admin.fastpay.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;

import com.example.admin.fastpay.R;
import com.example.admin.fastpay.presenter.UpdateVersionPresenter;
import com.zxz.www.base.net.download.Downloader;
import com.zxz.www.base.utils.DateUtil;
import com.zxz.www.base.utils.GetNativeBitmapSDK;

import java.util.Calendar;

/**
 * Created by 曾宪梓 on 2018/1/5.
 */

public class DialogUtil {

    private static String expiryDate;

    public static void pickExpiry(final Activity activity, final OnDatePicker picker){
        expiryDate = null;
        DatePickerDialog dialog = new DatePickerDialog(activity,DatePickerDialog.THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;
                expiryDate = year + ".";
                if (monthOfYear < 10) {
                    expiryDate += "0" + month + ".";
                } else {
                    expiryDate += month + ".";
                }
                if (dayOfMonth < 10) {
                    expiryDate += "0" + dayOfMonth;
                } else {
                    expiryDate += dayOfMonth;
                }
                expiryDate += " - ";
                DatePickerDialog dialog =new DatePickerDialog(activity, AlertDialog.THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                         int month = monthOfYear + 1;
                        expiryDate += year + ".";
                        if (monthOfYear < 10) {
                            expiryDate += "0" + month + ".";
                        } else {
                            expiryDate += month;
                        }
                        if (dayOfMonth < 10) {
                            expiryDate += "0" + dayOfMonth;
                        } else {
                            expiryDate += dayOfMonth;
                        }
                        picker.onPicker(expiryDate);
                    }
                }, year,monthOfYear,dayOfMonth);
                dialog.setTitle("请选择结束日期");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,monthOfYear);
                calendar.set(Calendar.DATE,dayOfMonth + 1);
                dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                dialog.show();
            }
        }, DateUtil.getTodayYear(),DateUtil.getTodayMonth(),DateUtil.getTodayDay());
        dialog.setTitle("请选择起始日期");
        dialog.show();
    }

    public interface OnDatePicker {
        void onPicker(String date);
    }

    public static void showGetPhoto(final Activity activity, final OnPhotoGet getter) {
        final GetNativeBitmapSDK util = GetNativeBitmapSDK.getInstance();
        util.setShouldCrop(false);
        new AlertDialog.Builder(activity).setItems(new String[]{"拍照", "从相册中选择"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                if (which == 0) {
                    util.fromTakePhoto(new GetNativeBitmapSDK.CropFinishListener() {
                        @Override
                        public void onCropFinish(Bitmap bitmap) {
                            dialog.dismiss();
                            getter.onGet(bitmap);
                        }
                    });
                } else if (which == 1) {
                    util.fromSelectPhoto(new GetNativeBitmapSDK.CropFinishListener() {
                        @Override
                        public void onCropFinish(Bitmap bitmap) {
                            dialog.dismiss();
                            getter.onGet(bitmap);
                        }
                    });
                }
            }
        }).show();
    }

    public interface OnPhotoGet {
        void onGet(Bitmap photo);
    }

    private static ProgressBar mProgress;

    public static void checkUpdateApk(final Activity activity, final OnUpdateDialogClose onUpdateDialogClose) {
        final UpdateVersionPresenter updateVersionPresenter = new UpdateVersionPresenter(activity);
        updateVersionPresenter.setUpdateApkListener(new UpdateVersionPresenter.UpdateApkListener() {
            @Override
            public void onUpdateInfo() {
                if (updateVersionPresenter.haveNewVersion()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle(R.string.soft_update_title);
                    builder.setMessage(R.string.soft_update_info);
                    // 更新
                    builder.setPositiveButton(R.string.soft_update_updatebtn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // 构造软件下载对话框
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setTitle(R.string.soft_updating);
                            // 给下载对话框增加进度条
                            final LayoutInflater inflater = LayoutInflater.from(activity);
                            View v = inflater.inflate(R.layout.softupdate_progress, null);
                            mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
                            builder.setView(v);
                            // 取消更新
                            builder.setNegativeButton(R.string.soft_update_cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    updateVersionPresenter.cancelUpdateApk();
                                    if (onUpdateDialogClose != null) {
                                        onUpdateDialogClose.onClose();
                                    }
                                }
                            });
                            Dialog downloadDialog = builder.create();
                            downloadDialog.show();
                            updateVersionPresenter.updateApk();
                        }
                    });
                    // 稍后更新
                    builder.setNegativeButton(R.string.soft_update_later, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (onUpdateDialogClose != null) {
                                onUpdateDialogClose.onClose();
                            }
                        }
                    });
                    Dialog noticeDialog = builder.create();
                    noticeDialog.show();
                }
            }

            @Override
            public void onUpdating(float progress) {
                if (progress < 0) {
                    if (onUpdateDialogClose != null) {
                        onUpdateDialogClose.onClose();
                    }
                } else {
                    mProgress.setProgress((int) (progress * 100));
                }
            }
        });
        updateVersionPresenter.getUpdateInfo();
    }

    public interface OnUpdateDialogClose {
        void onClose();
    }



}
