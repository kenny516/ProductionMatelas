-- Insérer des données dans la table matierePremiere
INSERT INTO matierePremiere (id, nom, prix_achat)
VALUES (1, 'Essence', 120.50),
       (2, 'Papier', 15.30),
       (3, 'Durcisseur', 75.00);

-- Insérer des données dans la table formule
INSERT INTO formule (id, nom, description)
VALUES (1, 'Formule A', 'Production standard de blocs.');

-- Insérer des données dans la table formuleDetail
INSERT INTO formuleDetail (id, formule_id, matiere_premiere_id, quantite, unite)
VALUES (1, 1, 1, 10.00, 'Litres'),
       (2, 1, 2, 5.00, 'Kg'),
       (3, 1, 3, 2.50, 'Litres');


-- Insérer des données dans la table achatMatierePremier
INSERT INTO achatMatierePremier (id, matiere_premiere_id, quantite, prix_achat, date_achat)
VALUES (1, 1, 50.00, 125.00, '2024-11-01'),
       (2, 2, 100.00, 20.00, '2024-11-03'),
       (3, 3, 30.00, 80.00, '2024-11-05');

-- Insérer des données dans la table machine
INSERT INTO machine (id, nom)
VALUES (1, 'Machine A'),
       (2, 'Machine B'),
       (3, 'Machine B');

-- Insérer des données dans la table block
INSERT INTO block (id, nom, longueur, largeur, epaisseur, cout_production, volume, machine_id, block_mere,
                   date_production)
VALUES (1, 'Block A1', 2.00, 1.50, 0.50, 300.00, 1.50, 1, NULL, '2024-11-10'),
       (2, 'Block B1', 3.00, 2.00, 0.75, 450.00, 4.50, 2, NULL, '2024-11-11'),
       (3, 'Block A2', 1.00, 1.00, 0.25, 150.00, 0.25, 1, 1, '2024-11-15');
