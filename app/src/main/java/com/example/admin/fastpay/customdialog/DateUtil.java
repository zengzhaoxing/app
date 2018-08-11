package com.example.admin.fastpay.customdialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lmt on 16/7/6.
 */
public class DateUtil {

    public static List<Integer> getDateForString(String date) {
        String[] dates = date.split("-");
        List<Integer> list = new ArrayList<>();
        list.add(Integer.parseInt(dates[0]));
        list.add(Integer.parseInt(dates[1]));
        list.add(Integer.parseInt(dates[2]));
        return list;
    }

}
