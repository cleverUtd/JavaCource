package homework;

import java.util.Random;

public class hw03 {

    static volatile int result;
    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();

        Thread task = new Thread(() -> result = new Random().nextInt(100));
        task.start();
        task.join();

        System.out.println("异步计算结果为："+ result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}
