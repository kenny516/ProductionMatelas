package com.matela.production.service;

import com.matela.production.DTO.QuantiteActuelleAchatDTO;
import com.matela.production.entity.*;
import com.matela.production.repository.BlockRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class BlockService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private AchatmatierepremierService achatmatierepremierService;
    @Autowired
    private SortieService sortieService;
    @Autowired
    private FormuleService formuleService;
    @Autowired
    private StockService stockService;

    public BlockService(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }

    public List<Block> getAllBlocks() {
        return blockRepository.findAll();
    }

    public Block getBlockById(Long id) {
        return blockRepository.findById(id).orElse(null);
    }

    public Block createBlock(Block block) {
        return blockRepository.save(block);
    }

    public Optional<Block> updateBlock(Long id, Block blockDetails) {
        return blockRepository.findById(id).map(block -> {
            block.setLongueur(blockDetails.getLongueur());
            block.setLargeur(blockDetails.getLargeur());
            block.setEpaisseur(blockDetails.getEpaisseur());
            block.setCoutProduction(blockDetails.getCoutProduction());
            return blockRepository.save(block);
        });
    }

    public void deleteBlock(Long id) {
        blockRepository.deleteById(id);
    }

    public List<Block> getBlockValid() {
        return blockRepository.getValidBlock();
    }

    public Double coutProduction(Block block, Double volumeReste) {
        return volumeReste * block.getCoutProduction() / block.getVolume();
    }

    public List<Block> getAllResteBlock(Block block) {
        return blockRepository.getAllDescendants(block.getId());
    }

    public Block getLastFille(Block block) {
        return blockRepository.getLastFIlle(block.getId());
    }

    public Block getFirstFille(Block block) {
        Block block1 = blockRepository.getFirstFIlle(block.getId());
        return Objects.requireNonNullElse(block1, block);
    }

    public double UpdatePR(Block block) {
        List<Block> blockList = getAllResteBlock(block);
        double proportion = block.getCoutProduction() / blockList.getFirst().getCoutProduction();
        for (Block blockIter : blockList) {
            double prOld = blockIter.getCoutProduction();
            blockIter.setCoutProduction(prOld * proportion);
            updateBlock(blockIter.getId(), blockIter);
        }
        return proportion;
    }

    public Block getFirstParent(Block block) {
        return blockRepository.getFirstParent(block.getId());
    }


    @Transactional
    public void importBlocksFromCsv(MultipartFile file) {
        try {
            String sql = generateQuery(file);
            // Exécuter la requête SQL avec EntityManager
            entityManager.createNativeQuery(sql).executeUpdate();
            System.out.println("Importation réussie avec une seule requête SQL !");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'importation des données CSV.");
        }
    }

    public String generateQuery(MultipartFile file) {
        try (InputStreamReader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim()
                    .parse(reader);

            StringBuilder sqlBuilder = new StringBuilder("INSERT INTO block (id, nom, longueur, largeur, epaisseur, cout_production, volume, machine_id, block_mere, date_production) VALUES ");
            int count = 0;

            System.out.println("commencement de la generation");
            for (CSVRecord record : records) {
                LocalDate dateCreation = LocalDate.parse(record.get("date_production"));

                sqlBuilder.append("(")
                        .append(record.get("id").trim()).append(", ") // ID
                        .append("'").append(record.get("nom").trim().replace("'", "''")).append("', ") // Nom
                        .append(record.get("longueur").trim()).append(", ") // Longueur
                        .append(record.get("largeur").trim()).append(", ") // Largeur
                        .append(record.get("epaisseur").trim()).append(", ") // Épaisseur
                        .append(record.get("cout_production").trim()).append(", ") // Coût de production
                        .append(record.get("volume").trim()).append(", ") // Volume
                        .append(record.get("machine_id").trim()).append(", ") // Machine ID
                        .append(record.isSet("NULL")).append(", ") // Block mère
                        .append("'").append(record.get("date_production").trim()).append("'") // Date production
                        .append("),");

                count++;
            }
            System.out.println("fin");
            // Supprimer la dernière virgule
            if (count > 0) {
                sqlBuilder.setLength(sqlBuilder.length() - 1);
                sqlBuilder.append(";");
            } else {
                System.out.println("Le fichier CSV est vide ou mal formaté.");
                return "";
            }

            // Exécuter la requête SQL avec EntityManager
            System.out.println("requête SQL succes!");
            return sqlBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'importation des données CSV.");
        }
        return "";
    }


    public double prixRevientVolumique(int nbLine) {
        double prVolumique = 0;
        double volumeTotal = 0;
        List<Block> blocks = blockRepository.findBlocklimit(nbLine);
        for (Block block : blocks) {
            volumeTotal += block.getVolumeb();
            prVolumique += block.getCoutProduction();
        }
        return prVolumique / volumeTotal;
    }

    public double cout_tehorique(LocalDate dateCreation, double volume) throws Exception {
        double cout_tehorique = 0.0;
        Formule formule = formuleService.findByFirst();
        for (FormuleDetail formuleDetail : formule.getFormuleDetails()) {
            double necessaire = volume * formuleDetail.getQuantite();
            List<QuantiteActuelleAchatDTO> quantiteActuelleAchatDTOS = achatmatierepremierService.findByMatierePremiereCurrentQuantitiesDate(formuleDetail.getId(), dateCreation);
            for (QuantiteActuelleAchatDTO quantiteActuelleAchatDTO : quantiteActuelleAchatDTOS) {
                double sortieD = 0.0;
                double quantite = quantiteActuelleAchatDTO.getQuantiteActuelle();
                if (quantite < necessaire) {
                    sortieD+=quantite;
                    necessaire -= quantite;
                    cout_tehorique += quantite * quantiteActuelleAchatDTO.getPrixAchat();
                } else if (quantite >= necessaire) {
                    sortieD+=necessaire;
                    cout_tehorique += necessaire * quantiteActuelleAchatDTO.getPrixAchat();
                }
                Sortie sortie = new Sortie();
                sortie.setAchatMatierePremierId(quantiteActuelleAchatDTO.getIdAchat());
                sortie.setDateSortie(dateCreation);
                sortie.setQuantite(sortieD);
                sortieService.createSortie(sortie);
                if (quantite == necessaire){
                    break;
                }
            }
        }
        return cout_tehorique;
    }


    public void generateRandomBlocks(int numBlocks, double prixVolumique, long minMachineId, long maxMachineId) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<Block> blocks = new ArrayList<>(numBlocks);

        double minVariation = 0.9;
        double maxVariation = 1.1;

        StringBuilder nameBuilder = new StringBuilder();

        for (int i = 0; i < numBlocks; i++) {
            Block block = new Block();

            nameBuilder.setLength(0);
            nameBuilder.append("Bloc ").append((char) ('A' + random.nextInt(26)))
                    .append(random.nextInt(1000));
            block.setNom(nameBuilder.toString());

            double longueur = 1 + random.nextDouble() * 5;
            double largeur = 1 + random.nextDouble() * 5;
            double epaisseur = 0.5 + random.nextDouble() * 2;
            block.setLongueur(longueur);
            block.setLargeur(largeur);
            block.setEpaisseur(epaisseur);

            // Calcul du volume
            double volume = longueur * largeur * epaisseur;
            block.setVolumeb(volume);

            double coutProduction = prixVolumique * volume;
            double variation = minVariation + (random.nextDouble() * (maxVariation - minVariation));
            block.setCoutProduction(coutProduction * variation);

            long machineId = minMachineId + random.nextLong(maxMachineId - minMachineId + 1);
            block.setMachine(new Machine(machineId));

            blocks.add(block);

            if (blocks.size() == 100) {
                blockRepository.saveAll(blocks);
                blocks.clear();
            }
        }

        if (!blocks.isEmpty()) {
            blockRepository.saveAll(blocks);
        }
    }

    public void GenererCSV(int numBlocks, double prixVolumique, long minMachineId, long maxMachineId, String filePath) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<String> csvLines = new ArrayList<>(numBlocks);

        double minVariation = 0.9;
        double maxVariation = 1.1;

        StringBuilder nameBuilder = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

        for (int i = 1; i <= numBlocks; i++) {
            nameBuilder.setLength(0);
            nameBuilder.append("Bloc ").append((char) ('A' + random.nextInt(26))).append(random.nextInt(1000));

            double longueur = 1 + random.nextDouble() * 5;
            double largeur = 1 + random.nextDouble() * 5;
            double epaisseur = 0.5 + random.nextDouble() * 2;

            // Calcul du volume
            double volume = longueur * largeur * epaisseur;

            double coutProduction = prixVolumique * volume;
            double variation = minVariation + (random.nextDouble() * (maxVariation - minVariation));
            coutProduction *= variation;

            long machineId = minMachineId + random.nextLong(maxMachineId - minMachineId + 1);
            Long blockMere = null; // 30% chance of being null

            LocalDate dateProduction = LocalDate.now().minusDays(random.nextInt(30)); // Random date in the last 30 days

            // Ajouter une ligne CSV
            String csvLine = String.format(
                    Locale.US, // Force l'utilisation du point comme séparateur décimal
                    "%d,%s,%.2f,%.2f,%.2f,%.2f,%.2f,%d,%s,%s",
                    i,
                    nameBuilder,
                    longueur,
                    largeur,
                    epaisseur,
                    coutProduction,
                    volume,
                    machineId,
                    blockMere == null ? "" : blockMere,
                    dateProduction.format(formatter)
            );

            csvLines.add(csvLine);
        }

        // Écrire les données dans un fichier CSV
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("id,nom,longueur,largeur,epaisseur,cout_production,volume,machine_id,block_mere,date_production\n");
            for (String line : csvLines) {
                writer.write(line + "\n");
            }
            System.out.println("Fichier CSV généré : " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateRandomBlocksQueryNative(int numBlocks, double prixVolumique, long minMachineId, long maxMachineId) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        StringBuilder queryBuilder = new StringBuilder();

        double minVariation = 0.9;
        double maxVariation = 1.1;

        for (int i = 0; i < numBlocks; i++) {
            // Génération des valeurs aléatoires
            String nom = "Bloc " + (char) ('A' + random.nextInt(26)) + random.nextInt(1000);
            double longueur = 1 + random.nextDouble() * 5;
            double largeur = 1 + random.nextDouble() * 5;
            double epaisseur = 0.5 + random.nextDouble() * 2;
            double volume = longueur * largeur * epaisseur;
            double coutProduction = prixVolumique * volume;
            double variation = minVariation + (random.nextDouble() * (maxVariation - minVariation));
            double coutFinal = coutProduction * variation;
            long machineId = minMachineId + random.nextLong(maxMachineId - minMachineId + 1);

            // Construction de la requête SQL pour l'insertion directe
            queryBuilder.setLength(0);  // Réinitialisation du StringBuilder
            queryBuilder.append("INSERT INTO block (nom, longueur, largeur, epaisseur, volume, cout_production, machine_id) ")
                    .append("VALUES ('")
                    .append(nom).append("', ")
                    .append(longueur).append(", ")
                    .append(largeur).append(", ")
                    .append(epaisseur).append(", ")
                    .append(volume).append(", ")
                    .append(coutFinal).append(", ")
                    .append(machineId).append(");");

            // Sauvegarde en batch tous les 100 blocs pour optimiser les performances
            if (i % 100 == 0 && i > 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        entityManager.createNativeQuery(queryBuilder.toString()).executeUpdate();
    }


}
