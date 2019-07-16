package com.company;

public class ThreadEx5 extends Thread {
    public static long startTime = 0;

    @Override
    public void run() {
        for (int i = 0; i < 300; i++) {
            System.out.printf("%s", new String("|"));
        }
        System.out.print("소요시간2: " + (System.currentTimeMillis() - ThreadEx5.startTime));
    }
}
