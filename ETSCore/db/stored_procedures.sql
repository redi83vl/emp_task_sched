DELIMITER //

DROP PROCEDURE getTasksByCreator//
CREATE PROCEDURE getTasksByCreator(IN creatorPnumber CHAR(10))
BEGIN
    DECLARE creatorId INT;
    SELECT id FROM Person p WHERE p.pnumber = creatorPnumber LIMIT 1 INTO creatorId;
    SELECT * FROM Task t WHERE t.creator = creatorId;
END //

DROP PROCEDURE getTasksByExecutor//
CREATE PROCEDURE getTasksByExecutor(IN executorPnumber CHAR(10))
BEGIN
    DECLARE executorId INT;
    SELECT id FROM Person p WHERE p.pnumber = executorPnumber LIMIT 1 INTO executorId;
    SELECT * FROM Task t WHERE t.executor = executorId;
END //

DROP PROCEDURE getTasks//
CREATE PROCEDURE getTasks(IN creatorPnumber CHAR(10), IN executorPnumber CHAR(10))
BEGIN
    DECLARE creatorId INT;
    DECLARE executorId INT;
    
    SELECT id FROM Person p WHERE p.pnumber = creatorPnumber LIMIT 1 INTO creatorId;
    SELECT id FROM Person p WHERE p.pnumber = executorPnumber LIMIT 1 INTO executorId;

    SELECT * FROM Task t WHERE t.creator = creatorId AND t.executor = executorId;
END //

DELIMITER ;
