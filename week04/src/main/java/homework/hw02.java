package homework;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class hw02 {

    static AtomicInteger result = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();


        new Thread(() -> result.set(new Random().nextInt(100))).start();

        Thread.sleep(1000L);
        System.out.println("异步计算结果为："+ result.get());
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}
