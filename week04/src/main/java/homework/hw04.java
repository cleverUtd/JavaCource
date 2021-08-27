package homework;

import java.util.Random;

public class hw04 {

    static volatile Integer result;
    static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();


        Thread task = new Thread(new Task());
        task.start();

        synchronized (lock) {
            while (result == null) {
                lock.wait();
            }
        }

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                result = new Random().nextInt(100);
                lock.notify();
            }
        }
    }
}
