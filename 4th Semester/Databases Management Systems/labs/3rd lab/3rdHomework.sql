USE CabinetStomatologic
GO

-------------------------
-- FUNCȚII DE VALIDARE --
-------------------------
GO
CREATE OR ALTER FUNCTION validareTratament
(@denumire VARCHAR(50), @pret INT, @durata INT, @idProgramare INT)
RETURNS VARCHAR(100)
AS 
BEGIN
    DECLARE @mesaj VARCHAR(100) = ''

    IF (LEN(@denumire) < 3)
        SET @mesaj += 'Denumirea tratamentului este prea scurtă! '

    IF (@pret < 0)
        SET @mesaj += 'Prețul tratamentului este invalid! '

    IF (@durata <= 0)
        SET @mesaj += 'Durata tratamentului este invalidă! '

    IF (NOT EXISTS (SELECT IDProgramare FROM Programari WHERE IDProgramare = @idProgramare))
        SET @mesaj += 'Programarea nu există! '

    RETURN @mesaj
END
GO

CREATE OR ALTER FUNCTION validareProgramare
(@data DATE, @ora TIME, @idMedic INT, @idPacient INT, @idIncapere INT)
RETURNS VARCHAR(100)
AS 
BEGIN
    DECLARE @mesaj VARCHAR(100) = ''

    IF (@data < CAST(GETDATE() AS DATE))
        SET @mesaj += 'Data programării este în trecut! '

    IF (NOT EXISTS (SELECT IDMedic FROM Medici WHERE IDMedic = @idMedic))
        SET @mesaj += 'Medicul nu există! '

    IF (NOT EXISTS (SELECT IDPacient FROM Pacienti WHERE IDPacient = @idPacient))
        SET @mesaj += 'Pacientul nu există! '

    IF (NOT EXISTS (SELECT IDIncapere FROM Incaperi WHERE IDIncapere = @idIncapere))
        SET @mesaj += 'Încăperea nu există! '

    RETURN @mesaj
END
GO

-------------------------
-- TABEL LOGĂRI ACȚIUNI --
-------------------------
CREATE TABLE IstoricLogare
(
    ID INT PRIMARY KEY IDENTITY,
    Actiune VARCHAR(20),
    Tabel VARCHAR(20),
    DataExecutiei DATETIME
)

---------------------------------------------
-- PROCEDURĂ CU ROLLBACK PE ÎNTREAGA OPERAȚIE --
---------------------------------------------
GO
CREATE OR ALTER PROCEDURE AddTratamentProgramare
    @denumire VARCHAR(50),
    @pret INT,
    @durata INT,
    @data DATE,
    @ora TIME,
    @idMedic INT,
    @idPacient INT,
    @idIncapere INT
AS
BEGIN
    BEGIN TRAN
    BEGIN TRY
        DECLARE @mesaj VARCHAR(200) = ''
        DECLARE @idProgramare INT

        DECLARE @msgProgramare VARCHAR(100) = dbo.validareProgramare(@data, @ora, @idMedic, @idPacient, @idIncapere)
        IF @msgProgramare <> ''
            SET @mesaj += @msgProgramare

        IF @mesaj <> ''
            RAISERROR(@mesaj, 14, 1)

        -- Inserare Programare
        INSERT INTO Programari(DataProgramare, Ora, Medic, Pacient, Incapere)
        VALUES (@data, @ora, @idMedic, @idPacient, @idIncapere)
        SET @idProgramare = SCOPE_IDENTITY()

        INSERT INTO IstoricLogare(Actiune, Tabel, DataExecutiei) VALUES ('Insert', 'Programari', GETDATE())

        -- Validare tratament
        DECLARE @msgTratament VARCHAR(100) = dbo.validareTratament(@denumire, @pret, @durata, @idProgramare)
        IF @msgTratament <> ''
            RAISERROR(@msgTratament, 14, 1)

        -- Inserare Tratament
        INSERT INTO Tratamente(Denumire, Pret, DurataMinute, Programare)
        VALUES (@denumire, @pret, @durata, @idProgramare)

        INSERT INTO IstoricLogare(Actiune, Tabel, DataExecutiei) VALUES ('Insert', 'Tratamente', GETDATE())

        COMMIT TRAN
        SELECT 'Transaction committed'
    END TRY
    BEGIN CATCH
        ROLLBACK TRAN
        SELECT 'Transaction rollbacked'
    END CATCH
END
GO

---------------------------------------------
-- PROCEDURĂ CU INSERARE PARȚIALĂ --
---------------------------------------------
GO
CREATE OR ALTER PROCEDURE AddTratamentProgramare2
    @denumire VARCHAR(50),
    @pret INT,
    @durata INT,
    @data DATE,
    @ora TIME,
    @idMedic INT,
    @idPacient INT,
    @idIncapere INT
AS
BEGIN
    DECLARE @idProgramare INT = 0
    DECLARE @inserareTratament INT = 0

    -- Programare
    BEGIN TRAN
    BEGIN TRY
        DECLARE @msgProgramare VARCHAR(100) = dbo.validareProgramare(@data, @ora, @idMedic, @idPacient, @idIncapere)
        IF @msgProgramare <> ''
            RAISERROR(@msgProgramare, 14, 1)

        INSERT INTO Programari(DataProgramare, Ora, Medic, Pacient, Incapere)
        VALUES (@data, @ora, @idMedic, @idPacient, @idIncapere)
        SET @idProgramare = SCOPE_IDENTITY()
        INSERT INTO IstoricLogare(Actiune, Tabel, DataExecutiei) VALUES ('Insert', 'Programari', GETDATE())

        COMMIT TRAN
        SELECT 'Transaction Programare committed'
    END TRY
    BEGIN CATCH
        ROLLBACK TRAN
        INSERT INTO IstoricLogare(Actiune, Tabel, DataExecutiei) VALUES ('ROLLBACK', 'Programari', GETDATE())
        SELECT 'Transaction Programare rollbacked'
    END CATCH

    -- Tratament
    IF (@idProgramare > 0)
    BEGIN
        BEGIN TRAN
        BEGIN TRY
            DECLARE @msgTratament VARCHAR(100) = dbo.validareTratament(@denumire, @pret, @durata, @idProgramare)
            IF @msgTratament <> ''
                RAISERROR(@msgTratament, 14, 1)

            INSERT INTO Tratamente(Denumire, Pret, DurataMinute, Programare)
            VALUES (@denumire, @pret, @durata, @idProgramare)
            INSERT INTO IstoricLogare(Actiune, Tabel, DataExecutiei) VALUES ('Insert', 'Tratamente', GETDATE())

            COMMIT TRAN
            SELECT 'Transaction Tratament committed'
        END TRY
        BEGIN CATCH
            ROLLBACK TRAN
            INSERT INTO IstoricLogare(Actiune, Tabel, DataExecutiei) VALUES ('ROLLBACK', 'Tratamente', GETDATE())
            SELECT 'Transaction Tratament rollbacked'
        END CATCH
    END
END
GO

-- Succes
EXEC AddTratamentProgramare 'Albire', 300, 30, '2025-06-01', '10:00:00', 1, 1, 1

-- Eșec total (rollback)
EXEC AddTratamentProgramare 'AB', -50, 0, '2020-01-01', '08:00:00', 99, 99, 99

-- Parțial (în AddTratamentProgramare2)
EXEC AddTratamentProgramare2 'Plombă', 200, 20, '2025-06-01', '12:00:00', 1, 1, 1

SELECT * FROM Programari
SELECT * FROM Tratamente
SELECT * FROM IstoricLogare
