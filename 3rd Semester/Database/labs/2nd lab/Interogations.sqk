USE CabinetStomatologic
--1. Data si ora programarii, executata de medicul cu id-ul 1, asupra pacientului cu id-ul 1,
--plus numele si prenumele acestora.
SELECT 
    P.Nume AS Nume_Pacient, 
    P.Prenume AS Prenume_Pacient, 
    M.Nume AS Nume_Medic, 
    M.Prenume AS Prenume_Medic, 
    PR.DataProgramare AS Data_Programarii, 
    PR.Ora As Ora_Programarii
FROM Programari PR
JOIN Pacienti P ON PR.Pacient = P.IDPacient
JOIN Medici M ON PR.Medic = M.IDMedic
WHERE PR.Medic = 1 AND PR.Pacient = 1;

--2. Programarile care au loc in incaperile de la etajul 1
--plus numele ingrijitorilor care se ocupa de incaperile de la acel etaj
SELECT 
    PR.DataProgramare, 
    PR.Ora AS Ora_Programarii,
    I.IDIncapere AS Numarul_Incaperii,
	IG.Nume AS Nume_Ingrijitor,
	IG.Prenume AS Prenume_Ingrijitor
FROM Programari PR
JOIN Incaperi I ON PR.Incapere = I.IDIncapere
JOIN Ingrijitori IG ON I.IDIncapere = IG.IDIngrijitor
WHERE I.Etaj = 1;

--3. Medicii de la sectia 'Chirurgie' si asistentii lui.
SELECT 
    M.IDMedic, 
    M.Nume AS Nume_Medic, 
    M.Prenume AS Prenume_Medic,
    A.IDAsistent,
    A.Nume AS Nume_Asistent,
    A.Prenume AS Prenume_Asistent
FROM Medici M
JOIN Asistenti A ON M.IDMedic = A.Medic
WHERE M.Sectie = (SELECT IDSectie FROM Sectii WHERE Denumire = 'Chirurgie');

--4. Pacientii si ce tratamente li se vor aplica celora care au programari in luna decembrie.
SELECT 
    P.Nume AS Nume_Pacient,
    P.Prenume AS Prenume_Pacient,
    T.Denumire AS Denumire_Tratament,
    PR.DataProgramare,
    PR.Ora AS Ora_Programarii
FROM Programari PR
JOIN Pacienti P ON PR.Pacient = P.IDPacient
JOIN Tratamente T ON PR.IDProgramare = T.Programare
WHERE MONTH(PR.DataProgramare) = 12;

--5. Programarile de detartraj si medicii care au executat-o.
SELECT 
    PR.DataProgramare, 
    PR.Ora AS Ora_Programarii, 
    M.Nume AS Nume_Medic, 
    M.Prenume AS Prenume_Medic
FROM Programari PR
JOIN Tratamente T ON PR.IDProgramare = T.Programare
JOIN Medici M ON PR.Medic = M.IDMedic
WHERE T.Denumire = 'Detartraj';

--6. Pacientii unici si medicii care i-au programat.
SELECT DISTINCT 
    P.Nume AS Nume_Pacient, 
    P.Prenume AS Prenume_Pacient, 
    M.Nume AS Nume_Medic, 
    M.Prenume AS Prenume_Medic
FROM Pacienti P
JOIN Programari PR ON P.IDPacient = PR.Pacient
JOIN Medici M ON PR.Medic = M.IDMedic;

--7. Asistentii unici pentru medicii din sectia 'Ortodontie'
SELECT DISTINCT 
    A.Nume AS Nume_Asistent, 
    A.Prenume AS Prenume_Asistent
FROM Asistenti A
JOIN Medici M ON A.Medic = M.IDMedic
JOIN Sectii S ON M.Sectie = S.IDSectie
WHERE S.Denumire = 'Ortodontie';

--8. Medicii ai caror asistenti au cel putin 2 studenti la practica.
SELECT 
    M.IDMedic, 
    M.Nume AS Nume_Medic, 
    M.Prenume AS Prenume_Medic
FROM Medici M
JOIN Asistenti A ON M.IDMedic = A.Medic
JOIN Studenti S ON A.IDAsistent = S.Asistent
GROUP BY M.IDMedic, M.Nume, M.Prenume
HAVING COUNT(S.IDStudent)>=2;

--9. Pacientii care au platit sau vor plati in total mai mult de 1000 RON.
SELECT 
    P.IDPacient, 
    P.Nume AS Nume_Pacient, 
    P.Prenume AS Prenume_Pacient,
    SUM(T.Pret) AS Total
FROM Pacienti P
JOIN Programari PR ON P.IDPacient = PR.Pacient
JOIN Tratamente T ON PR.IDProgramare = T.Programare
GROUP BY P.IDPacient, P.Nume, P.Prenume
HAVING SUM(T.Pret)>1000;

--10. Programarile, tratamentele si aparatele folosite in cadrul acestora.
SELECT 
    PR.DataProgramare,
    PR.Ora AS Ora_Programare,
    T.Denumire,
    A.Denumire
FROM Programari PR
JOIN Tratamente T ON PR.IDProgramare = T.Programare
JOIN Aparate AP ON T.IDTratament = AP.Tratament
JOIN Aparate A ON AP.IDAparat = A.IDAparat
GROUP BY PR.DataProgramare, PR.Ora, T.Denumire, A.Denumire
ORDER BY  PR.DataProgramare,  PR.Ora;
