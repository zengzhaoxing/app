//package com.zengzhaoxing.browser.util;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.TypeReference;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.lang.reflect.Type;
//import java.lang.reflect.TypeVariable;
//import java.util.ArrayList;
//import java.util.Map;
//
//public class Test {
//
//
//    public static void main(String[] args) throws Exception{
//        String s = "{\"code\":200,bean:{\"name\":\"name\",\"value\":1}}";
//        MyClass<Bean> myClass = getMyClass(s,Bean.class);
//        Object o = myClass.bean;
//        System.out.println(JSON.toJSONString(o));
//        System.out.println(new Gson().toJson(myClass));
//    }
//
//    public static <T> MyClass<T> getMyClass(String jsonString,Class<T> tClass) {
//        return JSON.parseObject(jsonString, new TypeReference<MyClass<T>>() {});
//    }
//
//    private static class MyClass<T> {
//
//        public T bean;
//
//        public int code;
//
//    }
//
//    private static class Bean {
//
//        String name;
//
//        int value;
//
//    }
//
//
//
//}
