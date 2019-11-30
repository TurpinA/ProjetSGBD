-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 30, 2019 at 07:21 PM
-- Server version: 5.7.17
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mydb`
--

-- --------------------------------------------------------

--
-- Table structure for table `annonce`
--

CREATE TABLE `annonce` (
  `idAnnonce` int(11) NOT NULL,
  `Logement` int(11) NOT NULL,
  `Utilisateur` int(11) NOT NULL,
  `Prix` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `annonce`
--

INSERT INTO `annonce` (`idAnnonce`, `Logement`, `Utilisateur`, `Prix`) VALUES
(1, 1, 5, 5),
(2, 3, 9, 2569851);

-- --------------------------------------------------------

--
-- Table structure for table `logement`
--

CREATE TABLE `logement` (
  `idLogement` int(11) NOT NULL,
  `Ville` varchar(45) DEFAULT NULL,
  `Taille` int(11) DEFAULT NULL,
  `Type` varchar(45) DEFAULT NULL,
  `AnneeDeCreation` date DEFAULT NULL,
  `Adresse` varchar(45) DEFAULT NULL,
  `Photo` varchar(45) DEFAULT NULL,
  `Description` varchar(245) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `logement`
--

INSERT INTO `logement` (`idLogement`, `Ville`, `Taille`, `Type`, `AnneeDeCreation`, `Adresse`, `Photo`, `Description`) VALUES
(1, 'Tours', 25, 'Appartement', '2019-11-22', '7 rue de la sainte vierge', 'PHOTO', 'DESCRIPTION'),
(2, 'Tours', 25, 'Maison', '2019-07-18', 'adresse', 'photo', 'description'),
(3, 'Paris', 253, 'Maison', '2019-08-01', 'Adre', 'photo', 'desc');

-- --------------------------------------------------------

--
-- Table structure for table `offre`
--

CREATE TABLE `offre` (
  `idOffre` int(11) NOT NULL,
  `Annonce` int(11) NOT NULL,
  `Acheteur` int(11) NOT NULL,
  `DateCreation` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `idUtilisateur` int(11) NOT NULL,
  `Nom` varchar(45) NOT NULL,
  `Prénom` varchar(45) NOT NULL,
  `NumeroTelephone` varchar(256) NOT NULL,
  `MotDePasse` varchar(45) DEFAULT NULL,
  `Ville` varchar(45) DEFAULT NULL,
  `mail` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `utilisateur`
--

INSERT INTO `utilisateur` (`idUtilisateur`, `Nom`, `Prénom`, `NumeroTelephone`, `MotDePasse`, `Ville`, `mail`) VALUES
(11, 'Turpin', 'Alexandre', '06292155150', '-926435972', 'Tours', 'alexandre.turpin@outlook.com'),
(12, 'Turpin', 'Julien', '0695874415', '3273686', 'Paris', 'julien.turpin@outlook.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `annonce`
--
ALTER TABLE `annonce`
  ADD PRIMARY KEY (`idAnnonce`),
  ADD UNIQUE KEY `idAnnonce` (`idAnnonce`),
  ADD KEY `fk_Annonce_Logement` (`Logement`),
  ADD KEY `fk_Annonce_Utilisateur1` (`Utilisateur`);

--
-- Indexes for table `logement`
--
ALTER TABLE `logement`
  ADD PRIMARY KEY (`idLogement`),
  ADD UNIQUE KEY `idLogement` (`idLogement`);

--
-- Indexes for table `offre`
--
ALTER TABLE `offre`
  ADD PRIMARY KEY (`idOffre`),
  ADD UNIQUE KEY `idOffre` (`idOffre`),
  ADD KEY `fk_Offre_Annonce1` (`Annonce`),
  ADD KEY `fk_Offre_Utilisateur2` (`Acheteur`);

--
-- Indexes for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`idUtilisateur`),
  ADD UNIQUE KEY `idUtilisateur` (`idUtilisateur`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `annonce`
--
ALTER TABLE `annonce`
  MODIFY `idAnnonce` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `logement`
--
ALTER TABLE `logement`
  MODIFY `idLogement` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `offre`
--
ALTER TABLE `offre`
  MODIFY `idOffre` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `idUtilisateur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `annonce`
--
ALTER TABLE `annonce`
  ADD CONSTRAINT `fk_Annonce_Logement` FOREIGN KEY (`Logement`) REFERENCES `logement` (`idLogement`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_Annonce_Utilisateur1` FOREIGN KEY (`Utilisateur`) REFERENCES `utilisateur` (`idUtilisateur`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `offre`
--
ALTER TABLE `offre`
  ADD CONSTRAINT `fk_Offre_Annonce1` FOREIGN KEY (`Annonce`) REFERENCES `annonce` (`idAnnonce`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_Offre_Utilisateur2` FOREIGN KEY (`Acheteur`) REFERENCES `utilisateur` (`idUtilisateur`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
