--DROP FUNCTION dbo.ValidareTelefon;
CREATE FUNCTION dbo.ValidareTelefon(@Telefon VARCHAR(50))
RETURNS BIT
AS
BEGIN
    DECLARE @isValid BIT;
    IF LEN(@Telefon) <> 10 OR LEFT(@Telefon, 2) <> '07' OR @Telefon LIKE '%[^0-9]%'
    BEGIN
        SET @isValid = 0;
        RETURN @isValid;
    END
    SET @isValid = 1;
    RETURN @isValid;
END;

--DROP FUNCTION dbo.ValidareEmail;

CREATE FUNCTION dbo.ValidareEmail(@Email VARCHAR(50))
RETURNS BIT
AS
BEGIN
    DECLARE @isValid BIT;
    IF CHARINDEX('@', @Email) > 1 AND CHARINDEX('.', @Email, CHARINDEX('@', @Email)) > CHARINDEX('@', @Email)
	BEGIN
		SET @isValid = 1;
		RETURN @isValid;
	END
	SET @isValid = 0;
	RETURN @isValid;
END;

-- CRUD Pacienti
-- CREATE
CREATE PROCEDURE dbo.InsertPacient
    @Nume VARCHAR(50),
    @Prenume VARCHAR(50),
    @Telefon VARCHAR(50),
    @Email VARCHAR(50),
    @Adresa VARCHAR(50)
AS
BEGIN
    IF dbo.ValidareTelefon(@Telefon) = 0
    BEGIN
        PRINT 'Eroare: Telefon invalid!';
        RETURN;
    END;

    IF dbo.ValidareEmail(@Email) = 0
    BEGIN
        PRINT 'Eroare: Email invalid!';
        RETURN;
    END;

    INSERT INTO Pacienti (Nume, Prenume, Telefon, Email, Adresa)
    VALUES (@Nume, @Prenume, @Telefon, @Email, @Adresa);

    PRINT 'Pacient adaugat cu succes!';
END;


-- READ
--toti pacientii
CREATE PROCEDURE dbo.GetAllPacienti
AS
BEGIN
    SELECT IDPacient, Nume, Prenume, Telefon, Email, Adresa
    FROM Pacienti;
END;

--pacient dupa id
CREATE PROCEDURE dbo.GetPacientByID
    @IDPacient INT
AS
BEGIN
    SELECT IDPacient, Nume, Prenume, Telefon, Email, Adresa
    FROM Pacienti
    WHERE IDPacient = @IDPacient;

    IF @@ROWCOUNT = 0
    BEGIN
        PRINT 'Pacientul cu ID-ul specificat nu exista.';
    END;
END;

-- UPDATE
CREATE PROCEDURE dbo.UpdatePacient
    @IDPacient INT,
    @Nume VARCHAR(50),
    @Prenume VARCHAR(50),
    @Telefon VARCHAR(50),
    @Email VARCHAR(50),
    @Adresa VARCHAR(50)
AS
BEGIN
    IF dbo.ValidareTelefon(@Telefon) = 0
    BEGIN
        PRINT 'Eroare: Telefon invalid!';
        RETURN;
    END;

    IF dbo.ValidareEmail(@Email) = 0
    BEGIN
        PRINT 'Eroare: Email invalid!';
        RETURN;
    END;

    UPDATE Pacienti
    SET Nume = @Nume,
        Prenume = @Prenume,
        Telefon = @Telefon,
        Email = @Email,
        Adresa = @Adresa
    WHERE IDPacient = @IDPacient;

    IF @@ROWCOUNT = 0
    BEGIN
        PRINT 'Pacientul cu ID-ul specificat nu exista.';
    END
    ELSE
    BEGIN
        PRINT 'Pacient actualizat cu succes!';
    END;
END;

--DELETE
CREATE PROCEDURE dbo.DeletePacient
    @IDPacient INT
AS
BEGIN
    DELETE FROM Pacienti
    WHERE IDPacient = @IDPacient;

    IF @@ROWCOUNT = 0
    BEGIN
        PRINT 'Pacientul cu ID-ul specificat nu exista.';
    END
    ELSE
    BEGIN
        PRINT 'Pacient sters cu succes!';
    END;
END;



--CRUD Medici
--CREATE
CREATE PROCEDURE dbo.InsertMedic
    @Nume VARCHAR(50),
    @Prenume VARCHAR(50),
    @Telefon VARCHAR(50),
    @Sectie INT
