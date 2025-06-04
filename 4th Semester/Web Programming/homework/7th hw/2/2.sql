CREATE TABLE IF NOT EXISTS produse (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nume VARCHAR(100) NOT NULL,
    descriere TEXT,
    pret DECIMAL(10,2) NOT NULL
);

INSERT INTO produse (nume, descriere, pret) VALUES
('Laptop Lenovo', 'Laptop performant cu procesor Intel i5 și 8GB RAM.', 2999.99),
('Monitor Samsung', 'Monitor LED 24 inch Full HD.', 799.50),
('Tastatură mecanică Redragon', 'Tastatură cu iluminare RGB și switch-uri blue.', 249.90),
('Mouse Logitech', 'Mouse wireless ergonomic.', 129.00),
('SSD Kingston 500GB', 'SSD SATA III pentru viteză rapidă.', 269.00),
('Router TP-Link', 'Router dual-band pentru conexiune stabilă.', 199.99),
('Căști JBL', 'Căști wireless cu bass puternic.', 349.99),
('Imprimantă HP', 'Multifuncțională cu Wi-Fi.', 449.00),
('Boxă portabilă Sony', 'Boxă Bluetooth cu autonomie mare.', 379.00),
('Memorie RAM Corsair 16GB', 'DDR4 3200MHz pentru gaming.', 399.00),
('Geantă laptop 15.6"', 'Geantă rezistentă și elegantă.', 99.99),
('Webcam Logitech HD', 'Camera web 1080p pentru conferințe.', 179.00);
