package homework;

import java.util.Random;

public class hw06 {

    static volatile Integer result;

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();


        Thread task = new Thread(new Task());
        task.start();

        while (task.isAlive()) {

        }
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            try {
                // 模拟耗时操作
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result = new Random().nextInt(100);
        }
    }
}
