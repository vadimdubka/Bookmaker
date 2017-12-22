-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bookmaker
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
-- Dumping data for table `bet`
--

LOCK TABLES `bet` WRITE;
/*!40000 ALTER TABLE `bet` DISABLE KEYS */;
INSERT INTO `bet` VALUES (4,1,'2','2017-12-06 07:56:50',1.00,'new'),(4,2,'1','2017-12-06 07:56:50',2.00,'new'),(4,21,'1','2017-12-16 16:50:45',1.00,'losing'),(4,25,'1','2017-12-16 16:50:45',1.00,'win'),(4,26,'X','2017-12-16 16:50:45',1.00,'paid'),(5,1,'1','2017-12-06 07:56:50',3.00,'new'),(5,22,'2','2017-12-16 16:50:45',2.00,'losing'),(5,25,'1','2017-12-16 16:50:45',1.00,'win'),(5,26,'1','2017-12-16 16:50:45',2.00,'win'),(6,1,'X','2017-12-06 07:56:50',2.00,'paid'),(6,22,'X','2017-12-06 07:56:50',1.22,'win'),(6,23,'X','2017-12-16 16:50:45',3.00,'losing'),(6,24,'1','2017-12-16 16:50:45',2.00,'win'),(6,27,'2','2017-12-16 16:50:45',4.00,'new'),(7,1,'X','2017-12-06 07:56:50',1.00,'new'),(7,23,'2','2017-12-16 16:50:45',1.00,'win'),(7,24,'X','2017-12-16 16:50:45',1.00,'losing'),(7,27,'2','2017-12-16 16:50:45',2.00,'win');
/*!40000 ALTER TABLE `bet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Чемпионат мира 2018',18),(18,'Футбол',NULL),(19,'Баскетбол',NULL),(20,'Хокей',NULL),(22,'Волейбол',NULL),(23,'Лига чемпионов УЕФА',18),(25,'Регби',NULL),(26,'Теннис',NULL),(27,'Гандбол',NULL),(28,'КХЛ',20),(29,'NXL',20),(30,'NBA',19),(31,'Евролига',19),(32,'Лига чемпионов',22),(33,'Чемпионат Европы 2018',22),(34,'Кубок Европейских Чемпионов',25),(35,'Australian Open 2018',26),(36,'ITF',26),(37,'Чемпионат Европы 2018',27),(38,'Россия. Премьер-лига',18),(39,'Англия. Премьер-лига',18),(40,'Чемпионат Беларуси',18);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,1,'2018-06-14 19:00:00','Россия','Саудовская Аравия',NULL,NULL),(2,1,'2018-06-15 19:00:00','Египет','Уругвай',NULL,NULL),(3,1,'2018-06-15 19:00:00','Морокко','Иран',NULL,NULL),(4,1,'2018-06-15 19:00:00','Португалия','Испания',NULL,NULL),(5,1,'2018-06-16 19:00:00','Франция','Австралия',NULL,NULL),(6,1,'2018-06-16 19:00:00','Аргентина','Исландия',NULL,NULL),(7,1,'2018-06-16 19:00:00','Перу','Дания',NULL,NULL),(8,1,'2018-06-16 19:00:00','Хорватия','Нигерия',NULL,NULL),(9,1,'2018-06-17 19:00:00','Коста-Рика','Сербия',NULL,NULL),(11,1,'2018-06-17 19:00:00','Бразилия','Швейцария',NULL,NULL),(17,1,'2018-06-18 19:00:00','Швеция','Южная Корея',NULL,NULL),(18,1,'2018-06-17 19:00:00','Германия','Мексика',NULL,NULL),(19,23,'2017-11-16 19:00:00','Базель','Манчестер Сити',1,2),(21,23,'2017-11-20 19:00:00','Ювентус','Тотенхем',2,2),(22,23,'2017-11-18 19:00:00','Порту','Ливерпуль',3,1),(23,23,'2017-11-19 19:00:00','Реал Мадрид','ПСЖ',4,0),(24,23,'2017-11-19 19:00:00','Бавария','Бешикташ',0,0),(25,23,'2017-11-20 19:00:00','Челси','Барселона',0,1),(26,23,'2017-11-18 19:00:00','Севилья','Манчестер Юнайтед',1,2),(27,23,'2017-11-16 19:00:00','Шахтер','Рома',2,3),(28,28,'2018-01-30 19:00:00','Динамо Минск','СКА',NULL,NULL),(29,28,'2018-01-29 19:00:00','ЦСКА','Витязь',NULL,NULL),(30,28,'2018-01-28 19:00:00','Слован','Автомобилист',NULL,NULL),(32,29,'2018-01-28 19:00:00','Бостон Брюинз','Нью-Джерси Девилз',NULL,NULL),(33,29,'2018-01-29 19:00:00','Нью-Йорк Айлендерс','Анахайм Дакс',NULL,NULL),(34,29,'2018-01-30 19:00:00','Питтсбург Пингвинз','Коламбус Блю Джекетс',NULL,NULL),(35,30,'2018-01-30 19:00:00','Кливленд Кавальерс','Чикаго Буллз',NULL,NULL),(36,30,'2018-01-29 19:00:00','Нью-Йорк Никс','Бостон Селтикс',NULL,NULL),(37,30,'2018-01-28 19:00:00','Финикс Санз','Мемфис Гриззлис',NULL,NULL);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `outcome`
--

LOCK TABLES `outcome` WRITE;
/*!40000 ALTER TABLE `outcome` DISABLE KEYS */;
INSERT INTO `outcome` VALUES (1,'1',1.36),(1,'2',10.50),(1,'X',1.20),(2,'1',5.00),(2,'2',1.68),(2,'X',3.90),(3,'1',2.35),(3,'2',3.40),(3,'X',3.00),(4,'1',4.50),(4,'2',1.86),(4,'X',3.40),(5,'1',2.50),(5,'2',3.40),(5,'X',2.70),(6,'1',2.50),(6,'2',3.40),(6,'X',2.70),(7,'1',2.50),(7,'2',3.40),(7,'X',2.70),(8,'1',2.50),(8,'2',3.40),(8,'X',2.70),(9,'1',2.50),(9,'2',3.40),(9,'X',2.70),(11,'1',2.50),(11,'2',3.40),(11,'X',2.70),(17,'1',2.50),(17,'2',3.40),(17,'X',2.70),(18,'1',2.50),(18,'2',3.40),(18,'X',2.70),(19,'1',2.50),(19,'2',3.40),(19,'X',2.70),(21,'1',2.50),(21,'2',3.40),(21,'X',2.70),(22,'1',1.75),(22,'2',3.40),(22,'X',2.70),(23,'1',2.00),(23,'2',3.40),(23,'X',2.70),(24,'1',1.05),(24,'2',3.40),(24,'X',2.70),(25,'1',1.38),(25,'2',3.40),(25,'X',2.70),(26,'1',2.84),(26,'2',3.40),(26,'X',2.70),(27,'1',1.77),(27,'2',1.55),(27,'X',2.70);
/*!40000 ALTER TABLE `outcome` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `outcome_type`
--

LOCK TABLES `outcome_type` WRITE;
/*!40000 ALTER TABLE `outcome_type` DISABLE KEYS */;
INSERT INTO `outcome_type` VALUES ('1','Победа первого участника.'),('2','Победа второго участника.'),('X','Ничья');
/*!40000 ALTER TABLE `outcome_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` VALUES (4,'unverified',20.00,'PETR','PETROVICH','PETROV','1991-06-15','unverified','LB2921653',NULL),(5,'basic',15.00,'OLEG','OLEGOVICH','ORLOV','1992-06-15','unverified','DF2133456',NULL),(6,'ban',30.00,'ZAHAR','ZAHAROVICH','SOKOLOV','1991-10-01','unverified','DF9854213',NULL),(7,'vip',25.00,'IVAN','IVANOVICH','IVANOV','1993-01-01','unverified','LJ7894565',NULL),(8,'unverified',10.00,'YURIY','ANDREEVICH','DUDE','1986-01-01','unverified','BN1594512',NULL),(9,'unverified',10.00,'ALEKSANDR','SERGEEVICH','PUSHKIN','1986-06-06','unverified','RT4561235',NULL);
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `player_status`
--

LOCK TABLES `player_status` WRITE;
/*!40000 ALTER TABLE `player_status` DISABLE KEYS */;
INSERT INTO `player_status` VALUES ('unverified',10.00,0.00),('basic',10.00,1000.00),('vip',20.00,2000.00),('ban',0.00,0.00);
/*!40000 ALTER TABLE `player_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,4,'2017-12-16 16:57:28',-2.00),(2,5,'2017-12-16 16:57:28',-2.00),(3,6,'2017-12-16 16:57:28',-3.00),(4,7,'2017-12-16 16:57:28',-1.50),(5,7,'2017-12-16 16:57:28',2.50),(6,4,'2017-12-16 16:57:28',1.27),(7,5,'2017-12-16 16:57:28',3.20),(8,6,'2017-12-16 16:57:28',1.80),(9,4,'2017-12-16 16:57:28',-1.20),(10,5,'2017-12-16 16:57:28',-2.30),(11,6,'2017-12-16 16:57:28',-1.90),(12,7,'2017-12-16 16:57:28',-2.00),(13,4,'2017-12-16 16:59:07',5.50),(14,6,'2017-12-16 16:59:07',-2.85),(15,7,'2017-10-16 16:59:07',-6.00),(16,5,'2017-12-16 16:59:07',-3.30),(17,8,'2017-11-16 16:57:28',-2.00),(18,9,'2017-10-16 16:57:28',3.00),(19,8,'2017-11-16 16:57:28',2.75),(20,9,'2017-11-16 16:57:28',1.44);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin@gmail.com','dc643ebcc01ea0d335160182b2543ecd','admin','2017-11-20 16:41:50'),(3,'analyst@gmail.com','1a32bd60ff06bf14031ca0df2daa467f','analyst','2017-11-21 01:54:48'),(4,'petrov@yandex.ry','533a197f9304e95e226cf5e8b4f6c627','player','2017-11-21 02:21:28'),(5,'orlov@gmail.com','3d789c309c67449f58d35a38a0672f32','player','2017-11-21 03:24:30'),(6,'sokolov@gmail.com','c9c4d4fae7a09d4e234754df2a0e9206','player','2017-11-21 04:43:54'),(7,'ivanov@gmail.com','545351618466d60adfe7efdd0d8e82d0','player','2017-12-01 10:51:15'),(8,'dude@gmail.com','1e433786990e22e35378f3a8560ba608','player','2017-12-15 16:30:02'),(9,'pushkin@gmail.com','9efce5a976743b151229822ab8faa4d1','player','2017-12-17 15:50:56'),(14,'volkov@mail.com','e5d5d61bc87c3933b1b90dbd500093ff','admin','2017-12-21 23:25:11');
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

-- Dump completed on 2017-12-22  1:05:34
