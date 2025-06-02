CREATE NONCLUSTERED INDEX idx_biscuiti_pret ON Biscuiti(pret);

SELECT * FROM Biscuiti WHERE pret>=5 AND pret<=7;
