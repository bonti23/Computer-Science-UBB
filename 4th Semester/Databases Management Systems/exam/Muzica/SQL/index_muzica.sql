CREATE NONCLUSTERED INDEX idx_melodii_an ON Melodii(an_lansare);

SELECT * FROM Melodii WHERE an_lansare >= 2001 AND an_lansare <= 2011;
