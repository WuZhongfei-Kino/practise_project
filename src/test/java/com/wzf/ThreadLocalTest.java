package com.wzf;

import org.junit.Test;

public class ThreadLocalTest {
    @Test
    public void testThreadLocalSetAndGet(){
        //1.提供一个ThreadLocal对象
        ThreadLocal<Object> thread = new ThreadLocal<>();
        //2.开启两个线程
        new Thread(()->{
            thread.set("张三");
            System.out.println(Thread.currentThread().getName()+":"+thread.get());
            System.out.println(Thread.currentThread().getName()+":"+thread.get());
            System.out.println(Thread.currentThread().getName()+":"+thread.get());
        },"蓝色").start();

        new Thread(()->{
            thread.set("李四");
            System.out.println(Thread.currentThread().getName()+":"+thread.get());
            System.out.println(Thread.currentThread().getName()+":"+thread.get());
            System.out.println(Thread.currentThread().getName()+":"+thread.get());
        },"绿色").start();
    }
}
