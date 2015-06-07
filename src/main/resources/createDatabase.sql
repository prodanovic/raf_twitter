CREATE DATABASE IF NOT EXISTS  twitter;
USE twitter;

CREATE TABLE IF NOT EXISTS  `user` (
  `username` VARCHAR(100) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=INNODB DEFAULT CHARSET=latin1 ;

CREATE TABLE IF NOT EXISTS  `tweet` (
  `id` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `createdAt` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1  ;


CREATE TABLE IF NOT EXISTS  `followers` (
  `followed` varchar(100) NOT NULL,
  `follower` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1   ;


