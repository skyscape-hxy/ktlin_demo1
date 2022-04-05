package com.example.ktlin_demo;

import java.util.HashMap;
import java.util.Map;

public class SingletomManager {
    private static Map<String,Object> objMap =new HashMap<String ,Object>();
    private SingletomManager (){}
    public  static void registerservice(String key ,Object instance){
        if (!objMap.containsKey(key)){
            objMap.put(key,instance);
        }

    }
    public static Object getService(String key){
        return objMap.get(key);
    }
}
