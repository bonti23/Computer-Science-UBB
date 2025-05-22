-- Script pentru demonstratii tranzactionale (Dirty Reads, Non-Repeatable Reads, Phantom Reads, Deadlock)

USE CabinetStomatologic;
GO

-- Creăm tabel pentru logul tranzacțiilor
CREATE TABLE IstoricTranzactii (
    ID INT PRIMARY KEY IDENTITY(1,1),
    actiune VARCHAR(100),
    data_executiei DATETIME,
    mesaj VARCHAR(255)
);
GO

SELECT * FROM IstoricTranzactii;

------------------------ Dirty Reads ------------------------
-- 1. Tranzacție cu ROLLBACK (nu persistă modificările)
SELECT * FROM Medici;
BEGIN TRANSACTION;
UPDATE Medici SET Telefon = '07000000' WHERE Nume = 'Lin';
WAITFOR DELAY '00:00:10';
ROLLBACK TRANSACTION;

INSERT INTO IstoricTranzactii(actiune, data_executiei, mesaj)
VALUES('Dirty Read Test', CURRENT_TIMESTAMP, 'Rollback finalizat');
GO


-------------------- Non-Repeatable Reads --------------------
-- 2. Tranzacție concurentă care modifică
BEGIN TRANSACTION;
WAITFOR DELAY '00:00:10';
UPDATE Sectii SET NumarMedici = NumarMedici + 1 WHERE Denumire = 'Chirurgie';
COMMIT;
GO

-------------------- Phantom Reads --------------------------

-- 2. Tranzacție concurentă: adaugă pacient
BEGIN TRANSACTION;
WAITFOR DELAY '00:00:10';
INSERT INTO Pacienti (Nume, Prenume, Telefon, Email, Adresa)
VALUES ('Test', 'Phantom', '0123', 't@t.com', 'Strada X');
COMMIT;
GO

------------------------ Deadlock ----------------------------

-- Soluție: prioritate mai mare pentru unul dintre thread-uri
SET DEADLOCK_PRIORITY HIGH;
BEGIN TRANSACTION;
UPDATE Sectii SET NumarMedici = NumarMedici + 1 WHERE Denumire = 'Chirurgie';
WAITFOR DELAY '00:00:10';
UPDATE Medici SET Telefon = '2222' WHERE Nume = 'Popescu';
COMMIT;
GO

------------------- Proceduri pentru Threads (C#) -------------------
GO
CREATE OR ALTER PROCEDURE run_thread1
AS
BEGIN
    BEGIN TRANSACTION;
    UPDATE Medici SET Telefon = '9999' WHERE Nume = 'Popescu';
    WAITFOR DELAY '00:00:10';
    UPDATE Sectii SET NumarMedici = NumarMedici + 1 WHERE Denumire = 'Chirurgie';
    COMMIT;
END;
GO

CREATE OR ALTER PROCEDURE run_thread2
AS
BEGIN
    SET DEADLOCK_PRIORITY HIGH;
    BEGIN TRANSACTION;
    UPDATE Sectii SET NumarMedici = NumarMedici + 1 WHERE Denumire = 'Chirurgie';
    WAITFOR DELAY '00:00:10';
    UPDATE Medici SET Telefon = '8888' WHERE Nume = 'Popescu';
    COMMIT;
END;
GO

-- Verificare finală
SELECT * FROM IstoricTranzactii;
SELECT * FROM Pacienti;
SELECT * FROM Sectii;
SELECT * FROM Medici;
