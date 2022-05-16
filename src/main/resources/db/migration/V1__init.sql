
DROP TABLE IF EXISTS `admin`;
CREATE table `admin`
(
    admin_id  int(11)      not null auto_increment,
    full_name varchar(50)  not null,
    username  varchar(150) not null,
    password  varchar(150) not null,
    role      varchar(20)  not null,
    enable    bit          not null,
    primary key (admin_id),
    unique key `admin_username_unique` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category`
(
    `category_id` int(11)                                NOT NULL AUTO_INCREMENT,
    `name`        varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`category_id`),
    UNIQUE KEY `UK_46ccwnsi9409t36lurvtyljak` (`name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post`
(
    `post_id`      int(11)  NOT NULL AUTO_INCREMENT,
    `content`      longtext COLLATE utf8mb4_unicode_ci,
    `created_at`   datetime NOT NULL,
    `description`  varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `image`        varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `is_published` bit(1)   NOT NULL,
    `title`        varchar(70) COLLATE utf8mb4_unicode_ci  DEFAULT NULL,
    `updated_at`   datetime NOT NULL,
    `view_number`  int(11)  NOT NULL,
    `category_id`  int(11)  NOT NULL,
    `admin_id`     int(11)  not null,
    PRIMARY KEY (`post_id`),
    KEY `FKg6l1ydp1pwkmyj166teiuov1b` (`category_id`),
    CONSTRAINT `FKg6l1ydp1pwkmyj166teiuov1b` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
    constraint `admin_post` foreign key (`admin_id`) references `admin` (`admin_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag`
(
    `tag_id` int(11)                                NOT NULL AUTO_INCREMENT,
    `name`   varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`tag_id`),
    UNIQUE KEY `UK_1wdpsed5kna2y38hnbgrnhi5b` (`name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

--
-- Table structure for table `post_tag`
--

DROP TABLE IF EXISTS `post_tag`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_tag`
(
    `post_id` int(11) NOT NULL,
    `tag_id`  int(11) NOT NULL,
    PRIMARY KEY (`post_id`, `tag_id`),
    KEY `FKac1wdchd2pnur3fl225obmlg0` (`tag_id`),
    CONSTRAINT `FKac1wdchd2pnur3fl225obmlg0` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`tag_id`),
    CONSTRAINT `FKc2auetuvsec0k566l0eyvr9cs` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;