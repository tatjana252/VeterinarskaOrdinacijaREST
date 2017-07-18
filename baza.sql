/*
SQLyog Community v12.4.1 (64 bit)
MySQL - 5.7.14 : Database - veterinarskaordinacija
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`veterinarskaordinacija` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `veterinarskaordinacija`;

/*Table structure for table `korisnik` */

DROP TABLE IF EXISTS `korisnik`;

CREATE TABLE `korisnik` (
  `korisnikid` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `pass` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`korisnikid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `korisnik` */

insert  into `korisnik`(`korisnikid`,`pass`) values 
('admin','admin'),
('k1','k1'),
('k2','k2'),
('k3','k3');

/*Table structure for table `ljubimac` */

DROP TABLE IF EXISTS `ljubimac`;

CREATE TABLE `ljubimac` (
  `ljubimacid` int(11) NOT NULL AUTO_INCREMENT,
  `ime` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `sifracipa` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `datumrodjenja` date NOT NULL,
  `vrstazivotinjeid` int(11) NOT NULL,
  `vlasnikid` int(11) NOT NULL,
  `rasa` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pol` enum('M','Z') COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ljubimacid`),
  UNIQUE KEY `uc1` (`ime`,`sifracipa`,`datumrodjenja`,`vrstazivotinjeid`,`rasa`,`pol`),
  KEY `vrstazivotinjeid` (`vrstazivotinjeid`),
  KEY `vlasnikid` (`vlasnikid`),
  CONSTRAINT `ljubimac_ibfk_1` FOREIGN KEY (`vrstazivotinjeid`) REFERENCES `vrstazivotinje` (`vrstazivotinjeid`),
  CONSTRAINT `ljubimac_ibfk_2` FOREIGN KEY (`vlasnikid`) REFERENCES `vlasnik` (`vlasnikid`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `ljubimac` */

insert  into `ljubimac`(`ljubimacid`,`ime`,`sifracipa`,`datumrodjenja`,`vrstazivotinjeid`,`vlasnikid`,`rasa`,`pol`) values 
(1,'Flekica','123546789123456','2016-08-24',1,23,'Turska angora','Z'),
(13,'Švrća','256785235416981','2014-08-01',2,2,'Pekinezer','M'),
(15,'Mališa','','2017-04-23',3,21,'Činčila','M'),
(71,'Kiki','','2010-06-24',5,43,'','M'),
(72,'Žuća','','2015-06-01',2,44,'Jorkširski terijer','Z'),
(73,'Žuća','','2014-09-16',2,45,'Labrador','M');

/*Table structure for table `poseta` */

DROP TABLE IF EXISTS `poseta`;

CREATE TABLE `poseta` (
  `posetaid` int(11) NOT NULL AUTO_INCREMENT,
  `ljubimacid` int(11) NOT NULL,
  `datum` date NOT NULL,
  PRIMARY KEY (`posetaid`),
  KEY `poseta_ibfk_1` (`ljubimacid`),
  CONSTRAINT `poseta_ibfk_1` FOREIGN KEY (`ljubimacid`) REFERENCES `ljubimac` (`ljubimacid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `poseta` */

insert  into `poseta`(`posetaid`,`ljubimacid`,`datum`) values 
(1,1,'2025-03-20'),
(2,13,'2025-06-20'),
(3,13,'2026-03-20'),
(9,1,'2017-05-01'),
(10,13,'2017-05-12'),
(11,1,'2017-05-12'),
(14,13,'2017-05-15'),
(18,15,'2017-05-11'),
(29,13,'2017-05-28'),
(30,13,'2017-06-17'),
(50,15,'2017-06-22'),
(60,1,'2017-06-23'),
(61,1,'2017-06-23'),
(68,72,'2017-06-24'),
(71,73,'2017-06-24');

/*Table structure for table `stavkaposete` */

DROP TABLE IF EXISTS `stavkaposete`;

CREATE TABLE `stavkaposete` (
  `posetaid` int(11) NOT NULL,
  `uslugaid` int(11) NOT NULL,
  `stavkaposeteid` int(11) NOT NULL AUTO_INCREMENT,
  `opis` varchar(1000) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`posetaid`,`uslugaid`,`stavkaposeteid`),
  KEY `uslugaid` (`uslugaid`),
  KEY `stavkaposeteid` (`stavkaposeteid`),
  CONSTRAINT `stavkaposete_ibfk_2` FOREIGN KEY (`uslugaid`) REFERENCES `usluga` (`uslugaid`),
  CONSTRAINT `stavkaposete_ibfk_3` FOREIGN KEY (`posetaid`) REFERENCES `poseta` (`posetaid`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `stavkaposete` */

insert  into `stavkaposete`(`posetaid`,`uslugaid`,`stavkaposeteid`,`opis`) values 
(1,32,1,'Uspesno izvrseno'),
(1,33,2,'Neuspeno izvrseno'),
(1,33,28,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu'),
(1,35,3,'Bla'),
(1,35,4,NULL),
(1,36,26,'tralalala'),
(1,37,25,'asdasd'),
(1,52,27,'lkj'),
(10,37,9,'s'),
(10,39,10,'siv222'),
(11,41,11,NULL),
(11,45,12,NULL),
(14,33,15,'Pas je dovedem zbog uocenih okruglih alopecija. Dijagnostifikovana je mikrosporoza.'),
(14,52,16,NULL),
(18,33,21,NULL),
(18,37,20,'szdfsdf'),
(60,32,79,'Godišnji pregled.'),
(60,52,80,NULL),
(61,32,81,'Godišnji pregled.'),
(61,52,82,NULL),
(68,38,95,'Povreda zadobijena prilikom šišanja.'),
(68,38,97,NULL),
(68,39,96,NULL),
(68,53,98,NULL),
(71,34,102,NULL),
(71,40,103,NULL);

/*Table structure for table `tipusluge` */

DROP TABLE IF EXISTS `tipusluge`;

CREATE TABLE `tipusluge` (
  `tipuslugeid` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`tipuslugeid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `tipusluge` */

insert  into `tipusluge`(`tipuslugeid`,`naziv`) values 
(1,'pregled'),
(2,'opšte intervencije'),
(3,'hirurgija'),
(4,'kozmetika'),
(5,'laboratorijske analize'),
(6,'ostalo');

/*Table structure for table `usluga` */

DROP TABLE IF EXISTS `usluga`;

CREATE TABLE `usluga` (
  `uslugaid` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `cena` double NOT NULL,
  `tipuslugeid` int(11) NOT NULL,
  PRIMARY KEY (`uslugaid`),
  UNIQUE KEY `uc` (`naziv`,`tipuslugeid`),
  KEY `fk_tip` (`tipuslugeid`),
  CONSTRAINT `fk_tip` FOREIGN KEY (`tipuslugeid`) REFERENCES `tipusluge` (`tipuslugeid`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2306 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `usluga` */

insert  into `usluga`(`uslugaid`,`naziv`,`cena`,`tipuslugeid`) values 
(32,'Pregled pasa i mačaka',800,1),
(33,'Dermatološki pregled',1000,1),
(34,'Pregled ostalih kucnih ljubimaca',500,1),
(35,'Dnevna terapija',800,1),
(36,'Fiksiranje malih životinja',400,2),
(37,'Sondiranje pasa i mačaka',1100,2),
(38,'Obrada rane',1000,3),
(39,'Šivenje rana',1500,3),
(40,'Stavljanje zavoja',400,3),
(41,'Skraćivanje kandži',800,4),
(42,'Šišanje pasa - malih',2000,4),
(44,'Šišanje pasa - velikih',3000,4),
(45,'Kupanje pasa i mačaka',1000,4),
(46,'Uzimanje krvi',300,5),
(47,'Holesterol',270,5),
(48,'Urea',185,5),
(49,'Brzi test FIV',2000,5),
(50,'Čipovanje pasa i macaka',2000,6),
(51,'Izdavanje pasoša',1500,6),
(52,'Kućna poseta',1000,6),
(53,'Transport životinje',2000,6),
(55,'Sterilizacija',7000,3),
(2246,'Šišanje pasa - srednjih',2500,4),
(2303,'dodaj',10,5),
(2304,'asd',10,1),
(2305,'asda',20,1);

/*Table structure for table `vlasnik` */

DROP TABLE IF EXISTS `vlasnik`;

CREATE TABLE `vlasnik` (
  `vlasnikid` int(11) NOT NULL AUTO_INCREMENT,
  `jmbg` varchar(13) COLLATE utf8_unicode_ci NOT NULL,
  `ime` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `prezime` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `adresa` varchar(225) COLLATE utf8_unicode_ci NOT NULL,
  `brojtelefona` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`vlasnikid`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `vlasnik` */

insert  into `vlasnik`(`vlasnikid`,`jmbg`,`ime`,`prezime`,`adresa`,`brojtelefona`) values 
(2,'0206994715159','Dušica','Stepić','Cvijićeva 100','063/257223'),
(3,'2029877503251','Nikola','Nikolić','Nikolićeva 181','063365896'),
(4,'1701954756856','Bogoljub','Karić','Ljutice Bogdana 2','011/721523'),
(5,'2504940856896','Alfredo','Paćino','Srpskih vladara 18','068/865888'),
(6,'2005984715310','Nikola','Simić','Beogradska 23','0606811235'),
(7,'1203971751669','Aleksandar','Vučić','Gospodara Vučića 88','066/686689'),
(14,'2502995715204','Tatjana','Stojanović','Vjekoslava Afrića 10','0637395466'),
(18,'1234567891234','a','a','a','0637585644'),
(19,'2502995715204','Tatjana','Stojanović','Vjekoslava Afrića 10','0637395466'),
(20,'1701954756856','Bogoljub','Karić','Ljutice Bogdana 2','011/721523'),
(21,'0206994715159','Dušica','Stepić','Cvijićeva 100','063/257223'),
(22,'0206994715159','Dušica','Stepić','Cvijićeva 100','063/257223'),
(23,'2502995715204','Tatjana','Stojanović','Vjekoslava Afrića 9','0113426262'),
(27,'1203971751669','Aleksandar','Vučić','Gospodara Vučića 1000','066/686689'),
(28,'0000000000000','555','444','333','063255685'),
(29,'1234567891234','a','a','a','0637585644'),
(30,'2502995715204','Tatjana','Stojanović','Vjekoslava Afrića 9','011/3426262'),
(31,'2502995715201','Tat','Sto','Adre','0637395466'),
(41,'0912965850057','Dragan','Stojanović','Bulevar Kralja Aleksandra 222','0654933027'),
(43,'0808978710025','Nikola','Djordjević','Dušana Radovića 20a','065/8569852'),
(44,'2508986715215','Luka','Tomović','Hanibala Lučića 9','061/1855252'),
(45,'0605989715157','Milica','Popović','Đušina 91','063/356653');

/*Table structure for table `vrstazivotinje` */

DROP TABLE IF EXISTS `vrstazivotinje`;

CREATE TABLE `vrstazivotinje` (
  `vrstazivotinjeid` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`vrstazivotinjeid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `vrstazivotinje` */

insert  into `vrstazivotinje`(`vrstazivotinjeid`,`naziv`) values 
(1,'Mačke'),
(2,'Psi'),
(3,'Glodari'),
(4,'Gmizavci'),
(5,'Ptice'),
(6,'Drugo');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
