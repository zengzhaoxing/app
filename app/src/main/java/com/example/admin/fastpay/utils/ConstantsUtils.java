package com.example.admin.fastpay.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by sun on 2017/5/18.
 */

public class ConstantsUtils {

    public final static String JPUSH_RECEIVER = "jpush_receiver";
    public final static String MENDIAN_SHOUKUAN = "mendian_xinxi";
    public final static String SHOUKUAN_POSITION = "shoukan_position";
    public final static String MENDIAN_DATA = "mendian_data";

    public static final String IMAGE_DIR = Environment.getExternalStorageDirectory() + File.separator + "Android截屏";
    public static final String SCREEN_SHOT ="screenshot.png";

    public static final String JIETU ="invite.jpg";
}