AS
BEGIN
    IF dbo.ValidareTelefon(@Telefon) = 0
    BEGIN
        PRINT 'Telefon invalid!';
        RETURN;
    END;

    INSERT INTO Medici (Nume, Prenume, Telefon, Sectie)
    VALUES (@Nume, @Prenume, @Telefon, @Sectie);

    PRINT 'Medic adăugat cu succes!';
END;

--READ
--toti medicii
CREATE PROCEDURE dbo.GetAllMedici
AS
BEGIN
    SELECT IDMedic, Nume, Prenume, Telefon, Sectie
    FROM Medici;
END;

--medic dupa id
CREATE PROCEDURE dbo.GetMedicByID
    @IDMedic INT
AS
BEGIN
    SELECT IDMedic, Nume, Prenume, Telefon, Sectie
    FROM Medici
    WHERE IDMedic = @IDMedic;
END;

--UPDATE
CREATE PROCEDURE dbo.UpdateMedic
    @IDMedic INT,
    @Nume VARCHAR(50),
    @Prenume VARCHAR(50),
    @Telefon VARCHAR(50),
    @Sectie INT
AS
BEGIN
    IF dbo.ValidareTelefon(@Telefon) = 0
    BEGIN
        PRINT 'Telefon invalid!';
        RETURN;
    END;

    UPDATE Medici
    SET Nume = @Nume,
        Prenume = @Prenume,
        Telefon = @Telefon,
        Sectie = @Sectie
    WHERE IDMedic = @IDMedic;

    PRINT 'Medic actualizat cu succes!';
END;

--DELETE
CREATE PROCEDURE dbo.DeleteMedic
    @IDMedic INT
AS
BEGIN
    DELETE FROM Medici
    WHERE IDMedic = @IDMedic;

    PRINT 'Medic șters cu succes!';
END;


--CRUD Programari
--CREATE
CREATE PROCEDURE dbo.InsertProgramare
    @DataProgramare DATE,
    @Ora TIME,
    @Medic INT,
    @Pacient INT,
    @Incapere INT
AS
BEGIN
    INSERT INTO Programari (DataProgramare, Ora, Medic, Pacient, Incapere)
    VALUES (@DataProgramare, @Ora, @Medic, @Pacient, @Incapere);

    PRINT 'Programare adăugată cu succes!';
END;

--READ
--toate programarile
CREATE PROCEDURE dbo.GetAllProgramari
AS
BEGIN
    SELECT IDProgramare, DataProgramare, Ora, Medic, Pacient, Incapere
    FROM Programari;
END;

--dupa id
CREATE PROCEDURE dbo.GetProgramareByID
    @IDProgramare INT
AS
BEGIN
    SELECT IDProgramare, DataProgramare, Ora, Medic, Pacient, Incapere
    FROM Programari
    WHERE IDProgramare = @IDProgramare;

    IF @@ROWCOUNT = 0
    BEGIN
        PRINT 'Programarea cu ID-ul specificat nu există.';
    END;
END;

--UPDATE
CREATE PROCEDURE dbo.UpdateProgramare
    @IDProgramare INT,
    @DataProgramare DATE,
    @Ora TIME,
    @Medic INT,
    @Pacient INT,
    @Incapere INT
AS
BEGIN
    UPDATE Programari
    SET DataProgramare = @DataProgramare,
        Ora = @Ora,
        Medic = @Medic,
        Pacient = @Pacient,
        Incapere = @Incapere
    WHERE IDProgramare = @IDProgramare;

    IF @@ROWCOUNT = 0
    BEGIN
        PRINT 'Programarea cu ID-ul specificat nu există.';
    END
    ELSE
    BEGIN
        PRINT 'Programare actualizată cu succes!';
    END;
END;


--DELETE
CREATE PROCEDURE dbo.DeleteProgramare
    @IDProgramare INT
AS
BEGIN
    DELETE FROM Programari
    WHERE IDProgramare = @IDProgramare;

    IF @@ROWCOUNT = 0
    BEGIN
        PRINT 'Programarea cu ID-ul specificat nu există.';
    END
    ELSE
    BEGIN
        PRINT 'Programare ștersă cu succes!';
    END;
END;


-- Proceduri CRUD pentru Asistenti
-- CREATE
CREATE PROCEDURE dbo.InsertAsistent
    @Nume VARCHAR(50),
    @Prenume VARCHAR(50),
    @Telefon VARCHAR(50),
    @Medic INT
