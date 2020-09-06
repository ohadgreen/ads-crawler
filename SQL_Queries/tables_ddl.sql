-- MASTER_ADS
CREATE TABLE `master_ads` (
  `exchange_domain` varchar(200) NOT NULL,
  `seller_account` varchar(100) NOT NULL,
  `payment_type` varchar(20) NOT NULL,
  `tag_id` varchar(45) NULL,
  PRIMARY KEY (`exchange_domain`,`seller_account`,`payment_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

-- SITES
CREATE TABLE `sites` (
  `site_id` int NOT NULL AUTO_INCREMENT,
  `publisher_id` int DEFAULT NULL,
  `site_index` int DEFAULT NULL,
  `site_url` varchar(100) DEFAULT NULL,
  `crawl_error` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12650 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

-- SITES_ADS
CREATE TABLE `sites_ads` (
  `site_id` int NOT NULL,
  `exchange_domain` varchar(200) NOT NULL,
  `seller_account` varchar(100) NOT NULL,
  `payment_type` varchar(20) NOT NULL,
  `tag_id` varchar(45) NOT NULL,
  PRIMARY KEY (`site_id`,`exchange_domain`,`seller_account`,`payment_type`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci