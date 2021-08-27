package homework;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class hw10 {

    static final CountDownLatch countDownLatch = new CountDownLatch(1);
    static int result;
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        new Thread(() -> {
            // 模拟耗时操作
            try {
                Thread.sleep(1000L);
                result = new Random().nextInt(100);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        countDownLatch.await();
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }
}
