CREATE TABLE IF NOT EXISTS trenuri (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nr_tren VARCHAR(10) NOT NULL,
    tip_tren VARCHAR(20) NOT NULL,
    plecare VARCHAR(50) NOT NULL,
    ora_plecare TIME NOT NULL,
    sosire VARCHAR(50) NOT NULL,
    ora_sosire TIME NOT NULL
);

INSERT INTO trenuri (nr_tren, tip_tren, plecare, ora_plecare, sosire, ora_sosire) VALUES
('IR1753', 'InterRegio', 'București', '08:00:00', 'Brașov', '10:30:00'),
('RE2045', 'Regio', 'București', '09:00:00', 'Ploiești', '10:00:00'),
('IR1865', 'InterRegio', 'Ploiești', '10:15:00', 'Brașov', '12:00:00'),
('RE3041', 'Regio', 'București', '07:45:00', 'Pitești', '09:30:00'),
('IR4012', 'InterRegio', 'Pitești', '10:00:00', 'Sibiu', '13:30:00'),
('IR1420', 'InterRegio', 'București', '11:00:00', 'Constanța', '13:00:00'),
('RE2200', 'Regio', 'București', '06:00:00', 'Ploiești', '07:00:00'),
('RE2210', 'Regio', 'Ploiești', '07:30:00', 'Brașov', '09:15:00');
