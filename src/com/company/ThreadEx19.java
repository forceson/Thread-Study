package com.company;

public class ThreadEx19 {
    public void test() {
        Runnable r = new RunnableEx19();
        new Thread(r).start();
        new Thread(r).start();
    }

    private class RunnableEx19 implements Runnable {
        Account account = new Account();
        @Override
        public void run() {
            while(account.getBalance() > 0) {
                int money = (int) (Math.random() * 3 + 1) * 100;
                account.withdraw(money);
                System.out.println("balance:"+account.getBalance());
            }
        }
    }

    private class Account {
        private int balance = 1000;

        public int getBalance() {
            return balance;
        }

        public void withdraw(int money) {
            synchronized (this){
                if(balance >= money){
                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                    balance -= money;
                }
            }
        }
    }
}
