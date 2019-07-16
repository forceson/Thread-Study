package com.company;

public class ThreadEx16 {
    public void test() {
        ThreadEx16_1 th1 = new ThreadEx16_1("*");
        ThreadEx16_1 th2 = new ThreadEx16_1("**");
        ThreadEx16_1 th3 = new ThreadEx16_1("***");
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

    private class ThreadEx16_1 implements Runnable {
        // cache 갱신 에러 방지
        private volatile boolean suspended = false;
        private volatile boolean stopped = false;

        Thread th;

        public ThreadEx16_1(String name) {
            th = new Thread(this, name);
        }

        @Override
        public void run() {
            String name = th.getName();

            while (!stopped) {
                if (!suspended) {
                    System.out.println(name);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println(name + " - interrupted");
                    }
                } else {
                    // busy-waiting 방지
                    Thread.yield();
                }
            }
            System.out.println(name + " - stopped");
        }

        public void suspend() {
            suspended = true;
            th.interrupt();
            System.out.println(th.getName() + " - interrupt() by suspend()");
        }

        public void resume() {
            suspended = false;
        }

        public void stop() {
            stopped = true;
            th.interrupt();
            System.out.println(th.getName() + " - interrupt() by stop()");
        }

        public void start(){
            th.start();
        }
    }
}