AS
BEGIN
    INSERT INTO Asistenti (Nume, Prenume, Telefon, Medic)
    VALUES (@Nume, @Prenume, @Telefon, @Medic);

    PRINT 'Asistent adăugat cu succes!';
END;

-- READ
-- toți asistenții
CREATE PROCEDURE dbo.GetAllAsistenti
AS
BEGIN
    SELECT IDAsistent, Nume, Prenume, Telefon, Medic
    FROM Asistenti;
END;

-- un asistent după ID
CREATE PROCEDURE dbo.GetAsistentByID
    @IDAsistent INT
AS
BEGIN
    SELECT IDAsistent, Nume, Prenume, Telefon, Medic
    FROM Asistenti
    WHERE IDAsistent = @IDAsistent;
END;

-- UPDATE
CREATE PROCEDURE dbo.UpdateAsistent
    @IDAsistent INT,
    @Nume VARCHAR(50),
    @Prenume VARCHAR(50),
    @Telefon VARCHAR(50),
    @Medic INT
AS
BEGIN
    UPDATE Asistenti
    SET Nume = @Nume,
        Prenume = @Prenume,
        Telefon = @Telefon,
        Medic = @Medic
    WHERE IDAsistent = @IDAsistent;

    PRINT 'Asistent actualizat cu succes!';
END;

-- DELETE
CREATE PROCEDURE dbo.DeleteAsistent
    @IDAsistent INT
AS
BEGIN
    DELETE FROM Asistenti
    WHERE IDAsistent = @IDAsistent;

    PRINT 'Asistent șters cu succes!';
END;

-- Proceduri CRUD pentru Studenti
-- CREATE
CREATE PROCEDURE dbo.InsertStudent
    @Nume VARCHAR(50),
    @Prenume VARCHAR(50),
    @Telefon VARCHAR(50),
    @Practica VARCHAR(50),
    @AnAcademic INT,
    @Universitate VARCHAR(50),
    @Asistent INT
AS
BEGIN
    INSERT INTO Studenti (Nume, Prenume, Telefon, Practica, AnAcademic, Universitate, Asistent)
    VALUES (@Nume, @Prenume, @Telefon, @Practica, @AnAcademic, @Universitate, @Asistent);

    PRINT 'Student adăugat cu succes!';
END;

-- READ
-- toți studenții
CREATE PROCEDURE dbo.GetAllStudenti
AS
BEGIN
    SELECT IDstudent, Nume, Prenume, Telefon, Practica, AnAcademic, Universitate, Asistent
    FROM Studenti;
END;

-- un student după ID
CREATE PROCEDURE dbo.GetStudentByID
    @IDstudent INT
AS
BEGIN
    SELECT IDstudent, Nume, Prenume, Telefon, Practica, AnAcademic, Universitate, Asistent
    FROM Studenti
    WHERE IDstudent = @IDstudent;
END;

-- UPDATE
CREATE PROCEDURE dbo.UpdateStudent
    @IDstudent INT,
    @Nume VARCHAR(50),
    @Prenume VARCHAR(50),
    @Telefon VARCHAR(50),
    @Practica VARCHAR(50),
    @AnAcademic INT,
    @Universitate VARCHAR(50),
    @Asistent INT
AS
BEGIN
    UPDATE Studenti
    SET Nume = @Nume,
        Prenume = @Prenume,
        Telefon = @Telefon,
        Practica = @Practica,
        AnAcademic = @AnAcademic,
        Universitate = @Universitate,
        Asistent = @Asistent
    WHERE IDstudent = @IDstudent;

    PRINT 'Student actualizat cu succes!';
END;

-- DELETE
CREATE PROCEDURE dbo.DeleteStudent
    @IDstudent INT
AS
BEGIN
    DELETE FROM Studenti
    WHERE IDstudent = @IDstudent;

    PRINT 'Student șters cu succes!';
END;

















-- CRUD Pacienti
-- Create
EXEC dbo.InsertPacient 
    @Nume = 'Popescu',
    @Prenume = 'Ion',
    @Telefon = '0722123456',
    @Email = 'popescu.ion@email.com',
    @Adresa = 'Strada Victoriei, nr. 10';

SELECT * FROM Pacienti WHERE Telefon = '0722123456';

-- Read
EXEC dbo.GetAllPacienti;

