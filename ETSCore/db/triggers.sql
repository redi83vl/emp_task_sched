
DELIMITER //

DROP TRIGGER `ETS`.`NewTaskNotification`//
CREATE TRIGGER `ETS`.`NewTaskNotification` AFTER INSERT ON `ETS`.`Task` FOR EACH ROW
BEGIN
    INSERT INTO `ETS`.`Notification` (`content`, `person`) VALUES ( CONCAT('Detyre e re: ', CONCAT(NEW.identifier, CONCAT(" - ", NEW.title))), NEW.`executor`);
END //

DELIMITER ;
