package com.company;

import java.util.Iterator;
import java.util.Map;

public class ThreadEx11 extends Thread {
    public ThreadEx11(String name) {
        super(name);
    }

    @Override
    public void run() {
        Map map = getAllStackTraces();
        Iterator iterator = map.keySet().iterator();

        int x = 0;
        while(iterator.hasNext()) {
            Object obj = iterator.next();
            Thread t = (Thread) obj;
            StackTraceElement[] ste = (StackTraceElement[]) map.get(obj);

            System.out.println("[" + ++x + "] name : " + t.getName()
                    + ", group : " + t.getThreadGroup().getName()
                    + ", daemon : " + t.isDaemon());

            for (int i = 0; i < ste.length; i++) {
                System.out.println(ste[i]);
            }

            System.out.println();
        }
    }
}
