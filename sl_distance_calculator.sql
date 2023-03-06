-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 06, 2023 at 03:21 AM
-- Server version: 5.5.41
-- PHP Version: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sl_distance_calculator`
--
CREATE DATABASE IF NOT EXISTS `sl_distance_calculator` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `sl_distance_calculator`;

-- --------------------------------------------------------

--
-- Table structure for table `edge`
--

DROP TABLE IF EXISTS `edge`;
CREATE TABLE IF NOT EXISTS `edge` (
  `e_id` int(5) NOT NULL AUTO_INCREMENT,
  `e_source` varchar(50) NOT NULL,
  `e_destination` varchar(50) NOT NULL,
  `e_distance` int(5) NOT NULL,
  PRIMARY KEY (`e_id`),
  UNIQUE KEY `e_source` (`e_source`,`e_destination`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `edge`
--

INSERT INTO `edge` (`e_id`, `e_source`, `e_destination`, `e_distance`) VALUES
(1, 'Colombo', 'Kandy', 150),
(2, 'Colombo', 'Galle', 200),
(3, 'Colombo', 'Horana', 40),
(4, 'Colombo', 'Panadura', 100),
(5, 'Horana', 'Panadura', 40),
(6, 'Kandy', 'Galle', 200),
(7, 'Panadura', 'Galle', 50),
(8, 'Panadura', 'Ragama', 60);

-- --------------------------------------------------------

--
-- Table structure for table `vertex`
--

DROP TABLE IF EXISTS `vertex`;
CREATE TABLE IF NOT EXISTS `vertex` (
  `v_id` int(5) NOT NULL AUTO_INCREMENT,
  `v_name` varchar(50) NOT NULL,
  PRIMARY KEY (`v_id`),
  UNIQUE KEY `v_name` (`v_name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vertex`
--

INSERT INTO `vertex` (`v_id`, `v_name`) VALUES
(1, 'Colombo'),
(4, 'Galle'),
(6, 'Horana'),
(3, 'Jaffna'),
(2, 'Kandy'),
(7, 'Panadura'),
(10, 'Ragama'),
(5, 'Trincomalee');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
