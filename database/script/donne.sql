INSERT INTO matierePremiereSimple (id, nom, quantite, prix_achat)
VALUES (1, 'Matiere A', 0.5, 10.00), -- 0.5 unité par volume 1
       (2, 'Matiere B', 0.3, 15.00), -- 0.3 unité par volume 0.6
       (3, 'Matiere C', 0.2, 20.00); -- 0.2 unité par volume 0.4


INSERT INTO matierePremiere (id, nom, prix_achat)
VALUES (1, 'Matiere A', 10.00), -- 0.5 unité par volume
       (2, 'Matiere B', 15.00), -- 0.3 unité par volume
       (3, 'Matiere C', 20.00); -- 0.2 unité par volume


INSERT INTO achatMatierePremier (id, matiere_premiere_id, quantite, prix_achat, date_achat)
VALUES (1, 1, 100, 9.50, '2024-11-01'),  -- Achat pour Matiere A
       (2, 1, 50, 10.00, '2024-11-10'),  -- Achat pour Matiere A
       (3, 2, 100, 14.00, '2024-11-01'), -- Achat pour Matiere B
       (4, 3, 50, 18.00, '2024-11-05'),  -- Achat pour Matiere C
       (5, 3, 100, 19.00, '2024-11-12'); -- Achat pour Matiere C

INSERT INTO machine (id, nom)
VALUES (1, 'Machine 1'),
       (2, 'Machine 2');

INSERT INTO block (id, nom, longueur, largeur, epaisseur, cout_production, volume, machine_id, block_mere, date_production)
VALUES (1, 'Bloc A', 2.0, 1.0, 1.0, 100, 2.0, 1, NULL, '2024-11-15'), -- Volume = 2
       (2, 'Bloc B', 1.0, 1.0, 1.0, 80, 1.0, 1, NULL, '2024-11-18'),  -- Volume = 1
       (3, 'Bloc C', 3.0, 1.0, 1.0, 120, 3.0, 2, NULL, '2024-11-20'); -- Volume = 3
