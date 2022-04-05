package com.example.ktlin_demo;

public class Singleton {
//    private static final Singleton s=new Singleton();
//    private Singleton(){
//
//    }
//    public static Singleton getSingleton(){
//        return s;
//    }
//    private Singleton(){}
//    private static Singleton instance;
//    public static synchronized Singleton getInstance(){
//        if (instance==null){
//            instance =new Singleton();
//        }
//        return instance;
//    }

//    private static Singleton instance;
//    private Singleton(){}
//    public static Singleton getInstance(){
//        if (instance==null){
//            synchronized (Singleton.class){
//                if (instance==null){
//                    instance=new Singleton();
//                }
//            }
//        }
//        return instance;
//    }
    private Singleton(){}
    public static Singleton getInstance(){
      return   SingletonHolder.s;
    }
    private static class SingletonHolder{
        private static final Singleton s=new Singleton();
    }
}
