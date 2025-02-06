-- MySQL dump 10.13  Distrib 5.7.44, for Win64 (x86_64)
--
-- Host: localhost    Database: proyectorium
-- ------------------------------------------------------
-- Server version	5.7.44-log

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
use proyectorium;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creationDate` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `icon` longblob,
  `name` varchar(255) DEFAULT NULL,
  `pegi` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provider`
--

DROP TABLE IF EXISTS `provider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provider` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contractEnd` datetime DEFAULT NULL,
  `contractIni` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provider`
--

LOCK TABLES `provider` WRITE;
/*!40000 ALTER TABLE `provider` DISABLE KEYS */;
/*!40000 ALTER TABLE `provider` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `duration` int(11) DEFAULT NULL,
  `movieHour` varchar(255) DEFAULT NULL,
  `movieImage` longblob,
  `releaseDate` datetime DEFAULT NULL,
  `sinopsis` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `provider_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bv93d42qswtrd1b9384pxen57` (`provider_id`),
  CONSTRAINT `FK_bv93d42qswtrd1b9384pxen57` FOREIGN KEY (`provider_id`) REFERENCES `provider` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_category`
--

DROP TABLE IF EXISTS `movie_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie_category` (
  `movie_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  KEY `FK_ktxsmjb8vqj36krhr5typ3a2p` (`category_id`),
  KEY `FK_ftakch9ptobhrwaatfovr4nk6` (`movie_id`),
  CONSTRAINT `FK_ftakch9ptobhrwaatfovr4nk6` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`),
  CONSTRAINT `FK_ktxsmjb8vqj36krhr5typ3a2p` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_category`
--

LOCK TABLES `movie_category` WRITE;
/*!40000 ALTER TABLE `movie_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `movie_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_type` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `companyId` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `userType` varchar(255) DEFAULT NULL,
  `zip` int(11) DEFAULT NULL,
  `nSS` bigint(20) DEFAULT NULL,
  `numTickets` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `buyDate` datetime DEFAULT NULL,
  `numPeople` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `movie_id` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_e8xk2f29wem1asn719e2d72vq` (`movie_id`),
  KEY `FK_i0i7rws9vvh121bg8mibj73pe` (`user_id`),
  CONSTRAINT `FK_e8xk2f29wem1asn719e2d72vq` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`),
  CONSTRAINT `FK_i0i7rws9vvh121bg8mibj73pe` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

ALTER TABLE movie DROP FOREIGN KEY FK_bv93d42qswtrd1b9384pxen57;

ALTER TABLE movie ADD CONSTRAINT FK_bv93d42qswtrd1b9384pxen57
FOREIGN KEY (provider_id) REFERENCES provider(id)
ON DELETE SET NULL;

-- Insertar datos en la tabla "provider"
INSERT INTO proyectorium.provider (id, name, email, phone, contractIni, contractEnd, price)
VALUES 
    (1, 'Warner Bros.', 'contact@warnerbros.com', 111111111, '2023-01-01', '2026-01-01', 500000),
    (2, 'Universal Pictures', 'info@universal.com', 222222222, '2023-02-01', '2026-02-01', 400000),
    (3, 'Paramount Pictures', 'service@paramount.com', 333333333, '2023-03-01', '2023-12-01', 600000);


-- Insertar datos en la tabla "category"
INSERT INTO proyectorium.category (id, name, description, creationDate, pegi)
VALUES 
    (1, 'Action', 'Action-packed movies', '2025-01-01', 'PEGI_18'),
    (2, 'Comedy', 'Comedy movies for all ages', '2025-01-02', 'PEGI_12'),
    (3, 'Horror', 'Scary movies', '2025-01-03', 'PEGI_16');


-- Insertar datos en la tabla "movie"
INSERT INTO proyectorium.movie (id, title, duration, sinopsis, releaseDate, movieHour, provider_id)
VALUES 
    (1, 'The Dark Knight', 152, 'Batman faces off against the Joker.', '2008-07-18', 'HOUR_16', 1),
    (2, 'Jurassic Park', 127, 'Dinosaurs are brought back to life with disastrous results.', '1993-06-11', 'HOUR_18', 2),
    (3, 'Inception', 148, 'A thief enters the dreams of others to steal secrets.', '2010-07-16', 'HOUR_20', 3);


INSERT INTO proyectorium.movie_category()
VALUES
(1,1),
(1,3),
(2,1),
(3,3);


-- Insertar datos en la tabla "user"
INSERT INTO proyectorium.user (id, email, fullName, password, street, city, zip, companyId, userType, numTickets, user_type)
VALUES 
    (1, 'admin1@example.com', 'Admin One', 'password1', 'Street 1', 'City A', 10001, 1, 'ADMIN', NULL, 'UserEntity'),
    (2, 'customer1@example.com', 'Customer One', 'password2', 'Street 2', 'City B', 10002, 1, 'CUSTOMER', 5, 'UserEntity');


-- Insertar datos en la tabla "ticket"
INSERT INTO proyectorium.ticket (id, buyDate, price, numPeople, movie_id, user_id)
VALUES 
    (1, '2025-01-10', 20.00, 2, 1, 2),  -- "The Dark Knight" (2 people)
    (2, '2025-01-11', 12.00, 1, 2, 3);  -- "Jurassic Park" (1 person)

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-06 21:26:27
