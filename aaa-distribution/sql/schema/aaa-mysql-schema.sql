
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
-- Table structure for table `aaa_aircraft`
--

DROP TABLE IF EXISTS `aaa_aircraft`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aaa_aircraft` (
  `tailSign` varchar(255) COLLATE utf8_bin NOT NULL,
  `aaa_aircraft_type_name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`tailSign`),
  KEY `FK_d5jdkc5m3b5r1558cldbpjt7c` (`aaa_aircraft_type_name`),
  CONSTRAINT `FK_d5jdkc5m3b5r1558cldbpjt7c` FOREIGN KEY (`aaa_aircraft_type_name`) REFERENCES `aaa_aircraft_type` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `aaa_aircraft_type`
--

DROP TABLE IF EXISTS `aaa_aircraft_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aaa_aircraft_type` (
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `aaa_license`
--

DROP TABLE IF EXISTS `aaa_license`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aaa_license` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  `expirationDate` datetime DEFAULT NULL,
  `aaa_aircraft_type_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `aaa_pilot_username` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3q4btp3h3rtwugcw7mdqxm96t` (`aaa_aircraft_type_name`),
  KEY `FK_jlbtd58m54mgre37exbwp58ai` (`aaa_pilot_username`),
  CONSTRAINT `FK_jlbtd58m54mgre37exbwp58ai` FOREIGN KEY (`aaa_pilot_username`) REFERENCES `aaa_pilot` (`username`),
  CONSTRAINT `FK_3q4btp3h3rtwugcw7mdqxm96t` FOREIGN KEY (`aaa_aircraft_type_name`) REFERENCES `aaa_aircraft_type` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `aaa_pilot`
--

DROP TABLE IF EXISTS `aaa_pilot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aaa_pilot` (
  `username` varchar(255) COLLATE utf8_bin NOT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `aaa_reservation`
--

DROP TABLE IF EXISTS `aaa_reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aaa_reservation` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  `endDate` datetime DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `aaa_reservation_tail_sign` varchar(255) COLLATE utf8_bin NOT NULL,
  `aaa_reservation_state_name` varchar(255) COLLATE utf8_bin NOT NULL,
  `aaa_pilot_username` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_64j1w6vh1g2dlxs0tohsxn0el` (`aaa_reservation_tail_sign`),
  KEY `FK_bnone2c67efwe5e1a6csnm2w` (`aaa_reservation_state_name`),
  KEY `FK_hf1sm0bc9sofyyxb32pn5fhs8` (`aaa_pilot_username`),
  CONSTRAINT `FK_hf1sm0bc9sofyyxb32pn5fhs8` FOREIGN KEY (`aaa_pilot_username`) REFERENCES `aaa_pilot` (`username`),
  CONSTRAINT `FK_64j1w6vh1g2dlxs0tohsxn0el` FOREIGN KEY (`aaa_reservation_tail_sign`) REFERENCES `aaa_aircraft` (`tailSign`),
  CONSTRAINT `FK_bnone2c67efwe5e1a6csnm2w` FOREIGN KEY (`aaa_reservation_state_name`) REFERENCES `aaa_reservation_state` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `aaa_reservation_state`
--

DROP TABLE IF EXISTS `aaa_reservation_state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aaa_reservation_state` (
  `name` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
