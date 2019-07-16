package com.company;

import javax.swing.*;

public class ThreadEx14 extends Thread{
    public void test() {
        ThreadEx14 threadEx14 = new ThreadEx14();
        threadEx14.start();

        String input = JOptionPane.showInputDialog("아무 값이나 입력하세요.");
        System.out.println("입력하신 값은 " + input + "입니다.");
        threadEx14.interrupt();
        System.out.println("isInterrupted():" + threadEx14.isInterrupted());
    }

    @Override
    public void run() {
        int i = 10;

        while (i != 0 && !isInterrupted()) {
            System.out.println(i--);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // InterruptedException 발생하면서 interrupted 값은 false로 자동으로 변함. 그래서 따로 interrupt() 해줘야한다.
                interrupt();
            }
        }
        System.out.println("카운트가 종료되었습니다.");
    }
}
