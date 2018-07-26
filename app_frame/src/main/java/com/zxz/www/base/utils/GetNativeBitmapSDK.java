package com.zxz.www.base.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.soundcloud.android.crop.Crop;
import com.zxz.www.base.app.BaseApp;
import com.zxz.www.base.app.SDKConnector;

import java.io.InputStream;

/**
 * Created by zengxianzi on 2016/10/9.
 */
public class GetNativeBitmapSDK extends SDKConnector{

    private static GetNativeBitmapSDK mGetNativeBitmapSDK;

    private final static int REQUEST_TAKE_PHOTO = 100;

    private final static int REQUEST_SELECT_PHOTO = 101;

    private final static String PROFILE = "GetNativeBitmapUtil_output.jpg";

    private final static String TEMP_PROFILE = "GetNativeBitmapUtil_temp.jpg";

    public static GetNativeBitmapSDK getInstance() {
        if (mGetNativeBitmapSDK == null) {
            mGetNativeBitmapSDK = new GetNativeBitmapSDK();
        }
        return mGetNativeBitmapSDK;
    }

    @Override
    protected void onMainActivityResult(int requestCode, int resultCode, Intent data) {
        super.onMainActivityResult(requestCode, resultCode, data);
        callbackFromSystem(requestCode,resultCode,data);
    }

    public interface CropFinishListener {
        void onCropFinish(final Bitmap bitmap);
    }

    private CropFinishListener mCropFinishListener;

    private boolean mShouldCrop = true;

    public void setShouldCrop(boolean shouldCrop) {
        mShouldCrop = shouldCrop;
    }

    public void fromTakePhoto(final CropFinishListener cropFinishListener) {
        PermissionUtil.requestPermission(mActivity,Manifest.permission.CAMERA, new PermissionUtil.OnPermissionRequest() {
            @Override
            public void onResult(boolean isAllow) {
                if (isAllow) {
                    mCropFinishListener = cropFinishListener;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    String authorities = AppInfoUtil.getPackageName() + ".fileprovider";
                    Uri uri = FileProvider.getUriForFile(mActivity, authorities, FileUtil.getInstance().createFile(TEMP_PROFILE));
                    BaseApp.getContext().grantUriPermission(AppInfoUtil.getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION |
                            Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    mActivity.startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                }
            }
        });

    }

    public void fromSelectPhoto(CropFinishListener cropFinishListener) {
        mCropFinishListener = cropFinishListener;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        intent.putExtra("return-data", true);
        mActivity.startActivityForResult(intent, REQUEST_SELECT_PHOTO);
    }

    /**
     * call this method in onActivityResult(int requestCode, int resultCode, Intent data) of the Activity in which
     * GetNativeBitmapSDK is used
     * to get result.
     */
    public void callbackFromSystem(final int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                String authorities = AppInfoUtil.getPackageName() + ".fileprovider";
                if (mShouldCrop) {
                    Uri imageUri = FileProvider.getUriForFile(BaseApp.getContext(), authorities, FileUtil.getInstance().createFile(TEMP_PROFILE));
                    BaseApp.getContext().grantUriPermission(AppInfoUtil.getPackageName(), imageUri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    cropImage(imageUri);
                } else {
                    if (mCropFinishListener != null) {
                        mCropFinishListener.onCropFinish(FileUtil.getInstance().getBitmap(TEMP_PROFILE));
                        mCropFinishListener = null;
                    }
                }
                break;
            case REQUEST_SELECT_PHOTO:
                if (mShouldCrop) {
                    cropImage(data.getData());
                } else {
                    try {
                        if (mCropFinishListener != null) {
                            InputStream is =  BaseApp.getContext().getContentResolver().openInputStream(data.getData());
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            mCropFinishListener.onCropFinish(bitmap);
                            mCropFinishListener = null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Crop.REQUEST_CROP:
                final Bitmap bitmap = FileUtil.getInstance().getBitmap(PROFILE);
                if (mCropFinishListener != null) {
                    mCropFinishListener.onCropFinish(bitmap);
                    mCropFinishListener = null;
                }
                break;
        }
    }

    private void cropImage(Uri uri) {
        Uri outputUri = Uri.fromFile(FileUtil.getInstance().createFile(PROFILE));
        Crop.of(uri, outputUri).asSquare().start(mActivity);
    }


}
