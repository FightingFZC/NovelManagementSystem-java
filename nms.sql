-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: nms
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL COMMENT ' ',
  `headImage` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'http://localhost:8080/img/headImage_default.png',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('a1','a1','http://localhost:8080/img/headImage_default.png');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `novel`
--

DROP TABLE IF EXISTS `novel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `novel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `creationTime` datetime NOT NULL,
  `lastModifiedTime` datetime NOT NULL,
  `data` mediumtext,
  `title` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_username` (`username`),
  CONSTRAINT `FK_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `novel`
--

LOCK TABLES `novel` WRITE;
/*!40000 ALTER TABLE `novel` DISABLE KEYS */;
INSERT INTO `novel` VALUES (38,'1','2021-12-18 23:17:18','2021-12-20 20:52:27','<p>测试第一篇</p><p>第一篇</p><p>帅帅付....</p>','第一篇第二次修改'),(39,'1','2021-12-18 23:23:52','2021-12-20 20:33:32','<p>测试第一篇</p><p>第一篇</p><p>帅帅付</p>','第2篇'),(40,'1','2021-12-19 10:40:00','2021-12-19 18:06:58','<p>测试第一篇</p><p>第一篇</p><p>帅帅付</p>','导入测试1'),(41,'1','2021-12-19 10:41:06','2021-12-19 18:06:58','<p>测试第一篇</p><p>第一篇</p><p>帅帅付</p>','null'),(42,'1','2021-12-19 10:49:19','2021-12-19 18:06:58','<p>测试第一篇</p><p>第一篇</p><p>帅帅付</p>','导入测试2'),(43,'1','2021-12-19 10:49:44','2021-12-19 18:06:58','<p>测试第一篇</p><p>第一篇</p><p>帅帅付</p>','导入测试2'),(44,'1','2021-12-19 10:56:25','2021-12-19 18:06:58','<p>测试第一篇</p><p>第一篇</p><p>帅帅付</p>','导入测试3'),(45,'1','2021-12-19 11:09:58','2021-12-19 18:06:58','<p>测试第一篇</p><p>第一篇</p><p>帅帅付</p>','1'),(46,'1','2021-12-19 11:10:05','2021-12-19 18:06:58','<p>测试第一篇</p><p>第一篇</p><p>帅帅付</p>','2'),(49,'1','2021-12-19 11:30:52','2021-12-19 18:06:58','<p>测试第一篇</p><p>第一篇</p><p>帅帅付</p>','5'),(55,'2','2021-12-19 19:27:14','2021-12-19 19:29:20','<p><b>帅帅付好帅</b></p>','第一篇文章11'),(57,'2','2021-12-19 19:30:34','2021-12-19 19:30:34','<p></p><p></p><p>付智超最帅</p><p></p><p></p><p></p><p>FuZhichao is the handsome man in the world.</p><p></p>','2'),(58,'1','2021-12-20 20:40:11','2021-12-20 20:40:11','<p>2131232131</p>','管理员添加的第一篇'),(59,'1','2021-12-20 20:40:52','2021-12-20 20:53:33','<p>22222222发生的说法</p>','管理员添加的第二篇');
/*!40000 ALTER TABLE `novel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `headImage` varchar(50) NOT NULL DEFAULT 'http://localhost:8080/img/headImage_default.png',
  `isFrozen` tinyint(1) NOT NULL DEFAULT '0',
  `email` varchar(30) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('1','1','http://localhost:8080/img/headImage_user1.jpg',0,'1378799690@qq.com'),('14','14','http://localhost:8080/img/headImage_default.png',0,'1378799690@qq.com'),('15','15','http://localhost:8080/img/headImage_default.png',0,'1378799690@qq.com'),('16','16','http://localhost:8080/img/headImage_default.png',0,'1378799690@qq.com'),('17','17','http://localhost:8080/img/headImage_default.png',0,'1378799690@qq.com'),('2','2','http://localhost:8080/img/headImage_user2.png',0,'1378799690@qq.com'),('3','3','http://localhost:8080/img/headImage_default.png',0,'1378799690@qq.com'),('4','4','http://localhost:8080/img/headImage_default.png',0,'1378799690@qq.com'),('5','5','http://localhost:8080/img/headImage_default.png',0,'1378799690@qq.com'),('6','6','http://localhost:8080/img/headImage_default.png',0,'1378799690@qq.com'),('7','7','http://localhost:8080/img/headImage_default.png',0,'1378799690@qq.com'),('8','8','http://localhost:8080/img/headImage_default.png',0,'1378799690@qq.com'),('9','9','http://localhost:8080/img/headImage_default.png',0,'1378799690@qq.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-21 11:57:45
