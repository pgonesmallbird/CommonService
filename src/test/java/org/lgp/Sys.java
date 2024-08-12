package org.lgp;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.List;

public class Sys extends Thread{
    private volatile boolean stop = false;

    @Override
    public void run() {
        while (!stop) {
            System.out.println("Thread is running...");
        }
        System.out.println("Thread is over...");
    }

    static class Horse {
        public static void race(Object name, int rate) {
            System.out.println("Horse name=" + name + " rate=" + rate);
        }
    }

    public static void main(String[] args) {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(void.class, Object.class, int.class);
        MethodHandle handle = null;
        try {
            handle = lookup.findStatic(Horse.class, "race", methodType);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            handle.invokeExact("test1", 1);
        } catch (Throwable e) {
            System.out.println("test1 failed");
        }

        try {
            handle.invoke("test2", 2);
        } catch (Throwable e) {
            System.out.println("test2 failed");
        }

        List<String> list = new ArrayList<>();
        list.add("str1");
        list.add(2,"str2");
        System.out.println(list);
    }
}
