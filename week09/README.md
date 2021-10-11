# 第9周

1. 改造自定义 RPC 的程序

- 将服务端写死查找接口实现类变成泛型和反射；
- 将客户端动态代理改成 AOP，添加异常处理；
- 使用 Netty+HTTP 作为 client 端传输方式。

## 助教建议
```
1.HTTP method，是否允许使用 GET，PUT 之类？是基于约定，比如说接口方法名前缀是 GET，那就是 HTTP GET 请求；还是说基于注解，微服务接口定义上边有个注解，我们就解析注解

2.HTTP 如果服务端需要在 Header 里面传递参数，比如说你希望用 RPC 框架去调用一个已有的 HTTP REST API，它要求在 Header 里面传递参数，你怎么把一个本地方法调用的某些参数，放进去Header

3.怎么抽象，以允许你的 RPC 可以替换 HTTP 框架

4.怎么抽象，以允许你的用户可以用json 作为 content-type，也可以用protobuf来作为body

5.filter 设计，怎么设计允许不同的服务可以有不同的 filter，这些 filter 可能在前，在后，或者环绕执行；

客户端和服务端，就怎么确定服务，怎么传递参数，怎么解析响应，是第一个要解决的问题。

然后要注意，平时设计这种东西，要先有抽象，再有实现。始终把自己摆在一个设计者和用户的角度，比如说设计 Filter 接口，一方面你是设计者，一方面把自己代入用户。即，如果我是一个用户，我想扩展 Filter，我需要怎么做。

再比如，我设计 HTTP 通信层接口，我应该考虑到，假如我作为一个用户，我不希望用 okhttp，而是使用公司规定的，我该怎么替换；
再比如，我作为一个用户，公司要求的序列化，是 protobuf，那么我该怎么扩展已有接口；
设计者把自己当成用户，就比较能发现一些应该要抽象出来的东西。
直接基于 TCP 来传输，也是要解决类似的问题。比如说，我的RPC协议头包含啥，协议体，以及附加信息（或者说元数据），该怎么把这些东西编成二进制等。
```

## 优秀作业参考
- https://github.com/haibo-duan/geektime-study/blob/main/doc/JavaCourseStudy/week9/%E7%AC%AC%E4%B9%9D%E5%91%A8%E4%BD%9C%E4%B8%9A.md 
在分布式事务里面，额外考虑已有事务存在的情况

- https://gitee.com/pwhxbdk/spring-demo/tree/master/week9/rpcfx-demo-consumer/src/main/java/io/kimmking/rpcfx/demo/consumer 
这个的做法，里面用Scanner 来注入 BeanDefinition，是RPC和MyBatis之类的框架的一个核心步骤，应该说，学会了操作 BeanDefiniion这一类比较高级的API，就迈过了为 Spring 生态添砖加瓦的门槛

