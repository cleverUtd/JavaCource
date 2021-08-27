package homework;

import java.util.Random;

public class hw01 {

    static int result;
    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();
        new Thread(() -> result = new Random().nextInt(100)).start();

        Thread.sleep(1000L);
        System.out.println("异步计算结果为："+ result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}
