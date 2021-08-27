package homework;

import java.util.Random;
import java.util.concurrent.*;

public class hw08 {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(1);
        FutureTask<Integer> futureTask = new FutureTask<>(new Task());
        executor.execute(futureTask);
        System.out.println("异步计算结果为：" + futureTask.get());
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
