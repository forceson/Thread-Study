package com.company;

import java.util.HashMap;

public class ThreadEx20 {
    private HashMap<String, String> map1 = new HashMap<>();
    private HashMap<String, String> map2 = new HashMap<>();

    private final Object object1 = new Object();
    private final Object object2 = new Object();

    public void test() {
        ThreadEx20 threadEx20 = new ThreadEx20();
        System.out.println("Test Start");
        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                threadEx20.put1("A", "B");
                System.out.print(threadEx20.get2("C"));
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                threadEx20.put2("C", "D");
                System.out.print(threadEx20.get1("A"));
            }
        }).start();
        System.out.println("Test End");
    }

    private String get1(String key) {
        synchronized (object1) {
            return map1.get(key);
        }
    }

    private String get2(String key) {
        synchronized (object2) {
            return map2.get(key);
        }
    }

    private void put1(String key, String value) {
        synchronized (object1) {
            map1.put(key, value);
        }
    }

    private void put2(String key, String value) {
        synchronized (object2) {
            map2.put(key, value);
        }
    }
}
