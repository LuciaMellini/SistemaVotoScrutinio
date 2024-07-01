-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: sistvotoscrutinio
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cae`
--

DROP TABLE IF EXISTS `cae`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cae` (
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `scrutinatore` tinyint NOT NULL,
  `configuratore` tinyint NOT NULL,
  PRIMARY KEY (`email`),
  CONSTRAINT `email` FOREIGN KEY (`email`) REFERENCES `utente` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cae`
--

LOCK TABLES `cae` WRITE;
/*!40000 ALTER TABLE `cae` DISABLE KEYS */;
INSERT INTO `cae` VALUES ('prova@prova.com','7110eda4d09e062aa5e4a390b0a572ac0d2c0220',1,1);
/*!40000 ALTER TABLE `cae` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `cae_password_BEFORE_INSERT` BEFORE INSERT ON `cae` FOR EACH ROW BEGIN
SET NEW.password=sha1(NEW.password);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `cae_password_BEFORE_UPDATE` BEFORE UPDATE ON `cae` FOR EACH ROW BEGIN
	IF !(NEW.password <=> OLD.password) THEN
		SET NEW.password=sha1(NEW.password);
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `caeschedeelettorali`
--

DROP TABLE IF EXISTS `caeschedeelettorali`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `caeschedeelettorali` (
  `cae` varchar(45) NOT NULL,
  `schedaelettorale` int NOT NULL,
  PRIMARY KEY (`cae`,`schedaelettorale`),
  KEY `schedeelettoraliCae_idx` (`schedaelettorale`),
  CONSTRAINT `caeSchedeelettorali` FOREIGN KEY (`cae`) REFERENCES `cae` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `schedeelettoraliCae` FOREIGN KEY (`schedaelettorale`) REFERENCES `schedaelettorale` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caeschedeelettorali`
--

LOCK TABLES `caeschedeelettorali` WRITE;
/*!40000 ALTER TABLE `caeschedeelettorali` DISABLE KEYS */;
INSERT INTO `caeschedeelettorali` VALUES ('prova@prova.com',1),('prova@prova.com',2),('prova@prova.com',3),('prova@prova.com',4);
/*!40000 ALTER TABLE `caeschedeelettorali` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidati`
--

DROP TABLE IF EXISTS `candidati`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidati` (
  `candidato` varchar(16) NOT NULL,
  `partito` varchar(45) NOT NULL,
  PRIMARY KEY (`candidato`,`partito`),
  KEY `partito_idx` (`partito`),
  CONSTRAINT `candidato` FOREIGN KEY (`candidato`) REFERENCES `candidato` (`codicefiscale`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `partito` FOREIGN KEY (`partito`) REFERENCES `partito` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidati`
--

LOCK TABLES `candidati` WRITE;
/*!40000 ALTER TABLE `candidati` DISABLE KEYS */;
INSERT INTO `candidati` VALUES ('AAAAAA00A00A000A','Partito1');
/*!40000 ALTER TABLE `candidati` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidato`
--

DROP TABLE IF EXISTS `candidato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidato` (
  `codicefiscale` varchar(16) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  PRIMARY KEY (`codicefiscale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidato`
--

LOCK TABLES `candidato` WRITE;
/*!40000 ALTER TABLE `candidato` DISABLE KEYS */;
INSERT INTO `candidato` VALUES ('AAAAAA00A00A000A','Candidato','Uno'),('BBBBBB11B11B111B','Candidato','Due');
/*!40000 ALTER TABLE `candidato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `elettore`
--

DROP TABLE IF EXISTS `elettore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `elettore` (
  `email` varchar(45) NOT NULL,
  `codicefiscale` varchar(16) NOT NULL,
  `luogoresidenza` varchar(45) NOT NULL,
  PRIMARY KEY (`email`),
  UNIQUE KEY `codicefiscale_UNIQUE` (`codicefiscale`),
  CONSTRAINT `emailElettore` FOREIGN KEY (`email`) REFERENCES `utente` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `elettore`
--

LOCK TABLES `elettore` WRITE;
/*!40000 ALTER TABLE `elettore` DISABLE KEYS */;
INSERT INTO `elettore` VALUES ('prova@prova.com','PRVPRV80A01H501K','Milano'),('test@test.com','TSTTST00A41F205J','Roma');
/*!40000 ALTER TABLE `elettore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `elettorepreferenzeespresse`
--

DROP TABLE IF EXISTS `elettorepreferenzeespresse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `elettorepreferenzeespresse` (
  `elettore` varchar(45) NOT NULL,
  `schedaelettorale` int NOT NULL,
  PRIMARY KEY (`elettore`,`schedaelettorale`),
  KEY `informazioneSchedaElettore_idx` (`schedaelettorale`),
  CONSTRAINT `elettoreInformazioneScheda` FOREIGN KEY (`elettore`) REFERENCES `elettore` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `schedaelettoraleElettore` FOREIGN KEY (`schedaelettorale`) REFERENCES `schedaelettorale` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `elettorepreferenzeespresse`
--

LOCK TABLES `elettorepreferenzeespresse` WRITE;
/*!40000 ALTER TABLE `elettorepreferenzeespresse` DISABLE KEYS */;
/*!40000 ALTER TABLE `elettorepreferenzeespresse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `elettorisessione`
--

DROP TABLE IF EXISTS `elettorisessione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `elettorisessione` (
  `elettore` varchar(45) NOT NULL,
  `sessione` int NOT NULL,
  PRIMARY KEY (`elettore`,`sessione`),
  KEY `sessione_idx` (`sessione`),
  CONSTRAINT `elettoreSessione` FOREIGN KEY (`elettore`) REFERENCES `elettore` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `elettorisessione`
--

LOCK TABLES `elettorisessione` WRITE;
/*!40000 ALTER TABLE `elettorisessione` DISABLE KEYS */;
INSERT INTO `elettorisessione` VALUES ('test@test.com',1),('prova@prova.com',2);
/*!40000 ALTER TABLE `elettorisessione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informazionescheda`
--

DROP TABLE IF EXISTS `informazionescheda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `informazionescheda` (
  `id` int NOT NULL AUTO_INCREMENT,
  `schedaelettorale` int NOT NULL,
  `quesito` int DEFAULT NULL,
  `partito` varchar(45) DEFAULT NULL,
  `candidato` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `schedaelettorale_idx` (`schedaelettorale`),
  KEY `quesitoSchedaelettorale_idx` (`quesito`),
  KEY `partitoSchedaElettorale_idx` (`partito`),
  KEY `candidatoSchedaElettorale_idx` (`candidato`),
  CONSTRAINT `schedaelettoraleInformazione` FOREIGN KEY (`schedaelettorale`) REFERENCES `schedaelettorale` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `informazionescheda`
--

LOCK TABLES `informazionescheda` WRITE;
/*!40000 ALTER TABLE `informazionescheda` DISABLE KEYS */;
INSERT INTO `informazionescheda` VALUES (1,1,NULL,NULL,'BBBBBB11B11B111B'),(2,1,NULL,NULL,'AAAAAA00A00A000A'),(3,2,NULL,'Partito1',NULL),(4,2,NULL,NULL,'AAAAAA00A00A000A'),(5,3,NULL,'Partito1',NULL),(6,3,NULL,NULL,'AAAAAA00A00A000A'),(7,4,1,NULL,NULL);
/*!40000 ALTER TABLE `informazionescheda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partito`
--

DROP TABLE IF EXISTS `partito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partito` (
  `nome` varchar(45) NOT NULL,
  `capopartito` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`nome`),
  KEY `capopartito_idx` (`capopartito`),
  CONSTRAINT `capopartito` FOREIGN KEY (`capopartito`) REFERENCES `candidato` (`codicefiscale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partito`
--

LOCK TABLES `partito` WRITE;
/*!40000 ALTER TABLE `partito` DISABLE KEYS */;
INSERT INTO `partito` VALUES ('Partito1','AAAAAA00A00A000A');
/*!40000 ALTER TABLE `partito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preferenza`
--

DROP TABLE IF EXISTS `preferenza`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `preferenza` (
  `voce` int NOT NULL,
  `preferenza` int NOT NULL,
  KEY `voce_idx` (`voce`),
  CONSTRAINT `voce` FOREIGN KEY (`voce`) REFERENCES `informazionescheda` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preferenza`
--

LOCK TABLES `preferenza` WRITE;
/*!40000 ALTER TABLE `preferenza` DISABLE KEYS */;
/*!40000 ALTER TABLE `preferenza` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quesito`
--

DROP TABLE IF EXISTS `quesito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quesito` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quesito` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quesito`
--

LOCK TABLES `quesito` WRITE;
/*!40000 ALTER TABLE `quesito` DISABLE KEYS */;
INSERT INTO `quesito` VALUES (1,'Referendum');
/*!40000 ALTER TABLE `quesito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedaelettorale`
--

DROP TABLE IF EXISTS `schedaelettorale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedaelettorale` (
  `id` int NOT NULL AUTO_INCREMENT,
  `limiteEtà` int NOT NULL,
  `modVoto` varchar(45) NOT NULL,
  `modCalcoloVincitore` varchar(45) NOT NULL,
  `descrizione` text NOT NULL,
  `quorum` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedaelettorale`
--

LOCK TABLES `schedaelettorale` WRITE;
/*!40000 ALTER TABLE `schedaelettorale` DISABLE KEYS */;
INSERT INTO `schedaelettorale` VALUES (1,18,'ORDINALE','MAGGIORANZAASS','Consultazione Ordinale',0),(2,25,'CATEGORICO','MAGGIORANZA','Consultazione Categorico',0),(3,18,'CATEGORICOCONPREF','MAGGIORANZA','Consultazione Categorico con preferenze',0),(4,18,'REFERENDUM','MAGGIORANZA','Consultazione Referendum',50);
/*!40000 ALTER TABLE `schedaelettorale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedeelettoralisessione`
--

DROP TABLE IF EXISTS `schedeelettoralisessione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedeelettoralisessione` (
  `schedaelettorale` int NOT NULL,
  `sessione` int NOT NULL,
  PRIMARY KEY (`schedaelettorale`,`sessione`),
  KEY `sessione_idx` (`sessione`),
  CONSTRAINT `schedaelettorale` FOREIGN KEY (`schedaelettorale`) REFERENCES `schedaelettorale` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sessione` FOREIGN KEY (`sessione`) REFERENCES `sessione` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedeelettoralisessione`
--

LOCK TABLES `schedeelettoralisessione` WRITE;
/*!40000 ALTER TABLE `schedeelettoralisessione` DISABLE KEYS */;
INSERT INTO `schedeelettoralisessione` VALUES (1,1),(3,1),(1,2),(2,2),(4,2);
/*!40000 ALTER TABLE `schedeelettoralisessione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessione`
--

DROP TABLE IF EXISTS `sessione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessione` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descrizione` text NOT NULL,
  `inizio` date NOT NULL,
  `fine` date NOT NULL,
  `luogo` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessione`
--

LOCK TABLES `sessione` WRITE;
/*!40000 ALTER TABLE `sessione` DISABLE KEYS */;
INSERT INTO `sessione` VALUES (1,'Sessione 1','2022-09-21','2022-09-30','Roma'),(2,'Sessione 2','2022-09-21','2022-12-31','Milano');
/*!40000 ALTER TABLE `sessione` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `consultazione_BEFORE_INSERT` BEFORE INSERT ON `sessione` FOR EACH ROW BEGIN
IF(NEW.inizio > NEW.fine) THEN
 signal sqlstate '45000' set message_text = 'discount_start must be before discount_end';
 END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES ('prova@prova.com','6279886fde090b3038f267098bcca771a6efa946'),('test@test.com','a94a8fe5ccb19ba61c4c0873d391e987982fbbd3');
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `utente_password_BEFORE_INSERT` BEFORE INSERT ON `utente` FOR EACH ROW BEGIN
SET NEW.password=sha1(NEW.password);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `utente_password_BEFORE_UPDATE` BEFORE UPDATE ON `utente` FOR EACH ROW BEGIN
	IF !(NEW.password <=> OLD.password) THEN
		SET NEW.password=sha1(NEW.password);
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-22 13:02:54
