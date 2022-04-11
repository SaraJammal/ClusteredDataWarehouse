DROP TABLE IF EXISTS `deal`;
CREATE TABLE `deal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_currency_iso_code` varchar(128) DEFAULT NULL,
  `to_currency_iso_code` varchar(128) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `deal_amount` double DEFAULT '0',
  `file_name` varchar(256) DEFAULT NULL,
  `reason` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
