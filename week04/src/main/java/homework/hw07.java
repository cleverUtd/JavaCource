package homework;

import java.util.Random;
import java.util.concurrent.*;

public class hw07 {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<Integer> future = executor.submit(new Task());
        System.out.println("异步计算结果为：" + future.get());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        executor.shutdown();
    }

    static class Task implements Callable<Integer> {

        @Override
        public Integer call() {
            try {
                // 模拟耗时操作
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new Random().nextInt(100);
        }
    }
}
