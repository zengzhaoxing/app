package com.zxz.www.base.utils;

import java.math.BigDecimal;

/**
 * Created by igola on 2017/10/16.
 */

public class MathUtil {

    public static String getFormatSize(double size) {

        double kiloByte = size / 1024;

        if (kiloByte < 1) {

            return size + "Byte";

        }

        double megaByte = kiloByte / 1024;

        if (megaByte < 1) {

            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));

            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";

        }

        double gigaByte = megaByte / 1024;

        if (gigaByte < 1) {

            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));

            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";

        }

        double teraBytes = gigaByte / 1024;

        if (teraBytes < 1) {

            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));

            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";

        }

        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";

    }

    public static String getFormatDuration(int ms) {
        if (ms < 1000) {
            return "00:00";
        }
        int s = ms / 1000;
        int h = s / 3600;
        int min = s % 3600 / 60;
        s = s % 60;
        String hs = h > 0 ? h + ":" : "";
        String mins = min > 9 ? min + ":" : "0" + min + ":";
        String ss = s > 9 ? s + "" : "0" + s;
        return hs + mins + ss;
    }

}
