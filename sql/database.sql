DROP DATABASE IF EXISTS Onskeskyen;
CREATE DATABASE Onskeskyen;
USE Onskeskyen;

CREATE TABLE bruger
(
    bruger_id INT AUTO_INCREMENT PRIMARY KEY,
    navn      VARCHAR(100) NOT NULL,
    email     VARCHAR(50)  NOT NULL,
    kodeord   VARCHAR(100) NOT NULL,
    dato      DATE NULL
);

CREATE TABLE kategori
(
    kategori_id INT AUTO_INCREMENT PRIMARY KEY,
    navn        VARCHAR(100) NULL
);

CREATE TABLE produkt
(
    produkt_id   INT AUTO_INCREMENT PRIMARY KEY,
    kategori_id  INT NULL,
    navn         VARCHAR(100) NULL,
    beskrivelse  VARCHAR(100) NULL,
    pris         DECIMAL(10, 2) NULL,
    produkt_link VARCHAR(100) NULL,
    billede_link VARCHAR(100) NULL,
    CONSTRAINT produkt_ibfk_1
        FOREIGN KEY (kategori_id) REFERENCES kategori (kategori_id)
);

CREATE INDEX kategori_id
    ON produkt (kategori_id);

CREATE TABLE ønskeliste
(
    ønskeliste_id  INT AUTO_INCREMENT PRIMARY KEY,
    ejer_bruger_id INT NULL,
    titel          VARCHAR(100) NOT NULL,
    beskrivelse    VARCHAR(100) NULL,
    offentlig      TINYINT(1) NULL,
    delingskode    VARCHAR(50) NULL,
    delingslink    VARCHAR(50) NULL,
    dato           DATE NULL,
    CONSTRAINT ønskeliste_ibfk_1
        FOREIGN KEY (ejer_bruger_id) REFERENCES bruger (bruger_id)
);

CREATE INDEX ejer_bruger_id
    ON ønskeliste (ejer_bruger_id);

CREATE TABLE ønskeliste_item
(
    ønskeliste_item_id INT AUTO_INCREMENT PRIMARY KEY,
    ønskeliste_id      INT NULL,
    produkt_id         INT NULL,
    note               VARCHAR(100) NULL,
    prioritet          INT NULL,
    antal              INT NULL,
    købt               TINYINT(1) NULL,
    dato               DATE NULL,
    CONSTRAINT ønskeliste_item_ibfk_1
        FOREIGN KEY (ønskeliste_id) REFERENCES ønskeliste (ønskeliste_id),
    CONSTRAINT ønskeliste_item_ibfk_2
        FOREIGN KEY (produkt_id) REFERENCES produkt (produkt_id)
);

CREATE INDEX produkt_id
    ON ønskeliste_item (produkt_id);

CREATE INDEX ønskeliste_id
    ON ønskeliste_item (ønskeliste_id);

CREATE TABLE reservation
(
    reservation_id     INT AUTO_INCREMENT PRIMARY KEY,
    ønskeliste_item_id INT NULL,
    bruger_id          INT NULL,
    antal              INT NULL,
    købt               TINYINT(1) NULL,
    dato               DATE NULL,
    CONSTRAINT reservation_ibfk_1
        FOREIGN KEY (ønskeliste_item_id) REFERENCES ønskeliste_item (ønskeliste_item_id),
    CONSTRAINT reservation_ibfk_2
        FOREIGN KEY (bruger_id) REFERENCES bruger (bruger_id)
);

CREATE INDEX bruger_id
    ON reservation (bruger_id);

CREATE INDEX ønskeliste_item_id
    ON reservation (ønskeliste_item_id);