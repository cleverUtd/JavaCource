给前面课程提供的 Student/Klass/School 实现自动配置和 Starter

# 使用方式

## 引入依赖

```
 <dependency>
    <groupId>com.zclau.java</groupId>
    <artifactId>school-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
```

## 配置application.yml
```
spring:
  school:
    name: Tsinghua University
    students:
      - id: 101
        name: student101
      - id: 102
        name: student102
```
