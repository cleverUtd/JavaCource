# 第1周 

## 内容回顾
- 字节码技术
- 类加载器
- 内存模型
- 启动参数
- jdk内置工具
- 各种GC

## 作业
- [x] 1（选做）自己写一个简单的 Hello.java，里面需要涉及基本类型，四则运行，if 和 for，然后自己分析一下对应的字节码，有问题群里讨论。
- [x] 2（必做）自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。
> 具体代码看：[XlassLoader.java](https://github.com/cleverUtd/JavaCource/blob/main/week01/src/main/java/classloader/XlassLoader.java)
- [x] 3（必做）画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的关系。
> [内存参数关系图](https://github.com/cleverUtd/JavaCource/blob/main/week01/src/main/resources/%E5%86%85%E5%AD%98%E5%8F%82%E6%95%B0%E5%85%B3%E7%B3%BB.png)
- [ ] 4（选做）检查一下自己维护的业务系统的 JVM 参数配置，用 jstat 和 jstack、jmap 查看一下详情，并且自己独立分析一下大概情况，思考有没有不合理的地方，如何改进。

