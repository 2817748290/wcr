/*
SQLyog  v12.2.6 (64 bit)
MySQL - 5.7.17 : Database - wcr
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wcr` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `wcr`;

/*Table structure for table `course_class` */

DROP TABLE IF EXISTS `course_class`;

CREATE TABLE `course_class` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `course_id` int(5) unsigned NOT NULL,
  `className` varchar(20) NOT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `course_class` */

insert  into `course_class`(`id`,`course_id`,`className`,`type`,`gmt_create`,`gmt_modified`) values 
(1,2,'1班',0,'2017-11-09 19:23:28','2017-11-10 16:36:05'),
(2,1,'计151',1,'2017-11-09 19:29:40','2017-11-09 19:29:40'),
(3,2,'2班',0,'2017-11-09 19:34:10','2017-11-10 16:36:38'),
(4,1,'计152',1,'2017-11-09 19:35:48','2017-11-09 19:35:48'),
(7,3,'测试1',0,'2017-11-11 01:38:20','2017-11-11 01:38:20');

/*Table structure for table `course_info` */

DROP TABLE IF EXISTS `course_info`;

CREATE TABLE `course_info` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(2500) DEFAULT NULL,
  `cover_img_url` varchar(1000) DEFAULT NULL,
  `is_backed` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `type` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `teacher_id` int(5) unsigned NOT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `course_info` */

insert  into `course_info`(`id`,`name`,`description`,`cover_img_url`,`is_backed`,`type`,`teacher_id`,`gmt_create`,`gmt_modified`) values 
(1,'计算机','计算机科学与技术专业菜市场','/api/courseInfo/download?filename=courseInfoUpload/2017-11/df97417f-f01c-4e50-a74e-3fb042680f88.timg',1,12,2,'2017-11-08 22:04:45','2017-11-11 13:05:51'),
(2,'绘画课','绘画课程','/api/courseInfo/download?filename=courseInfoUpload/2017-11/16ce83c6-0a1a-4f48-89fb-f7c3e43460bf.timg (1)',0,0,2,'2017-11-09 19:26:28','2017-11-11 13:01:19'),
(3,'测试1','测试测试测试','/api/courseInfo/download?filename=courseInfoUpload/2017-11/051583a3-1241-42b1-b51b-5478f04721c5.timg (2)',0,0,2,'2017-11-10 08:45:28','2017-11-11 13:01:46');

/*Table structure for table `course_node` */

DROP TABLE IF EXISTS `course_node`;

CREATE TABLE `course_node` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `level` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `parent_id` int(5) NOT NULL DEFAULT '0',
  `course_id` int(5) unsigned NOT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `course_node` */

/*Table structure for table `stu_class` */

DROP TABLE IF EXISTS `stu_class`;

CREATE TABLE `stu_class` (
  `class_id` int(5) unsigned NOT NULL,
  `student_id` int(5) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `stu_class` */

insert  into `stu_class`(`class_id`,`student_id`,`gmt_create`,`gmt_modified`) values 
(1,1,'2017-11-08 22:04:41','2017-11-08 22:04:41'),
(1,3,'2017-11-09 19:34:51','2017-11-09 19:34:51');

/*Table structure for table `user_group` */

DROP TABLE IF EXISTS `user_group`;

CREATE TABLE `user_group` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `user_group` */

insert  into `user_group`(`id`,`name`,`gmt_create`,`gmt_modified`) values 
(1,'教师','2017-11-09 19:27:35','2017-11-09 19:27:35'),
(2,'学生','2017-11-09 19:27:41','2017-11-09 19:27:41');

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `salt` varchar(50) NOT NULL,
  `is_locked` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `head_img_url` varchar(50) DEFAULT NULL,
  `default_class_id` int(5) DEFAULT NULL,
  `group_id` int(5) unsigned NOT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `user_info` */

insert  into `user_info`(`id`,`username`,`password`,`nickname`,`phone_number`,`salt`,`is_locked`,`head_img_url`,`default_class_id`,`group_id`,`gmt_create`,`gmt_modified`) values 
(1,'black','admin','小黑','123','asd',0,NULL,2,2,'2017-11-08 22:04:32','2017-11-09 21:50:05'),
(2,'lee','admin','李子明',NULL,'asd',0,NULL,NULL,1,'2017-11-09 19:27:21','2017-11-09 21:50:10'),
(3,'liu','admin','小刘','138','asd',0,NULL,4,2,'2017-11-09 19:35:28','2017-11-09 21:50:28');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
