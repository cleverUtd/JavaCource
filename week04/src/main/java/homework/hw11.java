package homework;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;

public class hw11 {

    static final CyclicBarrier barrier = new CyclicBarrier(2);
    static int result;
    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        long start = System.currentTimeMillis();

        new Thread(() -> {
            // 模拟耗时操作
            try {
                Thread.sleep(1000L);
                result = new Random().nextInt(100);
                barrier.await();
            } catch (BrokenBarrierException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        barrier.await();
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }
}
