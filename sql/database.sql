CREATE DATABASE IF NOT EXISTS Onskeskyen;

USE Onskeskyen;

DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS ønske;
DROP TABLE IF EXISTS ønskeliste;
DROP TABLE IF EXISTS bruger;

CREATE TABLE IF NOT EXISTS bruger
(
    bruger_id      INT AUTO_INCREMENT PRIMARY KEY,
    navn           VARCHAR(100) NOT NULL,
    email          VARCHAR(255) NOT NULL UNIQUE,
    kodeord        VARCHAR(100) NOT NULL,
    oprettet_dato  DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ønskeliste
(
    ønskeliste_id   INT AUTO_INCREMENT PRIMARY KEY,
    ejer_bruger_id  INT NOT NULL,
    titel           VARCHAR(100) NOT NULL,
    beskrivelse     TEXT NULL,
    offentlig       TINYINT(1) DEFAULT 1,
    delingslink     VARCHAR(1000) NULL,
    oprettet_dato   DATETIME DEFAULT CURRENT_TIMESTAMP,

    INDEX idx_onskeliste_ejer_bruger_id (ejer_bruger_id),

    CONSTRAINT fk_onskeliste_bruger
        FOREIGN KEY (ejer_bruger_id) REFERENCES bruger (bruger_id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS ønske
(
    ønske_id        INT AUTO_INCREMENT PRIMARY KEY,
    ønskeliste_id   INT NOT NULL,
    navn            VARCHAR(255) NOT NULL,
    beskrivelse     TEXT NULL,
    pris            DECIMAL(10,2) NULL,
    produkt_link    VARCHAR(1000) NULL,
    billede_link    VARCHAR(1000) NULL,
    købt            TINYINT(1) DEFAULT 0,
    dato            DATETIME DEFAULT CURRENT_TIMESTAMP,

    INDEX idx_onske_onskeliste_id (ønskeliste_id),

    CONSTRAINT fk_onske_onskeliste
        FOREIGN KEY (ønskeliste_id) REFERENCES ønskeliste (ønskeliste_id)
            ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reservation
(
    reservation_id  INT AUTO_INCREMENT PRIMARY KEY,
    ønske_id        INT NOT NULL UNIQUE,
    bruger_id       INT NOT NULL,
    dato            DATETIME DEFAULT CURRENT_TIMESTAMP,

    INDEX idx_reservation_onske_id (ønske_id),
    INDEX idx_reservation_bruger_id (bruger_id),

    CONSTRAINT fk_reservation_onske
        FOREIGN KEY (ønske_id) REFERENCES ønske (ønske_id)
            ON DELETE CASCADE,

    CONSTRAINT fk_reservation_bruger
        FOREIGN KEY (bruger_id) REFERENCES bruger (bruger_id)
            ON DELETE CASCADE
);

SELECT * FROM bruger;