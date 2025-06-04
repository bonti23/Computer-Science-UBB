CREATE TABLE contacte (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nume VARCHAR(100),
    prenume VARCHAR(100),
    telefon VARCHAR(20),
    email VARCHAR(100)
);

INSERT INTO contacte (nume, prenume, telefon, email) VALUES
('Popescu', 'Ion', '0712345678', 'ion.popescu@example.com'),
('Ionescu', 'Maria', '0723456789', 'maria.ionescu@example.com'),
('Georgescu', 'Andrei', '0734567890', 'andrei.georgescu@example.com'),
('Dumitru', 'Ana', '0745678901', 'ana.dumitru@example.com'),
('Vasilescu', 'Mihai', '0756789012', 'mihai.vasilescu@example.com');
