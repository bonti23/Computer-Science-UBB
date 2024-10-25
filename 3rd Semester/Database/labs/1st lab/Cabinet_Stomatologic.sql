Create database Cabinet_Stomatologic
go
use Cabinet_Stomatologic 
go

CREATE TABLE Sectii
(
IDSectie INT PRIMARY KEY IDENTITY,
Denumire VARCHAR(50),
NumarMedici INT
)

CREATE TABLE Medici
(
IDMedic INT PRIMARY KEY IDENTITY,
Nume VARCHAR(50),
Prenume VARCHAR(50),
Telefon VARCHAR(50),
Sectie INT FOREIGN KEY REFERENCES Sectii(IDSectie),
)

CREATE TABLE Asistenti
(
IDAsistent INT PRIMARY KEY IDENTITY,
Nume VARCHAR(50),
Prenume VARCHAR(50),
Telefon VARCHAR(50),
Medic INT FOREIGN KEY REFERENCES Medici(IDMedic) 
)
CREATE TABLE Studenti
(
IDstudent INT PRIMARY KEY IDENTITY,
Nume VARCHAR(50),
Prenume VARCHAR(50),
Telefon VARCHAR(50),
Practica VARCHAR(50),
AnAcademic INT,
Universitate VARCHAR(50),
Asistent INT FOREIGN KEY REFERENCES Asistenti(IDAsistent)
)
CREATE TABLE Pacienti
(
IDPacient INT PRIMARY KEY IDENTITY,
Nume VARCHAR(50) NOT NULL,
Prenume VARCHAR(50) NOT NULL,
Telefon VARCHAR(50),
Email VARCHAR(50),
Adresa VARCHAR(50)
)

CREATE TABLE Incaperi
(
IDIncapere INT PRIMARY KEY IDENTITY,
Etaj INT NOT NULL
)

CREATE TABLE Ingrijitori
(
IDIngrijitor INT FOREIGN KEY REFERENCES Incaperi(IDIncapere),
Nume VARCHAR(50),
Prenume VARCHAR(50),
CONSTRAINT PKIngrijitor PRIMARY KEY(IDIngrijitor)
)

CREATE TABLE Programari
(
IDProgramare INT PRIMARY KEY IDENTITY,
DataProgramare DATETIME NOT NULL,
Ora TIME NOT NULL,
Medic INT FOREIGN KEY REFERENCES Medici(IDMedic),
Pacient INT FOREIGN KEY REFERENCES Pacienti(IDPacient),
Incapere INT FOREIGN KEY REFERENCES Incaperi(IDIncapere)
)

CREATE TABLE Tratamente
(
IDTratament INT PRIMARY KEY IDENTITY,
Denumire VARCHAR(50),
Pret INT,
DurataMinute INT,
Programare INT FOREIGN KEY REFERENCES Programari(IDProgramare)
)

CREATE TABLE Aparate
(
IDAparat INT PRIMARY KEY IDENTITY,
Denumire VARCHAR(50),
Tratament INT FOREIGN KEY REFERENCES Tratamente(IDTratament)
)
