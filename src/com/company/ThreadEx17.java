package com.company;

public class ThreadEx17 {
    static long startTime = 0;

    public void test(){
        ThreadEx17_1 th1 = new ThreadEx17_1();
        ThreadEx17_2 th2 = new ThreadEx17_2();
        th1.start(); // main쓰레드가 th1 작업 끝날때까지 대기
        th2.start(); // main쓰레드가 th2 작업 끝날때까지 대기
        startTime = System.currentTimeMillis();

        try {
            th1.join();
            th2.join();
        } catch (InterruptedException e) {}

        System.out.println("소요시간: " + (System.currentTimeMillis() - startTime));
    }

    private class ThreadEx17_1 extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 300; i++) {
                System.out.print(new String("-"));
            }
        }
    }

    private class ThreadEx17_2 extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 300; i++) {
                System.out.print(new String("|"));
            }
        }
    }
}
