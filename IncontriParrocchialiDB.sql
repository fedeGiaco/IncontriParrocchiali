-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: incontriparrocchiali
-- ------------------------------------------------------
-- Server version	5.7.31

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
-- Table structure for table `prenotazione`
--
CREATE DATABASE  IF NOT EXISTS `incontriparrocchiali` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `incontriparrocchiali`;

DROP TABLE IF EXISTS `prenotazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prenotazione` (
  `idPrenotazione` int(11) NOT NULL AUTO_INCREMENT,
  `giornoPrenotazione` date DEFAULT NULL,
  `numeroPosto` int(11) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `cognome` varchar(45) DEFAULT NULL,
  `sesso` varchar(1) DEFAULT NULL,
  `classe` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idPrenotazione`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prenotazione`
--

LOCK TABLES `prenotazione` WRITE;
/*!40000 ALTER TABLE `prenotazione` DISABLE KEYS */;
INSERT INTO `prenotazione` VALUES (1,'2021-02-12',1,'Mario','Rossi','M','Prima'),(2,'2021-02-12',3,'Tommaso','Gatti','M','Prima'),(3,'2021-02-12',4,'Maddalena','Bove','F','Prima'),(4,'2021-02-12',5,'Luigi','Cervi','M','Seconda'),(5,'2021-02-12',7,'Antonella','Muli','F','Terza'),(6,'2021-02-13',2,'Mario','Rossi','M','Prima'),(7,'2021-02-13',3,'Tommaso','Gatti','M','Prima'),(8,'2021-02-13',7,'Ratto','Sabino','F','Seconda'),(9,'2021-02-13',9,'Roberto','Cavalli','M','Seconda'),(10,'2021-02-13',10,'Antonella','Muli','F','Terza'),(11,'2021-02-14',1,'Mario','Rossi','M','Prima'),(12,'2021-02-14',2,'Edoardo','Lepre','M','Seconda'),(13,'2021-02-14',3,'Vivetta','Giaguaro','F','Seconda'),(14,'2021-02-14',5,'Roberto','Cavalli','M','Seconda'),(15,'2021-02-14',7,'Edoardo','Lepre','M','Seconda'),(16,'2021-02-14',8,'Vivetta','Giaguaro','F','Seconda'),(17,'2021-02-15',1,'Mario','Rossi','M','Prima'),(18,'2021-02-15',3,'Tommaso','Gatti','M','Prima'),(19,'2021-02-15',4,'Maddalena','Bove','F','Prima'),(20,'2021-02-15',5,'Luigi','Cervi','M','Seconda'),(21,'2021-02-15',7,'Antonella','Muli','F','Terza'),(22,'2021-02-16',1,'Mario','Rossi','M','Prima'),(23,'2021-02-16',2,'Tommaso','Gatti','M','Prima'),(24,'2021-02-16',3,'Ratto','Sabino','F','Seconda'),(25,'2021-02-16',9,'Roberto','Cavalli','M','Seconda'),(26,'2021-02-16',10,'Antonella','Muli','F','Terza'),(27,'2021-02-17',1,'Mario','Rossi','M','Prima'),(28,'2021-02-17',2,'Edoardo','Lepre','M','Seconda'),(29,'2021-02-17',3,'Vivetta','Giaguaro','F','Seconda'),(30,'2021-02-18',1,'Mario','Rossi','M','Prima'),(31,'2021-02-18',3,'Tommaso','Gatti','M','Prima'),(32,'2021-02-18',4,'Maddalena','Bove','F','Prima'),(33,'2021-02-18',5,'Luigi','Cervi','M','Seconda'),(34,'2021-02-18',7,'Antonella','Muli','F','Terza'),(35,'2021-02-19',2,'Mario','Rossi','M','Prima'),(36,'2021-02-19',3,'Tommaso','Gatti','M','Prima'),(37,'2021-02-19',7,'Ratto','Sabino','F','Seconda'),(38,'2021-02-19',9,'Roberto','Cavalli','M','Seconda'),(39,'2021-02-19',10,'Antonella','Muli','F','Terza'),(40,'2021-02-20',1,'Mario','Rossi','M','Prima'),(41,'2021-02-20',2,'Edoardo','Lepre','M','Seconda'),(42,'2021-02-20',3,'Vivetta','Giaguaro','F','Seconda'),(43,'2021-02-21',1,'Mario','Rossi','M','Prima'),(44,'2021-02-21',3,'Tommaso','Gatti','M','Prima'),(45,'2021-02-21',4,'Maddalena','Bove','F','Prima'),(46,'2021-02-21',5,'Luigi','Cervi','M','Seconda'),(47,'2021-02-21',7,'Antonella','Muli','F','Terza'),(48,'2021-02-22',2,'Mario','Rossi','M','Prima'),(49,'2021-02-22',3,'Tommaso','Gatti','M','Prima'),(50,'2021-02-22',7,'Ratto','Sabino','F','Seconda'),(51,'2021-02-22',9,'Roberto','Cavalli','M','Seconda'),(52,'2021-02-22',10,'Antonella','Muli','F','Terza'),(53,'2021-02-23',1,'Mario','Rossi','M','Prima'),(54,'2021-02-23',2,'Edoardo','Lepre','M','Seconda'),(55,'2021-02-23',3,'Vivetta','Giaguaro','F','Seconda');
/*!40000 ALTER TABLE `prenotazione` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-18 16:50:52
