CREATE OR REPLACE VIEW vue_quantite_actuelle_achat AS
SELECT
    a.id AS id_achat,
    a.matiere_premiere_id,
    (a.quantite - COALESCE(SUM(s.quantite), 0)) AS quantite_actuelle,
    a.prix_achat,
    a.date_achat
FROM achatMatierePremier a
         LEFT JOIN sortie s
                   ON a.id = s.id_achatMateriel
GROUP BY a.id, a.matiere_premiere_id, a.prix_achat, a.date_achat,a.quantite
HAVING (a.quantite - COALESCE(SUM(s.quantite), 0)) > 0;
