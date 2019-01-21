package com.zxz.www.base.utils;


import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

    private static final String TAG = "StringUtils";

    private static final String MAIL_REGEX = "^([a-z0-9A-Z]+[-_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    private StringUtil() {
        throw new AssertionError();
    }

    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    public static String formatInteger(int number) {

        return String.format("%,d", number);
    }

    public static String formatFloat(float number) {
        if (number == (int) (number)) return String.format("%,.0f", number);
        return String.format("%,.2f", number);
    }

    public static String formatDouble(double number) {
        if (number == (int) (number)) return String.format("%,.0f", number);
        return String.format("%,.2f", number);
    }

    public static boolean isNumberOrLetter(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isDigit(c) && !Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        if (email == null || isEmpty(email) || email.length() > 30) {
            return false;
        } else if (email.contains("@") && email.contains(".")) {
            Pattern pattern = Pattern.compile(MAIL_REGEX);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        } else {
            return false;
        }
    }

    public static boolean isValidatePhoneNumber(String phoneNumber) {
        return phoneNumber != null && !isEmpty(phoneNumber) && isNumber(phoneNumber) && ((phoneNumber.length() == 11 && (phoneNumber.startsWith("13") || phoneNumber.startsWith("15") || phoneNumber.startsWith("18") || phoneNumber.startsWith("17")
                || phoneNumber.startsWith("14"))));
    }

    public static boolean isLengthBetween(String s, int from, int to) {
        if (s == null) {
            return false;
        } else {
            return s.length() > from && s.length() <= to;
        }
    }

    public static boolean isEqual(String s1, String s2) {
        if (s1 == null && s2 == null) {
            return true;
        } else if (s1 == null || s2 == null) {
            return false;
        } else {
            return s1.equals(s2);
        }
    }

    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    public static boolean isEquals(String actual, String expected) {
        if (actual == null && expected == null) {
            return true;
        } else if (actual != null && expected != null) {
            return actual.equals(expected);
        }else{
            return false;
        }
    }

    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    public static boolean isLetter(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isIdCardNo(String idCardNO) {
        boolean isID = false;
        if (idCardNO == null){
            return false;
        }
        int len = idCardNO.length();
        if (len != 18) {
            return false;
        } else {
            // 排除最后一位是:X的情况
            for (int i = 0; i < len; i++) {
                try {
                    isID = (idCardNO.charAt(i) + "").equalsIgnoreCase("X")|| Integer.parseInt("" + idCardNO.charAt(i)) > -1;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return isID;
    }


}

