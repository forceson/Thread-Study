package com.company;

public class ThreadEx10 implements Runnable {
    private static boolean autoSave = false;

    public void test() {
        Thread t = new Thread(new ThreadEx10());
        t.setDaemon(true);
        t.start();

        for (int i = 0; i <= 12; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            System.out.println(i);

            if (i == 5) {
                autoSave = true;
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException ignored) {
            }

            if (autoSave) {
                autoSave();
            }
        }
    }

    private void autoSave() {
        System.out.println("작업파일이 자동저장되었습니다.");
    }
}
