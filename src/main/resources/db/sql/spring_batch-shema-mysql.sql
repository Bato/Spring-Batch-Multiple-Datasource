DROP SCHEMA IF EXISTS `spring_batch` ;
CREATE SCHEMA IF NOT EXISTS `spring_batch` 
	DEFAULT CHARACTER SET = 'utf8' 
	DEFAULT COLLATE 'utf8_general_ci';
	
GRANT ALL PRIVILEGES ON spring_batch.* TO `javauser`@`%`  IDENTIFIED BY 'javadude' ;
FLUSH PRIVILEGES;	
 