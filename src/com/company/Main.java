package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        Thread thread = new Thread(thread2);

        for(int i = 0; i < 5; i++){
            System.out.println(i);
        }
        thread1.start();
        thread.start();


        ThreadEx5 threadEx5 = new ThreadEx5();
        threadEx5.start();
        ThreadEx5.startTime = System.currentTimeMillis();

        for (int i = 0; i < 300; i++) {
            System.out.printf("%s", new String("-"));
        }
        System.out.print("소요시간1: " + (System.currentTimeMillis() - ThreadEx5.startTime));

        // ...

        // Call stack 첫 번째 메서드가 run메서드라서 main 메서드가 Call stack에 없다.
        ThreadEx2 threadEx2 = new ThreadEx2();
        threadEx2.start();

        // 새로운 Thread 생성되지 않았으므로 main Thread에서 Call stack이 쌓임. 그래서 main 메서드도 포함.
        threadEx2.run();

//        ThreadEx6 threadEx6 = new ThreadEx6();
//        threadEx6.test();


//        ThreadEx7 threadEx7 = new ThreadEx7();
//        Thread thread3 = new Thread(threadEx7);
//        thread3.start();
//        String input = JOptionPane.showInputDialog("아무 값이나 입력하세요.");
//        System.out.println("입력하신 값은 " + input + "입니다.");

//        ThreadEx8 threadEx8 = new ThreadEx8();
//        ThreadEx8_2 threadEx8_2 = new ThreadEx8_2();
//        threadEx8_2.setPriority(7);
//        threadEx8.start();
//        threadEx8_2.start();

//        ThreadEx9 threadEx9 = new ThreadEx9();
//        threadEx9.test();

//        ThreadEx10 threadEx10 = new ThreadEx10();
//        threadEx10.test();

//        ThreadEx11 threadEx11 = new ThreadEx11("Thread1");
//        threadEx11.start();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        ThreadEx13 threadEx13 = new ThreadEx13();
//        threadEx13.test();

//        ThreadEx14 threadEx14 = new ThreadEx14();
//        threadEx14.test();

//        ThreadEx15 threadEx15 = new ThreadEx15();
//        threadEx15.test();

//        ThreadEx16 threadEx16 = new ThreadEx16();
//        threadEx16.test();

//        ThreadEx17 threadEx17 = new ThreadEx17();
//        threadEx17.test();

//        ThreadEx18 threadEx18 = new ThreadEx18();
//        threadEx18.test();

//        ThreadEx19 threadEx19 = new ThreadEx19();
//        threadEx19.test();

//        ThreadEx20 threadEx20 = new ThreadEx20();
//        threadEx20.test();

//        ThreadWaitEx1 threadWaitEx1 = new ThreadWaitEx1();
//        threadWaitEx1.test();

//        ThreadWaitEx2 threadWaitEx2 = new ThreadWaitEx2();
//        threadWaitEx2.test();

        ThreadWaitEx3 threadWaitEx3 = new ThreadWaitEx3();
        threadWaitEx3.test();
    }
}

class Thread1 extends Thread {
    public void run(){
        for(int i = 0; i < 5; i++){
            System.out.println(getName());
        }
    }
}

class Thread2 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName());
        }
    }
}
