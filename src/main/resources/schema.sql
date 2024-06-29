CREATE TABLE IF NOT EXISTS USERS (
                       ID INT AUTO_INCREMENT PRIMARY KEY,
                       USERNAME VARCHAR(50) NOT NULL,
                       PASSWORD VARCHAR(50) NOT NULL,
                       EMAIL VARCHAR(50) NOT NULL
);