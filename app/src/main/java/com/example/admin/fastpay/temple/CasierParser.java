//package com.example.admin.fastpay.jsonparse;
//
//import com.example.admin.fastpay.bean.CashierBean;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by admin on 2017/7/15.
// */
//
//public class CasierParser {
//    private static List<CashierBean> list;
//    public static  List<CashierBean> parseData(String json) {
//        list = new ArrayList<>();
//        if (json!=null){
//            try {
//                JSONArray jsonArray = new JSONArray(json);
//                for (int i = 0;i<jsonArray.length();i++){
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    String name = jsonObject.getString("name");
//                    String id = jsonObject.getString("id");
//                    String amount  = jsonObject.getString("amount");
//                    list.add(new CashierBean(name,id,amount));
//
//
//                }
//return list;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return list;
//    }
//    }
