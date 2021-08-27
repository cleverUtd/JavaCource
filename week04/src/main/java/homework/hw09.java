package homework;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class hw09 {

    final static ReentrantLock lock = new ReentrantLock();
    final static Condition taskDone = lock.newCondition();
    static volatile Integer result;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();


        new Thread(() -> {
            lock.lock();
            // 模拟耗时操作
            try {
                Thread.sleep(1000L);
                result = new Random().nextInt(100);
                taskDone.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }).start();

        lock.lock();
        try {
            while (result == null) {
                taskDone.wait();
            }
        } finally {
            lock.unlock();
        }
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }
}
