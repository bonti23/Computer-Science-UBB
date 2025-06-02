--index cofetarie
--pas1: rulam asta
CREATE NONCLUSTERED INDEX idx_Briose_Pret ON Briose(pret);

--pas2: query -> include actual execution plan

--pas3: rulam asta:
SELECT * FROM Briose WHERE pret BETWEEN 6.99 AND 7.01;

--pas4: mergem in execution plan
