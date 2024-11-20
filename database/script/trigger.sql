CREATE OR REPLACE FUNCTION calculate_volume()
    RETURNS TRIGGER AS
$$
BEGIN
    -- Calculer le volume uniquement si le volume est NULL
    IF NEW.volume IS NULL THEN
        NEW.volume := NEW.longueur * NEW.largeur * NEW.epaisseur;
    END IF;

    -- Retourner la nouvelle ligne pour permettre l'insertion
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER trigger_calculate_volume
    BEFORE INSERT
    ON block
    FOR EACH ROW
EXECUTE FUNCTION calculate_volume();



-- au cas ou
CREATE OR REPLACE FUNCTION calculate_volume()
    RETURNS TRIGGER AS
$$
BEGIN
    -- Calculer le volume uniquement si les dimensions changent
    IF TG_OP = 'INSERT' OR (NEW.longueur IS DISTINCT FROM OLD.longueur
        OR NEW.largeur IS DISTINCT FROM OLD.largeur
        OR NEW.epaisseur IS DISTINCT FROM OLD.epaisseur) THEN
        NEW.volume := NEW.longueur * NEW.largeur * NEW.epaisseur;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_calculate_volume
    BEFORE INSERT OR UPDATE
    ON block
    FOR EACH ROW
EXECUTE FUNCTION calculate_volume();
