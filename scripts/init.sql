CREATE DATABASE IF NOT EXISTS tokyo_api;

USE tokyo_api;

CREATE TABLE IF NOT EXISTS tournaments (
    `id`          INT UNSIGNED AUTO_INCREMENT PRIMARY KEY
  , `modality`    TEXT NOT NULL
  , `location`    TEXT NOT NULL
  , `start_date`  TIMESTAMP NOT NULL
  , `end_date`    TIMESTAMP NOT NULL
  , `country1`    TEXT NOT NULL
  , `country2`    TEXT NOT NULL
  , `stage`       TEXT NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=utf8;
