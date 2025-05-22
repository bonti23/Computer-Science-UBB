USE CabinetStomatologic;
GO 

------------------------ Dirty Reads ------------------------
-- 2. READ UNCOMMITTED (poate citi modificările rollback-uite)
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
BEGIN TRANSACTION;
SELECT * FROM Medici;
WAITFOR DELAY '00:00:15';
SELECT * FROM Medici;
COMMIT TRANSACTION;
GO

-- Soluția: READ COMMITTED
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
BEGIN TRANSACTION;
SELECT * FROM Medici;
WAITFOR DELAY '00:00:15';
SELECT * FROM Medici;
COMMIT TRANSACTION;
GO

-------------------- Non-Repeatable Reads --------------------
-- 1. Valori citite diferit în aceeași tranzacție
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
BEGIN TRANSACTION;
SELECT * FROM Sectii;
WAITFOR DELAY '00:00:06';
SELECT * FROM Sectii;
COMMIT;
GO

-- Soluția: REPEATABLE READ
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
BEGIN TRANSACTION;
SELECT * FROM Sectii;
WAITFOR DELAY '00:00:06';
SELECT * FROM Sectii;
COMMIT;
GO

-------------------- Phantom Reads --------------------------
-- 1. Phantom Read demonstrat pe Pacienti
SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
BEGIN TRANSACTION;
SELECT * FROM Pacienti WHERE IDPacient BETWEEN 1 AND 100;
WAITFOR DELAY '00:00:07';
SELECT * FROM Pacienti WHERE IDPacient BETWEEN 1 AND 100;
COMMIT;
GO

-- Soluția: SERIALIZABLE
SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
BEGIN TRANSACTION;
SELECT * FROM Pacienti WHERE IDPacient BETWEEN 1 AND 100;
WAITFOR DELAY '00:00:07';
SELECT * FROM Pacienti WHERE IDPacient BETWEEN 1 AND 100;
COMMIT;
INSERT INTO IstoricTranzactii(actiune, data_executiei, mesaj)
VALUES('Phantom Read', CURRENT_TIMESTAMP, 'Protejat cu SERIALIZABLE');
GO

------------------------ Deadlock ----------------------------
-- 1. Primul fir de execuție
SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
BEGIN TRANSACTION;
UPDATE Medici SET Telefon = '1111' WHERE Nume = 'Popescu';
WAITFOR DELAY '00:00:15';
UPDATE Sectii SET NumarMedici = NumarMedici + 1 WHERE Denumire = 'Chirurgie';
COMMIT;
GO

-- 2. Al doilea fir: provoacă deadlock
SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
BEGIN TRANSACTION;
UPDATE Sectii SET NumarMedici = NumarMedici + 1 WHERE Denumire = 'Chirurgie';
WAITFOR DELAY '00:00:15';
UPDATE Medici SET Telefon = '2222' WHERE Nume = 'Popescu';
COMMIT;
GO
