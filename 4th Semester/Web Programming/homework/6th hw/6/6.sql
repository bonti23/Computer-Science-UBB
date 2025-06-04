CREATE TABLE notebooks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(100) NOT NULL,
    producator VARCHAR(50) NOT NULL,
    procesor VARCHAR(50) NOT NULL,
    memorie INT NOT NULL,
    hdd INT NOT NULL,          -- capacitate HDD în GB
    placa_video VARCHAR(50) NOT NULL
);

INSERT INTO notebooks (model, producator, procesor, memorie, hdd, placa_video) VALUES
('Aspire 5', 'Acer', 'Intel Core i5', 8, 512, 'Intel UHD Graphics'),
('Swift 3', 'Acer', 'Intel Core i7', 16, 1000, 'NVIDIA MX350'),
('Pavilion 14', 'HP', 'Intel Core i5', 8, 256, 'Intel UHD Graphics'),
('Spectre x360', 'HP', 'Intel Core i7', 16, 512, 'Intel Iris Xe'),
('ThinkPad X1', 'Lenovo', 'Intel Core i5', 16, 1000, 'Intel UHD Graphics'),
('Yoga 7i', 'Lenovo', 'Intel Core i7', 32, 1000, 'NVIDIA GeForce MX450'),
('MacBook Air', 'Apple', 'Apple M1', 8, 256, 'Apple Integrated'),
('MacBook Pro', 'Apple', 'Apple M1', 16, 512, 'Apple Integrated');
