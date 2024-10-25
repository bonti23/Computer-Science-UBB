USE Cabinet_Stomatologic
GO

-- SECTII
INSERT INTO Sectii(Denumire, NumarMedici)
VALUES('Chirurgie', 3),
		('Ortodontie', 4),
		('Endodontie', 2),
		('Parodontologie', 1),
		('Pedodontie', 2);

--MEDICI
INSERT INTO Medici(Nume, Prenume, Telefon, Sectie)
VALUES('Zimbran', 'Mihai', '0711223344', 1),
		('Pop', 'Ilinca', '0711232345', 1),
		('Popescu', 'Andrei', '0799885643', 1),
		('Munteanu', 'Alexia', '0755341234', 2),
		('Marinescu', 'Gheorghe', '0743625362', 2),
		('Stanciu', 'Gloria', '0707070707', 2),
		('Moldovan', 'Bogdan', '0711223344', 2),
		('Dobre', 'Oana', '0789988998', 3),
		('Toma', 'Cristian', '0723122345', 3),
		('Lin', 'Denisa', '0712121212', 4),
		('Cristea', 'Ovidiu', '0756897423', 5),
		('Popa', 'Andreea', '0719235467', 5);

--
INSERT INTO Asistenti(Nume, Prenume, Telefon, Medic)
VALUES('Radu', 'Bianca', '0786564636', 1),
		('Iordache', 'Florin', '0789898999', 1),
		('Ene', 'Sorina', '0781234567', 2),
        ('Stoica', 'Dan', '0782345678', 3),
        ('Cernat', 'Alina', '0783456789', 4),
        ('Munteanu', 'Victor', '0784567890', 5),
        ('Apostol', 'Gabriela', '0785678901', 6),
        ('Cojocaru', 'Iulian', '0786789012', 6),
        ('Toma', 'Lavinia', '0787890123', 7),
        ('Neagu', 'Mircea', '0788901234', 8),
        ('Pavel', 'Roxana', '0789012345', 9),
        ('Roman', 'George', '0789123456', 9),
        ('Sava', 'Nicoleta', '0789234567', 10),
        ('Petcu', 'Darius', '0789345678', 11),
        ('Mihailescu', 'Teodora', '0789456789', 12),
        ('Grigorescu', 'Raul', '0789567890', 12);

INSERT INTO Studenti(Nume, Prenume, Telefon, Practica, AnAcademic, Universitate, Asistent)
VALUES('Popa', 'Andreea', '0781111222', 'Chirurgie', 5, 'UMFCD', 1),
       ('Vasilescu', 'Marian', '0782221333', 'Chirurgie', 5, 'UMFIH', 1),
       ('Dima', 'Larisa', '0783331444', 'Chirurgie', 4, 'UMFCD', 1),
       ('Iliescu', 'Razvan', '0784441555', 'Chirurgie', 4, 'UMFIH', 2),
       ('Simion', 'Mirela', '0785551666', 'Chirurgie', 6, 'UMFCD', 2),
       ('Petrescu', 'Sergiu', '0786661777', 'Chirurgie', 6, 'UMFIH', 3),
       ('Anton', 'Cristina', '0787771888', 'Chirurgie', 5, 'UMFCD', 4),
       ('Baciu', 'Florina', '0788881999', 'Chirurgie', 6, 'UMFIH', 4),
       ('Stan', 'Claudiu', '0789992000', 'Chirurgie', 4, 'UMFCD', 4),

       ('Marinescu', 'Anca', '0780002111', 'Ortodontie', 3, 'UMFST', 5),
       ('Diaconu', 'Gabriel', '0781112222', 'Ortodontie', 4, 'UMFCD', 6),
       ('Enache', 'Daniela', '0782222333', 'Ortodontie', 4, 'UMFST', 7),
       ('Voinea', 'Costin', '0783332444', 'Ortodontie', 5, 'UMFCD', 8),
       ('Constantin', 'Ruxandra', '0784442555', 'Ortodontie', 6, 'UMFST', 9),

       ('Georgescu', 'Adrian', '0785552666', 'Endodontie', 4, 'UMFVB', 10),
       ('Cristea', 'Liliana', '0786662777', 'Endodontie', 5, 'UMFVB', 11),
       ('Dragomir', 'Marius', '0787772888', 'Endodontie', 5, 'UMFVB', 11),
       ('Sima', 'Paula', '0788882999', 'Endodontie', 6, 'UMFVB', 12),
       ('Radu', 'Vlad', '0789992111', 'Endodontie', 6, 'UMFVB', 12),

       ('Moraru', 'Ileana', '0780002333', 'Parodontologie', 4, 'UMFIH', 13),

       ('Ciobanu', 'Cosmin', '0781112444', 'Pedodontie', 3, 'UMFIH', 14),
       ('Anghel', 'Silvia', '0782222555', 'Pedodontie', 4, 'UMFIH', 15),
       ('Dobre', 'Lucian', '0783332666', 'Pedodontie', 6, 'UMFIH', 16),
       ('Filip', 'Otilia', '0784442777', 'Pedodontie', 5, 'UMFIH', 16);

