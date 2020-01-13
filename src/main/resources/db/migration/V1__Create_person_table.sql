CREATE TABLE USER(
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`account_id` VARCHAR(100) ,
	`name` VARCHAR(50) ,
	`token` CHAR(36) ,
	`gmt_create` BIGINT ,
	`gmt_modified` BIGINT
);