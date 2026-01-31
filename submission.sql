-- MySQL dump 10.13  Distrib 8.0.44, for macos15.7 (arm64)
--
-- Host: localhost    Database: StudentManagement
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `student_courses`
--

DROP TABLE IF EXISTS `student_courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_courses` (
  `course_id` varchar(100) NOT NULL,
  `student_id` varchar(100) DEFAULT NULL,
  `course_name` varchar(100) DEFAULT NULL,
  `course_start` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `course_end` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`course_id`),
  KEY `FK_STUDENT` (`student_id`),
  CONSTRAINT `FK_STUDENT` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_courses`
--

LOCK TABLES `student_courses` WRITE;
/*!40000 ALTER TABLE `student_courses` DISABLE KEYS */;
INSERT INTO `student_courses` VALUES ('a00001','00001','JAVA','2026-01-23 12:28:49','2026-04-23 12:28:49'),('a00002','00002','JAVA','2026-01-23 12:28:49','2026-04-23 12:28:49'),('a00003','00003','AWS','2026-01-23 12:28:49','2026-04-23 12:28:49'),('a00004','00004','ENGLISH','2026-01-23 12:28:49','2026-04-23 12:28:49'),('a00005','00005','AWS','2026-01-23 12:28:49','2026-04-23 12:28:49');
/*!40000 ALTER TABLE `student_courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `id` varchar(100) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `kana_name` varchar(100) DEFAULT NULL,
  `nick_name` varchar(100) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `area` varchar(200) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `sex` varchar(100) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `isDeleted` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES ('00001','田中太郎','たなかたろう','タナタロ','ttttttt@gmail.com','東京都港区',23,'男',NULL,NULL),('00002','櫻井美穂','さくらいみほ','ミホミホ','uuuuuuuu@gmail.com','東京都世田谷区',33,'女',NULL,NULL),('00003','槇原希','まきはらのぞみ','マキノ','eeeeeeee@gmail.com','東京都渋谷区',19,'女',NULL,NULL),('00004','佐々木美沙','ささきみさ','ササキ','wwwwwwww@gmail.com','東京都板橋区',32,'女',NULL,NULL),('00005','高橋祐介','たかはしゆうすけ','ユウスケ','oooooooo@gmail.com','東京都渋谷区',20,'男',NULL,NULL),('00006','二階堂夢','にかいどうゆめ','ユメッチ','qqqqqqq.gmail.com','東京都八王子市',56,'女','特になし',NULL);
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-27  3:59:30
