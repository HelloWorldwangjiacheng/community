## 码匠社区

## 资料
[Spring 文档](https://spring.io/guides)
[Spring Web文档](https://spring.io/guides/gs/serving-web-content/)
[es社区](httPs://elasticsearch.cn/explore)
[Bootstrap文档](https://v3.bootcss.com/components)
[Github OAuth文档](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)



## 工具
https://git-scm.com/download

## SQL
```sql
CREATE TABLE USER(
	`id` BIGINT PRIMARY KEY AUTO_INCREMENT,
	`account_id` VARCHAR(100) ,
	`name` VARCHAR(50) ,
	`token` CHAR(36) ,
	`gmt_create` BIGINT ,
	`gmt_modified` BIGINT
);

SELECT * FROM `user`;
```

mvn flyway:migrate

关于MBG的部分：http://mybatis.org/generator/running/runningWithMaven.html

The MBG Maven plugin includes one goal: mybatis-generator:generate
The goal is not automatically executed by Maven. It can be executed in two ways.

The goal can be executed from the command line with the command:mvn mybatis-generator:generate
You can pass parameters to the goal with standard Maven command line properties. For example:

mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

```sql
CREATE TABLE `comment`(
	id BIGINT AUTO_INCREMENT PRIMARY KEY, /**/
	parent_id BIGINT NOT NULL,/*父类id*/
	`type` INT NOT NULL,/*父类类型*/
	commentator INT NOT NULL,/*评论人id*/
	gmt_create BIGINT NOT NULL,/*创建时间*/
	gmt_modified BIGINT NOT NULL,/*更新时间*/
	like_count INT DEFAULT 0/*点赞数*/
	
);
```