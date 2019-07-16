package com.company;

import java.util.ArrayList;

public class ThreadWaitEx2 {
    public void test() {
        Table table = new Table(); // 여러 쓰레드가 공유하는 객체

        new Thread(new Cook(table), "COOK1").start();
        new Thread(new Customer(table, "donut"), "CUST1").start();
        new Thread(new Customer(table, "burger"), "CUST2").start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        System.exit(0);
    }

    private class Table {
        String[] dishNames = {"donut", "donut", "burger"};
        final int MAX_FOOD = 6;

        private ArrayList<String> dishes = new ArrayList<>();

        public synchronized void add(String dish) {
            // 테이블에 음식이 가득찼으면, 테이블에 음식을 추가하지 않는다.
            while (dishes.size() >= MAX_FOOD) {
                String name = Thread.currentThread().getName();
                System.out.println(name + " is waiting.");
                try {
                    wait();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
            dishes.add(dish);
            System.out.println("Dishes:" + dishes.toString());
            notify();
        }

        public void remove(String dishName) {
            synchronized (this) {
                String name = Thread.currentThread().getName();
                while (dishes.size() == 0) {
                    System.out.println(name + " is waiting.");
                    try {
                        wait();
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                }
                while (true) {
                    for (int i = 0; i < dishes.size(); i++) {
                        if (dishName.equals(dishes.get(i))) {
                            dishes.remove(i);
                            notify();
                            return;
                        }
                    }
                    try {
                        System.out.println(name + " is waiting.");
                        wait();
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                }
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
                    Thread.sleep(100);
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

