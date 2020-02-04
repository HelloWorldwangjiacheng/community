## 码匠社区

##部署
###依赖
- Git
- JDK
- Maven
- MySQL
###步骤
- yum update
- yum install git
- mkdir App / cd App
- git clone git@github.com:HelloWorldwangjiacheng/community.git
- yum install maven(自动下载Java8)
- mvn -v
- mvn clean compile package 
- cp src/main/resources/application.properties src/main/resources/application-production.properties
- vim  src/main/resources/application-production.properties
- mvn package
- java -jar -Dspring.profiles.active=production target/community-0.0.1-SNAPSHOT.jar

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

mvn flyway:migrate  升级
mvn flyway:repair  升级失败后进行修复

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

CREATE TABLE `notification`(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	`notifier` BIGINT NOT NULL,
	`receiver` BIGINT NOT NULL,
	`outerId` BIGINT NOT NULL,
	`type` INT NOT NULL,
	`gmt_create` BIGINT NOT NULL,
	`status` INT DEFAULT 0 NOT NULL
	
);
```

