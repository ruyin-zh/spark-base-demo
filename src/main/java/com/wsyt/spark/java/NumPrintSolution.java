package com.wsyt.spark.java;

/**
 * @Author ruyin_zh
 * @Date 2020/11/3
 * @Title
 * @Desc
 **/
public class NumPrintSolution {

    private static class NumPrintRun implements Runnable {

        public void run(){
            while(count <= 100){
                synchronized(lock){
                    System.out.println(Thread.currentThread().getName() + ": " + count ++);
                    lock.notifyAll();

                    try{
                        if(count <= 100){
                            lock.wait();
                        }
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static volatile int count = 1;
    private static Object lock = new Object();

    public static void main(String[] args){
        new Thread(new NumPrintRun(),"Odd").start();
        new Thread(new NumPrintRun(),"Even").start();
    }

}
