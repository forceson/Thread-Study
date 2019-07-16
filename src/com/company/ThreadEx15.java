package com.company;

public class ThreadEx15 {
    public void test() {
        ThreadEx15_1 th1 = new ThreadEx15_1("*");
        ThreadEx15_1 th2 = new ThreadEx15_1("**");
        ThreadEx15_1 th3 = new ThreadEx15_1("***");
        th1.start();
        th2.start();
        th3.start();

        try {
            Thread.sleep(2000);
            th1.suspend();
            Thread.sleep(2000);
            th2.suspend();
            Thread.sleep(3000);
            th1.resume();
            Thread.sleep(3000);
            th1.stop();
            th2.stop();
            Thread.sleep(2000);
            th3.stop();
        } catch (InterruptedException e) { }
    }

    private class ThreadEx15_1 implements Runnable {
        // cache 갱신 에러 방지
        private volatile boolean suspended = false;
        private volatile boolean stopped = false;

        Thread th;

        public ThreadEx15_1(String name) {
            th = new Thread(this, name);
        }

        @Override
        public void run() {
            while (!stopped) {
                if (!suspended) {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
            System.out.println(Thread.currentThread().getName() + " - stopped");
        }

        public void suspend() {
            suspended = true;
        }

        public void resume() {
            suspended = false;
        }

        public void stop() {
            stopped = true;
        }

        public void start(){
            th.start();
        }
    }
}