--PACIENTI
INSERT INTO Pacienti(Nume, Prenume, Telefon, Email, Adresa)
VALUES('García', 'Miguel', '0781234567', 'miguel.garcia@email.com', 'Calea Moților 15'),
       ('Martínez', 'Lucía', '0782345678', 'lucia.martinez@email.com', 'Bulevardul Eroilor 30'),
       ('López', 'Carlos', '0783456789', 'carlos.lopez@email.com', 'Strada Horea 10'),
       ('Sánchez', 'María', '0784567890', 'maria.sanchez@email.com', 'Strada Cărămidăriei 25'),
       ('Pérez', 'Juan', '0785678901', 'juan.perez@email.com', 'Calea Someșului 100'),
       ('González', 'Ana', '0786789012', 'ana.gonzalez@email.com', 'Bulevardul 21 Decembrie 1989 50'),
       ('Rodríguez', 'José', '0787890123', 'jose.rodriguez@email.com', 'Strada Avram Iancu 60'),
       ('Fernández', 'Carmen', '0788901234', 'carmen.fernandez@email.com', 'Strada Baladei 5'),
       ('Gómez', 'Luis', '0789012345', 'luis.gomez@email.com', 'Calea Florești 20'),
       ('Ruiz', 'Laura', '0789123456', 'laura.ruiz@email.com', 'Bulevardul Nicolae Titulescu 30'),
       ('Díaz', 'Alberto', '0789234567', 'alberto.diaz@email.com', 'Strada Măceșului 45'),
       ('Moreno', 'Isabel', '0789345678', 'isabel.moreno@email.com', 'Calea Turzii 4'),
       ('Hernández', 'Raúl', '0789456789', 'raul.hernandez@email.com', 'Strada Răsăritului 120'),
       ('Jiménez', 'Pilar', '0789567890', 'pilar.jimenez@email.com', 'Bulevardul Muncii 9'),
       ('Álvarez', 'Manuel', '0789678901', 'manuel.alvarez@email.com', 'Strada Căpitan Grigore 80'),
       ('Castro', 'Sandra', '0789789012', 'sandra.castro@email.com', 'Calea Baciului 22'),
       ('Ortiz', 'Roberto', '0789890123', 'roberto.ortiz@email.com', 'Strada Dorobanților 35'),
       ('Ramírez', 'Cristina', '0789901234', 'cristina.ramirez@email.com', 'Calea Moților 55'),
       ('Romero', 'Francisco', '0789912345', 'francisco.romero@email.com', 'Strada Făgetului 17'),
       ('Serrano', 'Elena', '0789923456', 'elena.serrano@email.com', 'Calea Someșului 99'),
       ('Blanco', 'David', '0789934567', 'david.blanco@email.com', 'Bulevardul Eroilor 60'),
       ('Molina', 'Marta', '0789945678', 'marta.molina@email.com', 'Strada Avram Iancu 8'),
       ('Delgado', 'Javier', '0789956789', 'javier.delgado@email.com', 'Calea Florești 23'),
       ('Castillo', 'Patricia', '0789967890', 'patricia.castillo@email.com', 'Strada Răsăritului 70'),
       ('Gil', 'Andrés', '0789978901', 'andres.gil@email.com', 'Bulevardul 21 Decembrie 1989 99'),
       ('Iglesias', 'Verónica', '0789989012', 'veronica.iglesias@email.com', 'Strada Măceșului 10'),
       ('Santos', 'Fernando', '0789990123', 'fernando.santos@email.com', 'Calea Turzii 88'),
       ('Vargas', 'Rosa', '0780001234', 'rosa.vargas@email.com', 'Calea Moților 45'),
       ('Torres', 'Joaquín', '0781112345', 'joaquin.torres@email.com', 'Bulevardul Eroilor 15'),
       ('Peña', 'Adriana', '0782223456', 'adriana.pena@email.com', 'Strada Horea 200');

--INCAPERI
INSERT INTO Incaperi(Etaj)
VALUES(0),
		(0),
		(0),
		(0),
		(1),
		(1),
		(1),
		(1),
		(2),
		(2),
		(2),
		(2);

--INGRIJITORI
INSERT INTO Ingrijitori(IDIngrijitor, Nume, Prenume)
VALUES(1, 'Smith', 'James'),
		(2, 'Johnson', 'Emily'),
		(3, 'Brown', 'Michael'),
		(4, 'Williams', 'Jessica'),
		(5, 'Jones', 'Daniel'),
		(6, 'Davis', 'Sarah'),
		(7, 'Miller', 'Christopher'),
		(8, 'Wilson', 'Olivia'),
		(9, 'Taylor', 'Matthew'),
		(10, 'Anderson', ' Sophia'),
		(11, 'Thomas', 'Joshua'),
		(12, 'Jackson', 'Isabella')

--PROGRAMARI
INSERT INTO Programari(DataProgramare, Ora, Medic, Pacient, Incapere)
VALUES()
