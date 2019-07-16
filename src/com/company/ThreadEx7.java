package com.company;

public class ThreadEx7 implements Runnable{
    @Override
    public void run() {
        for (int i = 10; i > 0; i--) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (Exception ignored){}
        }
    }
}
