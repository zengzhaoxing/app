//package com.example.admin.fastpay.jsonparse;
//
//import com.example.admin.fastpay.model.MenuModel;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by admin on 2017/7/17.
// */
//
//public class MenuParser {
//    private static List<MenuModel> list;
//    public static List<MenuModel> parseData(String json){
//        list = new ArrayList<>();
//
//        if (json!=null){
//            try {
//                JSONArray jsonArray = new JSONArray(json);
//                for (int i = 0;i<jsonArray.length();i++){
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    String name = jsonObject.getString("name");
//                    String url = jsonObject.getString("url");
//                    String icon_url = jsonObject.getString("icon_url");
//
//                    list.add(new MenuModel(name,url,icon_url));
//
//                }
//                return  list;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//            return  list;
//    }
//}
