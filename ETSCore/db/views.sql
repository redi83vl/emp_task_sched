CREATE VIEW `ViewCompletedTask` AS
SELECT identifier, creator, executor, title, description, creationTime, expirationTime
FROM Task t, CompletedTask ct
WHERE t.id = ct.task;

CREATE VIEW `ViewRejectedTask` AS
SELECT identifier, creator, executor, title, description, creationTime, expirationTime
FROM Task t, RejectedTask rt
WHERE t.id = rt.task;
