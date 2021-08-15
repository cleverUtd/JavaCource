# 第2周作业

1. （选做）使用 GCLogAnalysis.java 自己演练一遍 串行/并行/CMS/G1 的案例。
2. （选做）使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。
3. （选做）如果自己本地有可以运行的项目，可以按照 2 的方式进行演练。
4. （必做）根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 和堆内存的总结，提交到 GitHub。
   应用不同的垃圾收集算法运行GCLogAnalysis.java


      运行过程中创建对象个数对比如下：

      -|128m| 256m | 512m | 1g | 2g | 4g
      ---|---|---|---|---|---|---
      **SerialGC** | OOM | 4632 | 8321 | 8481 | 8392 | 5544
      **ParallelGC** | OOM | 3134 | 7353 | 10851 | 11776 | 7812
      **ConcMarkSweepGC** | OOM | 4557 | 8736 | 11553 | 10172 | 9447
      **G1GC** | OOM | OOM | 8359 | 10916 | 10377 | 11679

      根据运行结果总结如下：
      - 堆内存设置较小如128M，会发生几次young gc后，之后因为old区占用比例大，频繁进行Full GC，但是每次Full GC效果不明显，堆内存逐渐耗尽，最终导致OOM；
      - SerialGC时，Young GC进行年轻代垃圾回收，Full GC只有堆中老年代进行垃圾回收；
      - ParallelGC时，Young GC进行年轻代垃圾回收，Full GC进行年轻代和老年代的垃圾回收；
      - 并行GC时，不配置Xms时，会比配置了Xmx=Xms时更早进行Young GC，因为初始化的年轻代会很小，很容易超出阈值进行垃圾回收；
      - CMS GC和并行GC，在堆内存都为4G时，CMS GC时产生的对象更多效果比并行GC要好，因为CMS GC在垃圾收集时只有特定的步骤才会进行暂停，线程暂停事件较短，可以处理更多的业务线程；
      - 串行GC在堆内存为4G时创建对象个数明显少于2G时的对象，原因是堆比较大时，业务暂停时间较长；


7. （选做）运行课上的例子，以及 Netty 的例子，分析相关现象。
8. （必做）写一段代码，使用 _HttpClient_ 或 OkHttp 访问 http://localhost:8801 ，代码提交到 GitHub
> 代码看：[OkHttpUtils.java](https://github.com/cleverUtd/JavaCource/blob/main/week02/src/main/java/httpserver/OkHttpUtils.java)