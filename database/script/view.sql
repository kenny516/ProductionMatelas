CREATE OR REPLACE VIEW vue_quantite_actuelle_achat AS
SELECT a.id                                        AS id_achat,
       a.matiere_premiere_id,
       (a.quantite - COALESCE(SUM(s.quantite), 0)) AS quantite_actuelle,
       a.prix_achat,
       a.date_achat
FROM achatMatierePremier a
         LEFT JOIN sortie s
                   ON a.id = s.id_achatMateriel
GROUP BY a.id, a.matiere_premiere_id, a.prix_achat, a.date_achat, a.quantite
HAVING (a.quantite - COALESCE(SUM(s.quantite), 0)) > 0
ORDER BY a.date_achat;

select * from vue_quantite_actuelle_achat;


CREATE OR REPLACE VIEW machineDashboard AS
(
SELECT
    machine_id,
    SUM(volume) AS volume,
    SUM(cout_production) AS cout_production,
    SUM(cout_tehorique) AS cout_theorique,
    EXTRACT(YEAR FROM date_production) AS production_year
FROM
    block
GROUP BY
    machine_id, EXTRACT(YEAR FROM date_production)
    );





select * from machineDashboard;


SELECT id_achat, matiere_premiere_id, quantite_actuelle,prix_achat,date_achat
FROM vue_quantite_actuelle_achat
WHERE matiere_premiere_id = 1
  AND date_achat <= '2024-11-11'
GROUP BY id_achat, matiere_premiere_id, date_achat, quantite_actuelle, prix_achat
HAVING SUM(quantite_actuelle) >= 45