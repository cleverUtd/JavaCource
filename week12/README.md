# 第12周

## 1. 配置 redis 的主从复制，sentinel 高可用，Cluster 集群。
### 1.1 redis主从复制
- 启动两个实例，端口是6379、6380；其中6379作为master，6380作为slave.
配置文件：
[redis6379.conf](https://github.com/cleverUtd/JavaCource/blob/main/week12/src/main/java/redis/redis6379.conf) 、
[redis6380.conf](https://github.com/cleverUtd/JavaCource/blob/main/week12/src/main/java/redis/redis6380.conf)
```
docker run -p 6379:6379 -v /Users/zclau/docker/redis/redis6379.conf:/opt/redis.conf -v /Users/zclau/docker/redis/data/redis6379:/opt/data --name redis6379 redis redis-server /opt/redis.conf

docker run -p 6380:6380 -v /Users/zclau/docker/redis/redis6380.conf:/opt/redis.conf -v /Users/zclau/docker/redis/data/redis6380:/opt/data --name redis6380 redis redis-server /opt/redis.conf
```

![master](https://github.com/cleverUtd/JavaCource/blob/main/week12/src/main/resources/master.png)
![slave](https://github.com/cleverUtd/JavaCource/blob/main/week12/src/main/resources/slave.png)

- 通过info命令，证明6379是以master角色，6380是salve

![6379 info](https://github.com/cleverUtd/JavaCource/blob/main/week12/src/main/resources/6379info.png)
![6380 info](https://github.com/cleverUtd/JavaCource/blob/main/week12/src/main/resources/6380info.png)

- 往master写数据，salve能读取到
- 
![6379set](https://github.com/cleverUtd/JavaCource/blob/main/week12/src/main/resources/6379set.png)
![6380read](https://github.com/cleverUtd/JavaCource/blob/main/week12/src/main/resources/6380read.png)

### 1.2 sentinel高可用
- 在上述部署的6379和6380主从下，通过配置sentinel实例实现主从切换，并且配置两个sentinel实例实现高可用

sentinel配置：
![sentinel0.conf](https://github.com/cleverUtd/JavaCource/blob/main/week12/src/main/java/redis/sentinel0.conf)
![sentinel1.conf](https://github.com/cleverUtd/JavaCource/blob/main/week12/src/main/java/redis/sentinel1.conf)

```
docker run -p 26379:26379 --name redis_sentinel0 \
  -v /Users/zclau/docker/redis/sentinel0.conf:/opt/sentinel0.conf \
  -v /Users/zclau/docker/redis/data/sentinel0:/data \
  redis redis-sentinel /opt/sentinel0.conf


  docker run -p 26380:26380 --name redis_sentinel1 \
  -v /Users/zclau/docker/redis/sentinel1.conf:/opt/sentinel1.conf \
  -v /Users/zclau/docker/redis/data/sentinel1:/data \
  redis redis-sentinel /opt/sentinel1.conf
```

- 停掉master实例

sentinel将6380切换成master
![sentinelSwitchMaster](https://github.com/cleverUtd/JavaCource/blob/main/week12/src/main/resources/sentinelSwitchMaster.png)

6380切换成master
![6380becomeMaster](https://github.com/cleverUtd/JavaCource/blob/main/week12/src/main/resources/6380becomeMaster.png)

- 重启6379实例

变为salve
![6379restart](https://github.com/cleverUtd/JavaCource/blob/main/week12/src/main/resources/6379restart.png)


## 2. 搭建 ActiveMQ 服务，基于 JMS，写代码分别实现对于 queue 和 topic 的消息生产和消费