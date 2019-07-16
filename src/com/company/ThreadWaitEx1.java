package com.company;

import java.util.ArrayList;

// ConcurrentModificationException, IndexOutOfBoundsException 발생 가능성
public class ThreadWaitEx1 {
    public void test() {
        Table table = new Table(); // 여러 쓰레드가 공유하는 객체

        new Thread(new Cook(table), "COOK1").start();
        new Thread(new Customer(table, "donut"), "CUST1").start();
        new Thread(new Customer(table, "burger"), "CUST2").start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        System.exit(0);
    }

    private class Table {
        String[] dishNames = {"donut", "donut", "burger"};
        final int MAX_FOOD = 6;

        private ArrayList<String> dishes = new ArrayList<>();

        public void add(String dish) {
            // 테이블에 음식이 가득찼으면, 테이블에 음식을 추가하지 않는다.
            if (dishes.size() >= MAX_FOOD)
                return;
            dishes.add(dish);
            System.out.println("Dishes:" + dishes.toString());
        }

        public boolean remove(String dishName) {
            for (int i = 0; i < dishes.size(); i++) {
                if (dishName.equals(dishes.get(i))) {
                    dishes.remove(i);
                    return true;
                }
            }
            return false;
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
                    Thread.sleep(1);
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
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
                String name = Thread.currentThread().getName();

                if (eatFood())
                    System.out.println(name + " ate a " + food);
                else
                    System.out.println(name + " failed to eat. : (");
            }
        }

        boolean eatFood() {
            return table.remove(food);
        }
    }
}
