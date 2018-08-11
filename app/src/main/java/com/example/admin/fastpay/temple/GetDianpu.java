//package com.example.admin.fastpay.jsonparse;
//
//import com.example.admin.fastpay.bean.DianPuBean;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by admin on 2017/7/14.
// */
//
//public class GetDianpu{
//    private static List<DianPuBean> list;
//    public static  List<DianPuBean> parseData(String json){
//
//        list = new ArrayList<>();
//
//        if (json!=null){
//            try {
//              JSONArray jsonArray = new JSONArray(json);
//                for (int k= 0;k <jsonArray.length();k++){
//                    JSONObject jsonObject1 = jsonArray.getJSONObject(k);
//                    String name = jsonObject1.getString("store_name");
//                    String id = jsonObject1.getString("store_id");
//                    list.add(new DianPuBean(name,id));
//
//                }
//
//                return  list;
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return  list;
//    }
//}
