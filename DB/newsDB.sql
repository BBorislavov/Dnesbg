-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: news_db
-- ------------------------------------------------------
-- Server version	5.7.14-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `category_id` int(11) DEFAULT NULL,
  `subcategory_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`subcategory_id`),
  UNIQUE KEY `category_id_UNIQUE` (`subcategory_id`),
  KEY `fk_category_idx` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (NULL,1,'Спорт'),(1,2,'Футбол'),(1,3,'тенис'),(1,5,'Волейбол'),(1,6,'Гребане');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `News_id` int(11) NOT NULL,
  `User_id` int(11) NOT NULL,
  `text` text NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`comment_id`,`News_id`,`User_id`),
  KEY `fk_table1_News1_idx` (`News_id`),
  KEY `fk_Comments_Users1_idx` (`User_id`),
  CONSTRAINT `fk_Comments_Users1` FOREIGN KEY (`User_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_table1_News1` FOREIGN KEY (`News_id`) REFERENCES `news` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `text` text NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (1,'Левски победи отново','Левски размаза Лудогорец','2016-09-26 14:05:35'),(2,'ЦСКА изпадна отново','ЦСКА е с 0 точки','2016-09-25 13:10:04'),(3,'Литекс стана ЦСКА','Литекст с преименува на ЦСКА','2016-09-23 15:00:00'),(4,'5-то ЦСКА се появи','Нов плевел','2016-09-20 20:00:00'),(5,'ЦСКА се затри','Всички ги мразят','2016-09-23 15:00:00'),(7,'ЦСКА остана без заплати','В ЦСКА нямат пари за храна','2016-09-28 16:20:33'),(8,'ЦСКА вече носят оранжеви екипи','В ЦСКА нямат пари за червени екипи','2016-09-28 16:33:48');
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news_has_categories`
--

DROP TABLE IF EXISTS `news_has_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news_has_categories` (
  `news_id` int(11) NOT NULL AUTO_INCREMENT,
  `subcategory_id` int(11) NOT NULL,
  PRIMARY KEY (`news_id`,`subcategory_id`),
  KEY `fk_News_has_Categories_News1_idx` (`news_id`),
  KEY `fk_News_has_Categories_Categories1_idx` (`subcategory_id`),
  CONSTRAINT `fk_News_has_Categories_Categories1` FOREIGN KEY (`subcategory_id`) REFERENCES `categories` (`subcategory_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_News_has_Categories_News1` FOREIGN KEY (`news_id`) REFERENCES `news` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news_has_categories`
--

LOCK TABLES `news_has_categories` WRITE;
/*!40000 ALTER TABLE `news_has_categories` DISABLE KEYS */;
INSERT INTO `news_has_categories` VALUES (1,2),(2,2),(3,2),(4,2),(5,2),(8,2);
/*!40000 ALTER TABLE `news_has_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `photos`
--

DROP TABLE IF EXISTS `photos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `photos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `news_id` int(11) NOT NULL,
  `url` varchar(100) NOT NULL DEFAULT 'http://gai-mn.org/Resources/Pictures/icon-default-news.png',
  PRIMARY KEY (`id`,`news_id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_table1_News2_idx` (`news_id`),
  CONSTRAINT `fk_table1_News2` FOREIGN KEY (`news_id`) REFERENCES `news` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `photos`
--

LOCK TABLES `photos` WRITE;
/*!40000 ALTER TABLE `photos` DISABLE KEYS */;
INSERT INTO `photos` VALUES (1,1,'https://static.pexels.com/photos/46710/pexels-photo-46710.jpeg'),(2,2,'http://gai-mn.org/Resources/Pictures/icon-default-news.png'),(3,3,'http://gai-mn.org/Resources/Pictures/icon-default-news.png'),(4,4,'http://gai-mn.org/Resources/Pictures/icon-default-news.png'),(8,5,'http://gai-mn.org/Resources/Pictures/icon-default-news.png'),(9,7,'http://gai-mn.org/Resources/Pictures/icon-default-news.png'),(10,8,'http://gai-mn.org/Resources/Pictures/icon-default-news.png');
/*!40000 ALTER TABLE `photos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(200) NOT NULL,
  `email` varchar(60) NOT NULL,
  `is_admin` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `password` (`password`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (8,'mesibg','e10adc3949ba59abbe56e057f20f883e','Mesi123@abv.bg',1),(23,'mesibg2','84a58aab786e71d6bccf7ecfdffd335d','mesibg@mail.bg',0),(26,'mesibg3','84a58aab786e71d6bccf7ecfdffd335d','mesibg2@mail.bg',0),(27,'admin6','e64b78fc3bc91bcbc7dc232ba8ec59e0','admin2@abv.bg',0),(29,'mesibg4','213e6cca0def32b8b2c8f9faf9e9d57e','asfasn@mail.bg',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_has_favourite_news`
--

DROP TABLE IF EXISTS `users_has_favourite_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_has_favourite_news` (
  `News_id` int(11) NOT NULL,
  `Users_id` int(11) NOT NULL,
  PRIMARY KEY (`News_id`,`Users_id`),
  KEY `fk_News_has_Users_Users1_idx` (`Users_id`),
  KEY `fk_News_has_Users_News1_idx` (`News_id`),
  CONSTRAINT `fk_News_has_Users_News1` FOREIGN KEY (`News_id`) REFERENCES `news` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_News_has_Users_Users1` FOREIGN KEY (`Users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_has_favourite_news`
--

LOCK TABLES `users_has_favourite_news` WRITE;
/*!40000 ALTER TABLE `users_has_favourite_news` DISABLE KEYS */;
/*!40000 ALTER TABLE `users_has_favourite_news` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-28 16:51:27
