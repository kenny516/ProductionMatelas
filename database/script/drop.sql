-- Connect to the production_matelas database before running these commands
\c production_matelas;

-- Drop the tables in reverse order of creation to avoid dependency issues
DROP TABLE IF EXISTS stock CASCADE;
DROP TABLE IF EXISTS transformation CASCADE;
DROP TABLE IF EXISTS transformationDetail CASCADE;
DROP TABLE IF EXISTS produit CASCADE;
DROP TABLE IF EXISTS reste CASCADE;
DROP TABLE IF EXISTS block CASCADE;

-- -- Finally, drop the database
\c postgres;
DROP DATABASE IF EXISTS production_matelas;
