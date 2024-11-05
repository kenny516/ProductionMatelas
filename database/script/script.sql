CREATE DATABASE production_matelas;
\c production_matelas;

CREATE TABLE block
(
    id              SERIAL PRIMARY KEY,
    longueur        DECIMAL(10, 2),
    largeur         DECIMAL(10, 2),
    epaisseur       DECIMAL(10, 2),
    cout_production DECIMAL(10, 2),
    date_production TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reste
(
    id              SERIAL PRIMARY KEY,
    block_id        INTEGER REFERENCES block (id),
    longueur        DECIMAL(10, 2),
    largeur         DECIMAL(10, 2),
    epaisseur       DECIMAL(10, 2),
    cout_production DECIMAL(10, 2),
    date_creation   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE produit
(
    id         SERIAL PRIMARY KEY,
    nom        VARCHAR(100),
    longueur   DECIMAL(10, 2),
    largeur    DECIMAL(10, 2),
    epaisseur  DECIMAL(10, 2),
    prix_vente DECIMAL(10, 2)
);

CREATE TABLE transformation
(
    id                  SERIAL PRIMARY KEY,
    block_id            INTEGER REFERENCES block (id),
    produit_id          INTEGER REFERENCES produit (id),
    quantite            INTEGER,
    date_transformation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    prix_revient        DECIMAL(10, 2)
);

CREATE TABLE stock
(
    id              SERIAL PRIMARY KEY,
    block_id        INTEGER REFERENCES block (id),
    reste_id        INTEGER REFERENCES reste (id) DEFAULT NULL,
    date_inventaire TIMESTAMP                     DEFAULT CURRENT_TIMESTAMP
);
