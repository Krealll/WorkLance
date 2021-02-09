-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema workdatabase
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema workdatabase
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `workdatabase` DEFAULT CHARACTER SET utf8 ;
USE `workdatabase` ;

-- -----------------------------------------------------
-- Table `workdatabase`.`development_teams`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workdatabase`.`development_teams` (
  `id_team` INT(11) NOT NULL AUTO_INCREMENT,
  `team_name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id_team`),
  UNIQUE INDEX `id_UNIQUE` (`id_team` ASC),
  UNIQUE INDEX `name_UNIQUE` (`team_name` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `workdatabase`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workdatabase`.`users` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(32) NOT NULL,
  `login` VARCHAR(64) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  `specialization` VARCHAR(80) NOT NULL,
  `description` VARCHAR(255) NULL,
  `show_email` TINYINT NULL,
  `fk_team_id` INT(11) NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `fk_users_development_teams1_idx` (`fk_team_id` ASC),
  CONSTRAINT `fk_users_development_teams1`
    FOREIGN KEY (`fk_team_id`)
    REFERENCES `workdatabase`.`development_teams` (`id_team`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `workdatabase`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workdatabase`.`orders` (
  `id_order` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `budget` DOUBLE NOT NULL,
  `date_of_creation` DATE NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `users_user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id_order`),
  UNIQUE INDEX `id_order_UNIQUE` (`id_order` ASC),
  INDEX `fk_orders_users_idx` (`users_user_id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  CONSTRAINT `fk_orders_users`
    FOREIGN KEY (`users_user_id`)
    REFERENCES `workdatabase`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `workdatabase`.`user_auth`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workdatabase`.`user_auth` (
  `id_user_auth` INT(11) NOT NULL AUTO_INCREMENT,
  `selector` VARCHAR(12) NOT NULL,
  `validator` VARCHAR(64) NOT NULL,
  `users_user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id_user_auth`),
  UNIQUE INDEX `id_user_auth_UNIQUE` (`id_user_auth` ASC),
  INDEX `fk_user_auth_users1_idx` (`users_user_id` ASC),
  CONSTRAINT `fk_user_auth_users1`
    FOREIGN KEY (`users_user_id`)
    REFERENCES `workdatabase`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `workdatabase`.`notifications`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `workdatabase`.`notifications` (
  `id_notification` INT NOT NULL AUTO_INCREMENT,
  `development_teams_id_team` INT(11) NOT NULL,
  `users_user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id_notification`),
  INDEX `fk_notifications_development_teams1_idx` (`development_teams_id_team` ASC),
  INDEX `fk_notifications_users1_idx` (`users_user_id` ASC),
  CONSTRAINT `fk_notifications_development_teams1`
    FOREIGN KEY (`development_teams_id_team`)
    REFERENCES `workdatabase`.`development_teams` (`id_team`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_notifications_users1`
    FOREIGN KEY (`users_user_id`)
    REFERENCES `workdatabase`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