EXEC dbo.GetPacientByID 
    @IDPacient = 32;

-- Update
EXEC dbo.UpdatePacient 
    @IDPacient = 32,
    @Nume = 'Ionescu',
    @Prenume = 'Maria',
    @Telefon = '0733123456',
    @Email = 'ionescu.maria@email.com',
    @Adresa = 'Strada Libertății, nr. 25';

SELECT * FROM Pacienti WHERE IDPacient = 32;

-- Delete
EXEC dbo.DeletePacient 
    @IDPacient = 33;

SELECT * FROM Pacienti WHERE IDPacient = 31;

-- CRUD Medici
-- Create
EXEC dbo.InsertMedic 
    @Nume = 'Marin',
    @Prenume = 'Ana',
    @Telefon = '0744123456',
    @Sectie = 1;

SELECT * FROM Medici WHERE Telefon = '0744123456';

-- Read
EXEC dbo.GetAllMedici;

EXEC dbo.GetMedicByID 
    @IDMedic = 1;


-- Update
EXEC dbo.UpdateMedic 
    @IDMedic = 14,
    @Nume = 'Popa',
    @Prenume = 'George',
    @Telefon = '0755123456',
    @Sectie = 2;

SELECT * FROM Medici WHERE IDMedic = 14;

-- Delete
EXEC dbo.DeleteMedic 
    @IDMedic = 14;

SELECT * FROM Medici WHERE IDMedic = 14;

-- CRUD Programari
-- Create
EXEC dbo.InsertProgramare 
    @Pacient = 1,
    @Medic = 2,
    @DataProgramare = '2024-12-15',
    @Ora = '10:30:00',
	@Incapere = 3;

SELECT * FROM Programari WHERE Pacient = 1 AND Medic = 2;

-- Read
EXEC dbo.GetAllProgramari;

EXEC dbo.GetProgramareByID 
    @IDProgramare = 31;

-- Update
EXEC dbo.UpdateProgramare 
    @IDProgramare = 31,       
    @Pacient = 1,          
    @Medic = 3,            
    @DataProgramare = '2024-12-16',
    @Ora = '11:00:00',
	@Incapere = 2

SELECT * FROM Programari WHERE IDProgramare = 31;

-- Delete
EXEC dbo.DeleteProgramare 
    @IDProgramare = 31;

SELECT * FROM Programari WHERE IDProgramare = 1;

-- CRUD Asistenti
-- Create
EXEC dbo.InsertAsistent 
    @Nume = 'Popescu',
    @Prenume = 'Elena',
    @Telefon = '0732123456',
    @Medic = 1;

SELECT * FROM Asistenti WHERE Telefon = '0732123456';

-- Read
EXEC dbo.GetAllAsistenti;

EXEC dbo.GetAsistentByID 
    @IDAsistent = 17;

-- Update
EXEC dbo.UpdateAsistent 
    @IDAsistent = 17,
    @Nume = 'Ionescu',
    @Prenume = 'Vasile',
    @Telefon = '0733123456',
    @Medic = 2;

SELECT * FROM Asistenti WHERE IDAsistent = 17;

-- Delete
EXEC dbo.DeleteAsistent 
    @IDAsistent = 17;

SELECT * FROM Asistenti WHERE IDAsistent = 17;

-- CRUD Studenti
-- Create
EXEC dbo.InsertStudent 
    @Nume = 'Dumitru',
    @Prenume = 'Ion',
    @Telefon = '0742123456',
    @Practica = 'Anestezie',
    @AnAcademic = 3,
    @Universitate = 'Universitatea de Medicina',
    @Asistent = 1;

SELECT * FROM Studenti WHERE Telefon = '0742123456';

-- Read
EXEC dbo.GetAllStudenti;

EXEC dbo.GetStudentByID 
    @IDStudent = 25;

-- Update
EXEC dbo.UpdateStudent 
    @IDStudent = 25,
    @Nume = 'Popa',
    @Prenume = 'Maria',
    @Telefon = '0743123456',
    @Practica = 'Chirurgie',
    @AnAcademic = 4,
    @Universitate = 'Universitatea de Medicina',
    @Asistent = 2;

SELECT * FROM Studenti WHERE IDStudent = 25;

-- Delete
EXEC dbo.DeleteStudent 
    @IDStudent = 25;

SELECT * FROM Studenti WHERE IDStudent = 25;
