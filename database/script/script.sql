CREATE DATABASE production_matelas;
\c production_matelas;

CREATE TABLE block
(
    id              SERIAL PRIMARY KEY,
    longueur        DECIMAL(10, 2),
    largeur         DECIMAL(10, 2),
    epaisseur       DECIMAL(10, 2),
    cout_production DECIMAL(10, 2),
    block_mere      INTEGER REFERENCES block (id) DEFAULT null,
    date_production TIMESTAMP                     DEFAULT CURRENT_TIMESTAMP
);


-- CREATE TABLE reste
-- (
--     id              SERIAL PRIMARY KEY,
--     block_id        INTEGER REFERENCES block (id),
--     longueur        DECIMAL(10, 2),
--     largeur         DECIMAL(10, 2),
--     epaisseur       DECIMAL(10, 2),
--     cout_production DECIMAL(10, 2),
--     date_creation   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
-- );

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