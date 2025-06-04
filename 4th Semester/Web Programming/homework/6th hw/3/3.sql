CREATE TABLE persoane (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nume VARCHAR(100),
    prenume VARCHAR(100),
    email VARCHAR(150)
);
INSERT INTO persoane (nume, prenume, email) VALUES
('Popescu', 'Ion', 'ion.popescu@example.com'),
('Ionescu', 'Maria', 'maria.ionescu@example.com');
