# 第4周

## 内容回顾
- Java并发包
- 什么是锁
- 并发原子类
- 并发工具类
- 常见线程安全类型
- 并发编程相关内容
- 并发编程经验总结

## 作业
- [ ] （选做）把示例代码，运行一遍，思考课上相关的问题。也可以做一些比较。
- [x] （必做）思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程? 写出你的方法，越多越好，提交到 GitHub。一个简单的代码参考:  https://github.com/kimmking/JavaCourseCodes/tree/main/03concurrency/0301 /src/main/java/java0/conc0303/Homework03.java
> [我的代码](https://github.com/cleverUtd/JavaCource/tree/main/week04/src/main/java/homework)
```
主要的写法分成这几类：
1.自旋
2.Sleep 等待结果
3.Thread 本身方法，join
4.同步工具类，Semaphore，CycliBarrier等
5.锁类：synchronize, lock, condition
6.并发集合类

前两种都要小心一点。
1.自旋的缺点在于，CPU消耗很大，而且可能陷入死循环，一般实际应用中都要加上循环内部sleep让出CPU，并且控制自旋次数或者时间防止死循环
2.现实中很难把握，该sleep 多久。

多线程同步，就是要多个线程要同时达到某种状态之后再继续，优秀考虑同步工具类。

多线程通信，就是互相直接传递数据，优先考虑并发集合

多线程竞争某个资源，优先考虑锁
```
- [ ] （选做）列举常用的并发操作 API 和工具类，简单分析其使用场景和优缺点。
- [ ] （选做）请思考: 什么是并发? 什么是高并发? 实现高并发高可用系统需要考虑哪些 因素，对于这些你是怎么理解的?
- [ ] （选做）请思考: 还有哪些跟并发类似 / 有关的场景和问题，有哪些可以借鉴的解决 办法。
- [x] （必做）把多线程和并发相关知识梳理一遍，画一个脑图，截图上传到 GitHub 上。 可选工具:xmind，百度脑图，wps，MindManage，或其他。
> [Java并发编程梳理](https://github.com/cleverUtd/JavaCource/tree/main/week04/src/main/resources/Java并发编程.png)

