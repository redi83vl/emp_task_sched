DROP SCHEMA IF EXISTS `ets` ;
CREATE SCHEMA IF NOT EXISTS `ets` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `ets` ;

-- -----------------------------------------------------
-- Table `ets`.`Person`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ets`.`Person` ;

CREATE TABLE IF NOT EXISTS `ets`.`Person`
(
  `id` INT NOT NULL AUTO_INCREMENT,
  `pnumber` VARCHAR(10) NOT NULL,
  `fname` VARCHAR(45) NOT NULL,
  `lname` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `pnumber_UNIQUE` (`pnumber` ASC)
);


-- -----------------------------------------------------
-- Table `ets`.`Task`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ets`.`Task` ;

CREATE TABLE IF NOT EXISTS `ets`.`Task` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `identifier` VARCHAR(10) NOT NULL,
  `creator` INT NOT NULL,
  `executor` INT NOT NULL,
  `title` TINYTEXT NOT NULL,
  `description` TEXT NOT NULL,
  `creationTime` TIMESTAMP NOT NULL,
  `expirationTime` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `identifier_UNIQUE` (`identifier` ASC),
  INDEX `fk_task_creator_idx` (`creator` ASC),
  INDEX `fk_task_executor_idx` (`executor` ASC),
  CONSTRAINT `fk_task_creator` FOREIGN KEY (`creator`) REFERENCES `ets`.`Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_task_executor` FOREIGN KEY (`executor`) REFERENCES `ets`.`Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);


-- -----------------------------------------------------
-- Table `ets`.`RejectedTask`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ets`.`RejectedTask` ;

CREATE TABLE IF NOT EXISTS `ets`.`RejectedTask` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `task` INT NOT NULL,
  `rejectionTime` TIMESTAMP NOT NULL,
  `annotations` TEXT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `task_UNIQUE` (`task` ASC),
  CONSTRAINT `fk_rejetected_task` FOREIGN KEY (`task`) REFERENCES `ets`.`Task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Table `ets`.`CompletedTask`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ets`.`CompletedTask` ;

CREATE TABLE IF NOT EXISTS `ets`.`CompletedTask` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `task` INT NOT NULL,
  `completionTime` TIMESTAMP NOT NULL,
  `annotations` TEXT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `task_UNIQUE` (`task` ASC),
  CONSTRAINT `fk_completed_task` FOREIGN KEY (`task`) REFERENCES `ets`.`Task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `ets`.`Notification` (
  `id` INT NOT NULL,
  `content` TINYTEXT NOT NULL,
  `person` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_notif_person_idx` (`person` ASC),
  CONSTRAINT `fk_notif_person` FOREIGN KEY (`person`) REFERENCES `ets`.`Person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);