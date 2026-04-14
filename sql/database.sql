CREATE DATABASE IF NOT EXISTS Onskeskyen
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE Onskeskyen;

CREATE TABLE IF NOT EXISTS bruger
(
    bruger_id INT AUTO_INCREMENT PRIMARY KEY,
    navn      VARCHAR(100) NOT NULL,
    email     VARCHAR(50)  NOT NULL,
    kodeord   VARCHAR(100) NOT NULL,
    oprettet_dato      DATETIME NULL
);

CREATE TABLE IF NOT EXISTS kategori
(
    kategori_id INT AUTO_INCREMENT PRIMARY KEY,
    navn        VARCHAR(100) NULL
);

CREATE TABLE IF NOT EXISTS produkt
(
    produkt_id   INT AUTO_INCREMENT PRIMARY KEY,
    kategori_id  INT NULL,
    navn         VARCHAR(100) NULL,
    beskrivelse  VARCHAR(100) NULL,
    pris         DECIMAL(10, 2) NULL,
    produkt_link VARCHAR(100) NULL,
    billede_link VARCHAR(100) NULL,
    INDEX idx_produkt_kategori_id (kategori_id),
    CONSTRAINT fk_produkt_kategori
        FOREIGN KEY (kategori_id) REFERENCES kategori (kategori_id)
);

CREATE TABLE IF NOT EXISTS ønskeliste
(
    ønskeliste_id  INT AUTO_INCREMENT PRIMARY KEY,
    ejer_bruger_id INT NULL,
    titel          VARCHAR(100) NOT NULL,
    beskrivelse    VARCHAR(100) NULL,
    offentlig      TINYINT(1) NULL,
    delingskode    VARCHAR(50) NULL,
    delingslink    VARCHAR(50) NULL,
    dato           DATE NULL,
    INDEX idx_onskeliste_ejer_bruger_id (ejer_bruger_id),
    CONSTRAINT fk_onskeliste_bruger
        FOREIGN KEY (ejer_bruger_id) REFERENCES bruger (bruger_id)
);

CREATE TABLE IF NOT EXISTS ønskeliste_item
(
    ønskeliste_item_id INT AUTO_INCREMENT PRIMARY KEY,
    ønskeliste_id      INT NULL,
    produkt_id         INT NULL,
    note               VARCHAR(100) NULL,
    prioritet          INT NULL,
    antal              INT NULL,
    købt               TINYINT(1) NULL,
    dato               DATE NULL,
    INDEX idx_onskeliste_item_onskeliste_id (ønskeliste_id),
    INDEX idx_onskeliste_item_produkt_id (produkt_id),
    CONSTRAINT fk_onskeliste_item_onskeliste
        FOREIGN KEY (ønskeliste_id) REFERENCES ønskeliste (ønskeliste_id),
    CONSTRAINT fk_onskeliste_item_produkt
        FOREIGN KEY (produkt_id) REFERENCES produkt (produkt_id)
);

CREATE TABLE IF NOT EXISTS reservation
(
    reservation_id     INT AUTO_INCREMENT PRIMARY KEY,
    ønskeliste_item_id INT NULL,
    bruger_id          INT NULL,
    antal              INT NULL,
    købt               TINYINT(1) NULL,
    dato               DATE NULL,
    INDEX idx_reservation_onskeliste_item_id (ønskeliste_item_id),
    INDEX idx_reservation_bruger_id (bruger_id),
    CONSTRAINT fk_reservation_onskeliste_item
        FOREIGN KEY (ønskeliste_item_id) REFERENCES ønskeliste_item (ønskeliste_item_id),
    CONSTRAINT fk_reservation_bruger
        FOREIGN KEY (bruger_id) REFERENCES bruger (bruger_id)
);
