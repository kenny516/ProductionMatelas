CREATE OR REPLACE FUNCTION generate_random_blocks(num_blocks INT)
    RETURNS void AS $$
DECLARE
    i INT;
    random_nom VARCHAR(100);
    random_longueur DECIMAL(10, 2);
    random_largeur DECIMAL(10, 2);
    random_epaisseur DECIMAL(10, 2);
    random_cout_production DECIMAL(10, 2);
    random_volume DECIMAL(10, 2);
    random_machine_id INT;
BEGIN
    FOR i IN 1..num_blocks LOOP
            -- Générer un nom aléatoire de bloc
            random_nom := 'Bloc ' || chr(65 + floor(random() * 26)::int);  -- Exemple : Bloc A, Bloc B, ...

            -- Générer des dimensions et des coûts aléatoires
            random_longueur := round(1 + (random() * 5), 2);  -- Longueur entre 1 et 6
            random_largeur := round(1 + (random() * 5), 2);   -- Largeur entre 1 et 6
            random_epaisseur := round(0.5 + (random() * 2), 2);  -- Epaisseur entre 0.5 et 2.5
            random_cout_production := round(50 + (random() * 500), 2);  -- Coût entre 50 et 550

            -- Calculer le volume
            random_volume := random_longueur * random_largeur * random_epaisseur;

            -- Générer un ID de machine aléatoire
            random_machine_id := floor(random() * 10) + 1;  -- Machine ID entre 1 et 10 (ajustez en fonction de vos données)

            -- Insérer les données aléatoires dans la table block
            INSERT INTO block (nom, longueur, largeur, epaisseur, cout_production, volume, machine_id)
            VALUES (random_nom, random_longueur, random_largeur, random_epaisseur, random_cout_production, random_volume, random_machine_id);
        END LOOP;
END;
$$ LANGUAGE plpgsql;
