package homework;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class hw13 {

    static BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(1);
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start=System.currentTimeMillis();
        new Thread(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                blockingQueue.put(new Random().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        System.out.println("异步计算结果为："+ blockingQueue.take());
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}
