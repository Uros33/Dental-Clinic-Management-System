-- INIT
SET NAMES utf8;
SET SQL_MODE='';

CREATE DATABASE IF NOT EXISTS `stomatologija` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `stomatologija`;

-- Drop redosled zbog FK veza
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `StavkaTermina`;
DROP TABLE IF EXISTS `Termin`;
DROP TABLE IF EXISTS `StomatologSertifikat`;
DROP TABLE IF EXISTS `Stomatolog`;
DROP TABLE IF EXISTS `Klijent`;
DROP TABLE IF EXISTS `TipKlijenta`;
DROP TABLE IF EXISTS `Usluga`;
DROP TABLE IF EXISTS `Sertifikat`;
SET FOREIGN_KEY_CHECKS = 1;

-- STOMATOLOG
CREATE TABLE `Stomatolog` (
  `StomatologID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(50) NOT NULL,
  `Prezime` VARCHAR(50) NOT NULL,
  `Username` VARCHAR(50) NOT NULL,
  `Password` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`StomatologID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

INSERT INTO `Stomatolog` (StomatologID, Ime, Prezime, Username, PASSWORD) VALUES
(1,'Uros','Djokic','uros','uros'),
(2,'Tamara','Arsić','tarsic','tamara'),
(3,'Vladimir','Rakić','vrakic','vladimir');

-- SERTIFIKAT
CREATE TABLE `Sertifikat` (
  `SertifikatID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(80) NOT NULL,
  PRIMARY KEY (`SertifikatID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

INSERT INTO `Sertifikat` (SertifikatID, Naziv) VALUES
(1,'Endodoncija – napredni nivo'),
(2,'Estetska stomatologija'),
(3,'Oralna hirurgija'),
(4,'Profilaksa i parodontologija');

-- STOMATOLOG-SERTIFIKAT
CREATE TABLE `StomatologSertifikat` (
  `StomatologID` INT UNSIGNED NOT NULL,
  `SertifikatID` INT UNSIGNED NOT NULL,
  `DatumIzdavanja` DATE NOT NULL,
  PRIMARY KEY (`StomatologID`,`SertifikatID`,`DatumIzdavanja`),
  CONSTRAINT `fk_zs_stomatolog` FOREIGN KEY (`StomatologID`) REFERENCES `Stomatolog`(`StomatologID`),
  CONSTRAINT `fk_zs_sertifikat` FOREIGN KEY (`SertifikatID`) REFERENCES `Sertifikat`(`SertifikatID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

INSERT INTO `StomatologSertifikat` (StomatologID, SertifikatID, DatumIzdavanja) VALUES
(1,1,'2021-06-10'),
(1,2,'2023-04-05'),
(2,4,'2022-11-18'),
(3,3,'2020-09-25');

-- TIP KLIJENTA
CREATE TABLE `TipKlijenta` (
  `TipKlijentaID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`TipKlijentaID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

INSERT INTO `TipKlijenta` (TipKlijentaID, Naziv) VALUES
(1,'Standardni'),
(2,'Premium'),
(3,'VIP');

-- KLIJENT
CREATE TABLE `Klijent` (
  `KlijentID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(50) NOT NULL,
  `Prezime` VARCHAR(50) NOT NULL,
  `Email` VARCHAR(100) NOT NULL,
  `Telefon` VARCHAR(40) NOT NULL,
  `TipKlijentaID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`KlijentID`),
  CONSTRAINT `fk_klijent_tip` FOREIGN KEY (`TipKlijentaID`) REFERENCES `TipKlijenta`(`TipKlijentaID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

INSERT INTO `Klijent` (KlijentID, Ime, Prezime, Email, Telefon, TipKlijentaID) VALUES
(1,'Jelena','Stojković','jelena.st@gmail.com','0602921392',1),
(2,'Nikola','Milovanović','nikola.mi@gmail.com','0610029103',2),
(3,'Ana','Jovanović','ana.jov@gmail.com','0632718294',3),
(4,'Marko','Vesić','marko.vesic@gmail.com','0607772223',1);

-- USLUGA
CREATE TABLE `Usluga` (
  `UslugaID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(100) NOT NULL,
  `Opis` VARCHAR(255),
  `Cena` DECIMAL(10,2) NOT NULL,
  `TrajanjeMin` INT NOT NULL,
  PRIMARY KEY (`UslugaID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

INSERT INTO `Usluga` (UslugaID, Naziv, Opis, Cena, TrajanjeMin) VALUES
(1,'Stomatološki pregled','Kompletan pregled i plan terapije',2000.00,20),
(2,'Čišćenje kamenca','Ultrazvučno uklanjanje kamenca i poliranje',4500.00,45),
(3,'Beljenje zuba','Ordinacijska procedura',16000.00,60),
(4,'Plomba kompozit','Estetska kompozitna plomba',7000.00,40),
(5,'Vađenje zuba','Ekstrakcija zuba',9000.00,30),
(6,'Lečenje kanala','Endodontsko lečenje jednog kanala',12000.00,70),
(7,'Fluorizacija','Preventivna zaštita gleđi',2500.00,15),
(8,'Keramička plomba','Inlej/Onlej',22000.00,90),
(9,'Faseta','Keramička faseta',28000.00,120),
(10,'Hirurška intervencija','Manji oralno-hirurški zahvat',15000.00,50);

-- TERMIN
CREATE TABLE `Termin` (
  `TerminID` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `DatumVremePocetka` DATETIME NOT NULL,
  `IznosBezPopusta` DECIMAL(10,2) NOT NULL,
  `Popust` DECIMAL(5,2) NOT NULL,   -- procentualno (npr. 10.00 = 10%)
  `KonacanIznos` DECIMAL(10,2) NOT NULL,
  `StomatologID` INT UNSIGNED NOT NULL,
  `KlijentID` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`TerminID`),
  CONSTRAINT `fk_termin_stomatolog` FOREIGN KEY (`StomatologID`) REFERENCES `Stomatolog`(`StomatologID`),
  CONSTRAINT `fk_termin_klijent` FOREIGN KEY (`KlijentID`) REFERENCES `Klijent`(`KlijentID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- STAVKA TERMINA
CREATE TABLE `StavkaTermina` (
  `TerminID` INT UNSIGNED NOT NULL,
  `Rb` INT NOT NULL,
  `UslugaID` INT UNSIGNED NOT NULL,
  `Napomena` VARCHAR(200),
  `Cena` DECIMAL(10,2) NOT NULL,
  `Iznos` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`TerminID`,`Rb`),
  CONSTRAINT `fk_st_termin` FOREIGN KEY (`TerminID`) REFERENCES `Termin`(`TerminID`) ON DELETE CASCADE,
  CONSTRAINT `fk_st_usluga` FOREIGN KEY (`UslugaID`) REFERENCES `Usluga`(`UslugaID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- ===============================
-- PODACI O TERMINIMA (ispravljeno)
-- Pravilo popusta: Standardni 0%, Premium 10%, VIP 20%
-- IznosBezPopusta = suma(Cena stavki); KonacanIznos = IznosBezPopusta * (1 - Popust/100)
-- Na stavkama: Iznos = Cena (bez popusta po stavci)
-- ===============================

-- Termin 1 – Jelena (Standardni = 0%)
INSERT INTO `Termin` (TerminID, DatumVremePocetka, IznosBezPopusta, Popust, KonacanIznos, StomatologID, KlijentID) VALUES
(1,'2025-10-20 09:00:00',2000.00,0.00,2000.00,1,1);

INSERT INTO `StavkaTermina` (TerminID, Rb, UslugaID, Napomena, Cena, Iznos) VALUES
(1,1,1,'Prvi pregled',2000.00,2000.00);

-- Termin 2 – Nikola (Premium = 10%)
INSERT INTO `Termin` (TerminID, DatumVremePocetka, IznosBezPopusta, Popust, KonacanIznos, StomatologID, KlijentID) VALUES
(2,'2025-10-20 11:30:00',4500.00,10.00,4050.00,2,2);

INSERT INTO `StavkaTermina` (TerminID, Rb, UslugaID, Napomena, Cena, Iznos) VALUES
(2,1,2,'Čišćenje kamenca',4500.00,4500.00);

-- Termin 3 – Marko (Standardni = 0%)
INSERT INTO `Termin` (TerminID, DatumVremePocetka, IznosBezPopusta, Popust, KonacanIznos, StomatologID, KlijentID) VALUES
(3,'2025-10-21 15:00:00',7000.00,0.00,7000.00,1,4);

INSERT INTO `StavkaTermina` (TerminID, Rb, UslugaID, Napomena, Cena, Iznos) VALUES
(3,1,4,'Plomba',7000.00,7000.00);

-- Termin 4 – Ana (VIP = 20%)
-- Stavke: 4,500 + 7,000 + 2,500 = 14,000 -> 20% = 11,200
INSERT INTO `Termin` (TerminID, DatumVremePocetka, IznosBezPopusta, Popust, KonacanIznos, StomatologID, KlijentID) VALUES
(4,'2025-10-22 10:15:00',14000.00,20.00,11200.00,2,3);

INSERT INTO `StavkaTermina` (TerminID, Rb, UslugaID, Napomena, Cena, Iznos) VALUES
(4,1,2,'Čišćenje kamenca',4500.00,4500.00),
(4,2,4,'Plomba',7000.00,7000.00),
(4,3,7,'Fluorizacija',2500.00,2500.00);

-- Termin 5 – Nikola (Premium = 10%)
-- Stavke: 12,000 + 7,000 + 15,000 = 34,000 -> 10% = 30,600
INSERT INTO `Termin` (TerminID, DatumVremePocetka, IznosBezPopusta, Popust, KonacanIznos, StomatologID, KlijentID) VALUES
(5,'2025-10-23 13:00:00',34000.00,10.00,30600.00,1,2);

INSERT INTO `StavkaTermina` (TerminID, Rb, UslugaID, Napomena, Cena, Iznos) VALUES
(5,1,6,'Lečenje kanala',12000.00,12000.00),
(5,2,4,'Plomba posle lečenja',7000.00,7000.00),
(5,3,10,'Manja hirurška korekcija',15000.00,15000.00);

-- Termin 6 – Jelena (Standardni = 0%)
-- Stavke: 16,000 + 2,000 = 18,000 -> 0% = 18,000
INSERT INTO `Termin` (TerminID, DatumVremePocetka, IznosBezPopusta, Popust, KonacanIznos, StomatologID, KlijentID) VALUES
(6,'2025-10-24 17:30:00',18000.00,0.00,18000.00,3,1);

INSERT INTO `StavkaTermina` (TerminID, Rb, UslugaID, Napomena, Cena, Iznos) VALUES
(6,1,3,'Beljenje – ordinacijski tretman',16000.00,16000.00),
(6,2,1,'Kontrolni pregled',2000.00,2000.00);
