DROP DATABASE production_matelas;
CREATE DATABASE production_matelas;
\c production_matelas;


-- Table des matières premières
CREATE TABLE matierePremiere
(
    id         SERIAL PRIMARY KEY,
    nom        VARCHAR(100)   NOT NULL,
    prix_achat DECIMAL(10, 2) NOT NULL
);

-- Table des formules
CREATE TABLE formule
(
    id          SERIAL PRIMARY KEY,
    nom         VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE formuleDetail
(
    id                  SERIAL PRIMARY KEY,
    formule_id          INTEGER REFERENCES formule (id) ON DELETE CASCADE,
    matiere_premiere_id INTEGER REFERENCES matierePremiere (id) ON DELETE CASCADE,
    quantite            DECIMAL(10, 2) NOT NULL,
    unite               VARCHAR(50)    NOT NULL
);

CREATE TABLE matierePremiereSimple
(
    id         SERIAL PRIMARY KEY,
    nom        VARCHAR(100)   NOT NULL,
    quantite   DECIMAL(10, 2),
    prix_achat DECIMAL(10, 2) NOT NULL
);


CREATE TABLE achatMatierePremier
(
    id                  SERIAL PRIMARY KEY,
    matiere_premiere_id INTEGER REFERENCES matierePremiere (id) ON DELETE CASCADE,
    quantite            DECIMAL(10, 2) NOT NULL,
    prix_achat          DECIMAL(10, 2) NOT NULL,
    date_achat          DATE DEFAULT CURRENT_DATE
);

CREATE TABLE sortie
(
    id               SERIAL PRIMARY KEY,
    id_achatMateriel INTEGER REFERENCES achatMatierePremier,
    quantite         DECIMAL(10, 2),
    date_sortie      DATE DEFAULT CURRENT_DATE
);


CREATE TABLE machine
(
    id  SERIAL PRIMARY KEY,
    nom VARCHAR(100)
);

CREATE TABLE block
(
    id              SERIAL PRIMARY KEY,
    nom             VARCHAR(100),
    longueur        DECIMAL(10, 2),
    largeur         DECIMAL(10, 2),
    epaisseur       DECIMAL(10, 2),
    cout_production DECIMAL(10, 2),
    volume          DECIMAL(10, 2),
    machine_id      INTEGER REFERENCES machine (id) NOT NULL,
    block_mere      INTEGER REFERENCES block (id) DEFAULT null,
    date_production DATE                          DEFAULT CURRENT_DATE
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
    reste_id            INTEGER REFERENCES block (id),
    date_transformation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transformationDetail
(
    id                SERIAL PRIMARY KEY,
    transformation_id INTEGER REFERENCES transformation (id),
    produit_id        INTEGER REFERENCES produit (id),
    quantite          INTEGER,
    prix_revient      DECIMAL(10, 2)
);



CREATE TABLE stock
(
    id              SERIAL PRIMARY KEY,
    block_id        INTEGER REFERENCES block (id) UNIQUE,
    longueur        DECIMAL(10, 2),
    largeur         DECIMAL(10, 2),
    epaisseur       DECIMAL(10, 2),
    cout_production DECIMAL(10, 2),
    date_inventaire TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- news table
CREATE TABLE mvt_stock_produit
(
    id                SERIAL PRIMARY KEY,
    produit_id        INTEGER REFERENCES produit (id),
    entre             INTEGER   DEFAULT NULL,
    sortie            INTEGER   DEFAULT NULL,
    prix_revient      DECIMAL(10, 2),
    prix_vente        DECIMAL(10, 2),
    date_modifiaction TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE teta
(
    id    SERIAL PRIMARY KEY,
    value DECIMAL(10, 2)
);


-- CREATE OR REPLACE VIEW stock_view AS
-- SELECT s.*
-- FROM stock s
--          JOIN (SELECT block_id, MAX(date_inventaire) AS latest_date
--                FROM stock
--                GROUP BY block_id) latest_stock ON s.block_id = latest_stock.block_id
--     AND s.date_inventaire = latest_stock.latest_date;


SELECT *
FROM block
WHERE id not in (SELECT block_mere
                 FROM block);

