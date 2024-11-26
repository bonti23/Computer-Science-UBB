--DO

--DROP PROCEDURE procedure_1
CREATE PROCEDURE procedure_1 AS
ALTER TABLE Medici
ALTER COLUMN Nume VARCHAR(100)
PRINT 'Rubrica [Nume] s-a modificat din cadrul tabelului [Medici]'
GO

--DROP PROCEDURE procedure_2
CREATE PROCEDURE procedure_2 AS
ALTER TABLE Sectii
ADD CONSTRAINT numar_minim CHECK (NumarMedici >= 1);
PRINT 'A fost adaugata o constrangere CHECK pentru rubrica [NumarMedici] din tabelul [Sectii], impunand cel putin 1 medic in fiecare sectie.'
GO

--DROP PROCEDURE procedure_3
CREATE PROCEDURE procedure_3 AS
IF OBJECT_ID('Clinici', 'U') IS NOT NULL
    BEGIN
        DROP TABLE Clinici;
        PRINT 'Tabelul [Clinici] a fost șters deoarece exista deja.'
    END
CREATE TABLE Clinici(
ClinicaID int PRIMARY KEY NOT NULL,
Denumire VARCHAR(50)
)
PRINT 'S-a creat un nou tabel, numit [Clinica]'
GO

--DROP PROCEDURE procedure_4
CREATE PROCEDURE procedure_4 AS
ALTER TABLE Clinici
ADD Adresa VARCHAR(50)
PRINT 'S-a adaugat rubrica [Adresa] din tabelul [Clinica]'
GO

--DROP PROCEDURE procedure_5
CREATE PROCEDURE procedure_5 AS
ALTER TABLE Clinici
ADD Sectie INT;
ALTER TABLE Clinici
ADD CONSTRAINT FK_Sectie FOREIGN KEY(Sectie) REFERENCES Sectii(IDSectie)
PRINT 'S-a adaugat o cheie straina'
GO

--UNDO
--DROP PROCEDURE undo_procedure_1
CREATE PROCEDURE undo_procedure_1 AS
ALTER TABLE Medici
ALTER COLUMN Nume VARCHAR(50)
PRINT 'S-a remodificat rubrica [Nume] din tabelul [Medici]'
GO

--DROP PROCEDURE undo_procedure_2
CREATE PROCEDURE undo_procedure_2 AS
ALTER TABLE Sectii
DROP CONSTRAINT numar_minim
PRINT 'S-a eliminat constrangerea pentru rubrica [NumarMedici] din tabelul [Sectii]'
GO

--DROP PROCEDURE undo_procedure_3
CREATE PROCEDURE undo_procedure_3 AS
DROP TABLE Clinici
PRINT 'S-a sters tabelul [Clinici]'
GO

--DROP PROCEDURE undo_procedure_4
CREATE PROCEDURE undo_procedure_4 AS
ALTER TABLE Clinici
DROP COLUMN Adresa
PRINT 'S-a remodificat rubrica [Adresa] din tabelul [Clinici]'
GO

--DROP PROCEDURE undo_procedure_5

CREATE PROCEDURE undo_procedure_5 AS
ALTER TABLE Clinici
DROP CONSTRAINT FK_Sectie;
ALTER TABLE Clinici
DROP COLUMN Sectie;
PRINT 'S-a eliminat cheia straina'
GO

DROP TABLE IF EXISTS Versiune
CREATE TABLE Versiune(
	version_number int
);

INSERT INTO Versiune VALUES(0)

SELECT * FROM Versiune

GO
CREATE OR ALTER PROCEDURE main @versiune INT
AS
BEGIN
	IF @versiune<0 or @versiune>5
	BEGIN
		PRINT 'invalid version!'
		RETURN
	END

	DECLARE @current_version AS INT
	SET @current_version=(SELECT version_number FROM Versiune)

	IF @versiune=@current_version
	BEGIN
		PRINT 'the actual one is equal to the current one'
		RETURN
	END

	DECLARE @procedure VARCHAR(20)
	DECLARE @undo_procedure VARCHAR(20)

	DECLARE @ok AS INT
	SET @ok=0

	DELETE FROM Versiune
	INSERT INTO Versiune(version_number) VALUES(@versiune)

	WHILE(@current_version<@versiune)
	BEGIN
		SET @ok = @ok + 1
		SET @current_version=@current_version+1
		SET @procedure = 'procedure_'+CAST(@current_version AS VARCHAR(10))
		PRINT 'procedura: '+@procedure
		EXEC @procedure
	END

	IF(@ok>0)
	BEGIN
		RETURN
	END

	WHILE(@current_version>@versiune)
	BEGIN
		SET @undo_procedure = 'undo_procedure_'+CAST(@current_version AS VARCHAR(10))
		PRINT 'undo procedure: '+@undo_procedure
		EXEC @undo_procedure
		SET @current_version=@current_version-1
	END

END
GO

exec procedure_1
exec undo_procedure_1

exec procedure_2
exec undo_procedure_2

exec procedure_3
exec undo_procedure_3

exec procedure_4
exec undo_procedure_4

exec procedure_5
exec undo_procedure_5


exec main 0
exec main 1
exec main 2
exec main 3
exec main 4
exec main 5
