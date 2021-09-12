# 第5周

## 内容回顾
1、Java8 Lambda/Stream
2、Lombok/Guava
3、设计原则与设计模式
4、单元测试与编程经验
5、性能与关系数据库
6、MySQL 与 SQL
7、数据库原理
8、参数优化与设计优化


## 作业
- [ ] 1.（选做）尝试使用 Lambda/Stream/Guava 优化之前作业的代码。

- [ ] 2.（选做）尝试使用 Lambda/Stream/Guava 优化工作中编码的代码。

- [ ] 3.（选做）根据课上提供的材料，系统性学习一遍设计模式，并在工作学习中思考如何用设计模式解决问题。

- [ ] 4.（选做）根据课上提供的材料，深入了解 Google 和 Alibaba 编码规范，并根据这些规范，检查自己写代码是否符合规范，有什么可以改进的。

- [ ] 5.（选做）基于课程中的设计原则和最佳实践，分析是否可以将自己负责的业务系统进行数据库设计或是数据库服务器方面的优化

- [x] 6.（必做）基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交 DDL 的 SQL 文件到 Github（后面 2 周的作业依然要是用到这个表结构）。

```
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `user_id` int(11) NOT NULL DEFAULT 0 COMMENT '用户id',
  `password`  varchar(64) NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(64) NOT NULL DEFAULT '' COMMENT '昵称',
  `email` varchar(64) NOT NULL DEFAULT '' COMENT '邮件'，
  `valid` tinyint(1) unsigned NOT NULL DEFAULT 1 COMMENT '0-无效 1-有效',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';

CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `order_seq` int(11) NOT NULL DEFAULT 0 COMMENT '订单号',
  `user_id` int(11) NOT NULL DEFAULT 0 COMMENT '用户id',
  `order_amount` bigint(20) NOT NULL DEFAULT 0 COMMENT '订单金额。 单位：分',
  `status` int(11)  NOT NULL DEFAULT 200 COMMENT '订单状态 100-已成单，未支付，200-已支付',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_seq` (`order_seq`)
  KEY `user_id`(`user_id`) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户订单';



CREATE TABLE `order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `order_seq` int(11) NOT NULL DEFAULT 0 COMMENT '订单号',
  `sku` varchar(64) NOT NULL DEFAULT '' COMMENT '商品sku'
  `amount` int(11) NOT NULL DEFAULT 0 COMMENT '购买数量', 
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `order_seq` (`order_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='订单明细';


CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `spu` varchar(64) NOT NULL DEFAULT '' COMMENT '商品编号',
  `product_name` varchar(64) NOT NULL DEFAULT '' COMMENT '商品名称。 如Iphone12',
  `category_id` int(11) NOT NULL DEFAULT 0 COMMENT '商品分类id',
  `attribute` text NOT NULL COMMENT '商品属性集合，json格式',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `spu` (`spu`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='商品表';



CREATE TABLE `product_specs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `spu` varchar(64) NOT NULL DEFAULT '' COMMENT '商品编号',
  `sku` varchar(64) NOT NULL DEFAULT '' COMMENT '商品规格编号',
  `product_specs_name` varchar(64) NOT NULL DEFAULT '' COMMENT '规格名称。 如 iphone12-128G',
  `stock` int(11) NOT NULL DEFAULT 0 COMMENT '库存',
  `price` bigint(11) NOT NULL DEFAULT 0 COMMENT '售卖价格。 单位：分',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sku` (`sku`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='商品规格表';
```

- [ ] 7.（选做）尽可能多的从“常见关系数据库”中列的清单，安装运行，并使用上一题的 SQL 测试简单的增删改查。

- [ ] 8.（选做）基于上一题，尝试对各个数据库测试 100 万订单数据的增删改查性能。

- [ ] 9.（选做）尝试对 MySQL 不同引擎下测试 100 万订单数据的增删改查性能。

- [ ] 10.（选做）模拟 1000 万订单数据，测试不同方式下导入导出（数据备份还原）MySQL 的速度，包括 jdbc 程序处理和命令行处理。思考和实践，如何提升处理效率。

- [ ] 11.（选做）对 MySQL 配置不同的数据库连接池（DBCP、C3P0、Druid、Hikari），测试增删改查 100 万次，对比性能，生成报告。

