package com.company;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadWaitEx3 {

    public void test() {
        Table table = new Table(); // 여러 쓰레드가 공유하는 객체

        new Thread(new Cook(table), "COOK1").start();
        new Thread(new Customer(table, "donut"), "CUST1").start();
        new Thread(new Customer(table, "burger"), "CUST2").start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        System.exit(0);
    }

    private class Table {
        String[] dishNames = {"donut", "donut", "burger"};
        final int MAX_FOOD = 6;

        private ArrayList<String> dishes = new ArrayList<>();

        private ReentrantLock lock = new ReentrantLock();
        private Condition forCook = lock.newCondition();
        private Condition forDonut = lock.newCondition();
        private Condition forBurger = lock.newCondition();

        public void add(String dish) {
            lock.lock();
            // 테이블에 음식이 가득찼으면, 테이블에 음식을 추가하지 않는다.
            try {
                while (dishes.size() >= MAX_FOOD) {
                    String name = Thread.currentThread().getName();
                    System.out.println(name + " is waiting.");
                    try {
                        forCook.await();
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                }
                dishes.add(dish);
                System.out.println("Dishes:" + dishes.toString());
                if (dish.equals("donut")) {
                    forDonut.signal();
                } else if (dish.equals("burger")) {
                    forBurger.signal();
                }
            } finally {
                lock.unlock();
            }
        }

        public void remove(String dishName) {
            lock.lock();
            String name = Thread.currentThread().getName();
            try {
                while (dishes.size() == 0) {
                    System.out.println(name + " is waiting.(음식 없음)");
                    try {
                        if (dishName.equals("donut")) {
                            forDonut.await();
                        } else if (dishName.equals("burger")) {
                            forBurger.await();
                        }
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                }
                while (true) {
                    for (int i = 0; i < dishes.size(); i++) {
                        if (dishName.equals(dishes.get(i))) {
                            dishes.remove(i);
                            forCook.signal();
                            return;
                        }
                    }
                    try {
                        System.out.println(name + " is waiting.(원하는 음식 없음)");
                        if (dishName.equals("donut")) {
                            forDonut.await();
                        } else if (dishName.equals("burger")) {
                            forBurger.await();
                        }
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                }
            } finally {
                lock.unlock();
            }
        }

        public int dishNum() {
            return dishNames.length;
        }
    }

    private class Cook implements Runnable {
        private Table table;

        public Cook(Table table) {
            this.table = table;
        }

        @Override
        public void run() {
            while (true) {
                int idx = (int) (Math.random() * table.dishNum());
                table.add(table.dishNames[idx]);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    private class Customer implements Runnable {
        private Table table;
        private String food;

        public Customer(Table table, String food) {
            this.table = table;
            this.food = food;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                String name = Thread.currentThread().getName();

                table.remove(food);
                System.out.println(name + " ate a " + food);
            }
        }
    }
}

