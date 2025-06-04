CREATE TABLE rute (
    id INT AUTO_INCREMENT PRIMARY KEY,
    oras_plecare VARCHAR(255),
    oras_sosire VARCHAR(255)
);

INSERT INTO rute (oras_plecare, oras_sosire) VALUES
('Oras1', 'Oras3'),
('Oras1', 'Oras4'),
('Oras2', 'Oras5'),
('Oras6', 'Oras7'),
('Oras6', 'Oras8');
