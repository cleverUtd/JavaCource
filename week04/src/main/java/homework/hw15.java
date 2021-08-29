package homework;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

public class hw15 {

    static Semaphore semaphore = new Semaphore(0);
    static int result;
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start=System.currentTimeMillis();
        new Thread(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = new Random().nextInt(100);
            semaphore.release();
        }).start();

        semaphore.acquire();
        System.out.println("异步计算结果为："+ result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

    }
}
