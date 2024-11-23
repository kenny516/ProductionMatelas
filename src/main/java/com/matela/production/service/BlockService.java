package com.matela.production.service;

import com.matela.production.DTO.MachineDTO;
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
import java.math.BigDecimal;
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
    public void importBlocksFromCsv(List<AchatMatierePremier> achatMatierePremiercs,MultipartFile file) throws Exception {
        Formule formule = formuleService.findByFirst();
        try {
            String sql = generateQuery(achatMatierePremiercs,formule.getFormuleDetails(), file);
            // Exécuter la requête SQL avec EntityManager
            entityManager.createNativeQuery(sql).executeUpdate();
            for ( AchatMatierePremier achatMatierePremier:achatMatierePremiercs){
                achatmatierepremierService.updateDateQuantite(achatMatierePremier.getId(), achatMatierePremier.getQuantite());
            }
            System.out.println("Importation réussie avec une seule requête SQL !");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String generateQuery(List<AchatMatierePremier> achatMatierePremiers,List<FormuleDetail> formuleDetails, MultipartFile file) throws Exception {
        try (InputStreamReader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim().parse(reader);

            StringBuilder sortie = new StringBuilder("INSERT INTO sortie (id_achatmateriel, date_sortie, quantite) VALUES ");
            StringBuilder sqlBuilder = new StringBuilder("INSERT INTO block (nom, longueur, largeur, epaisseur, cout_production, cout_tehorique, volume, machine_id, block_mere, date_production) VALUES ");
            int count = 0;

            System.out.println("Commencement de la génération");

            for (CSVRecord record : records) {
                try {
                    System.out.println("Numero =" +count);
                    // Gestion des dates avec un format flexible
                    String dateStr = record.get("date_production").trim();
                    LocalDate dateCreation = LocalDate.parse(dateStr);

                    double longueur = parseDouble(record.get("longueur").trim());
                    double epaisseur = parseDouble(record.get("epaisseur").trim());
                    double largeur = parseDouble(record.get("largeur").trim());

                    double volume = longueur * epaisseur * largeur;
                    //double coutTheorique = cout_theorique_Groupe(formuleDetails, dateCreation, volume);
                    double coutTheorique = cout_theorique_GroupeUpdate(achatMatierePremiers,formuleDetails, dateCreation, volume);

//                    System.out.println("Coût théorique: " + coutTheorique);

                    //                            .append(record.get("id").trim()).append(", ") // ID
                    sqlBuilder.append("(")
//                            .append(record.get("id").trim()).append(", ") // ID
                            .append("'").append("block").append(count).append("', ") // Nom
                            .append(longueur).append(", ") // Longueur
                            .append(largeur).append(", ") // Largeur
                            .append(epaisseur).append(", ") // Épaisseur
                            .append(parseDouble(record.get("cout_production").trim())).append(", ") // Coût de production
                            .append(coutTheorique).append(", ") // Coût théorique
                            .append(volume).append(", ") // Volume
                            .append(record.get("machine_id").trim()).append(", ") // Machine ID
                            .append("NULL").append(", ") // Block mère
                            .append("'").append(dateCreation).append("'") // Date production
                            .append("),");

                    count++;
                } catch (Exception e) {
                    System.err.println("Erreur dans la ligne CSV :" + record.toString() + " - " + e.getMessage());
                }
            }
            System.out.println("Fin");

            if (count > 0) {
                sqlBuilder.setLength(sqlBuilder.length() - 1);
                sqlBuilder.append(";");
            } else {
                System.out.println("Le fichier CSV est vide ou mal formaté.");
                return "";
            }
            sortie.setLength(sortie.length() - 2);
            sortie.append(";");
            //entityManager.createNativeQuery(sortie.toString()).executeUpdate();
            return sqlBuilder.toString();
        } catch (Exception e) {
            System.err.println("Erreur lors de l'importation des données CSV.");
            throw new Exception(e.getMessage());
        }
    }

    public static LocalDate parseDate(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr);
            return date;
        } catch (Exception e) {
            System.err.println("Format de date invalide: " + dateStr);
            throw new IllegalArgumentException("Format de date invalide: " + dateStr);
        }
    }

    private double parseDouble(String value) {
        try {
            value = value.replaceAll("\\s", "");
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.err.println("Valeur numérique invalide: " + value);
            throw new IllegalArgumentException("Valeur numérique invalide: " + value);
        }
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

    public double cout_theorique_GroupeUpdate(List<AchatMatierePremier> achatMatierePremiers,List<FormuleDetail> formuleDetails, LocalDate dateCreation, double volume) throws Exception {
        double cout_theorique = 0.0;

        for (FormuleDetail formuleDetail : formuleDetails) {
            double necessaire = volume * formuleDetail.getQuantite();
            for (AchatMatierePremier quantiteActuelleAchatDTO : achatMatierePremiers) {
                if (quantiteActuelleAchatDTO.getMatierePremiere().getId().equals(formuleDetail.getMatierePremiereId()) && !quantiteActuelleAchatDTO.getDateAchat().isAfter(dateCreation)) {
                    double quantiteDisponible = quantiteActuelleAchatDTO.getQuantite();
                    if (quantiteDisponible > 0) {
                        double quantiteAUtiliser = Math.min(quantiteDisponible, necessaire);
                        cout_theorique += quantiteAUtiliser * quantiteActuelleAchatDTO.getPrixAchat();
                        necessaire -= quantiteAUtiliser;
                        quantiteActuelleAchatDTO.setQuantite(quantiteDisponible - quantiteAUtiliser);
                    }
                }
                if (necessaire <= 0) {
                    break;
                }
            }
            if (necessaire > 0) {
                throw new Exception("Pas assez de matière première pour la formule avec la matière première " + achatMatierePremiers.size());
            }
        }
        return cout_theorique;
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
            nameBuilder.append("Bloc ").append((char) ('A' + random.nextInt(26))).append(random.nextInt(1000));
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
            String csvLine = String.format(Locale.US,
                    "%d,%s,%.2f,%.2f,%.2f,%.2f,%.2f,%d,%s,%s", i, nameBuilder, longueur, largeur, epaisseur, coutProduction, volume, machineId, blockMere == null ? "" : blockMere, dateProduction.format(formatter));

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
            queryBuilder.append("INSERT INTO block (nom, longueur, largeur, epaisseur, volume, cout_production, machine_id) ").append("VALUES ('").append(nom).append("', ").append(longueur).append(", ").append(largeur).append(", ").append(epaisseur).append(", ").append(volume).append(", ").append(coutFinal).append(", ").append(machineId).append(");");

            // Sauvegarde en batch tous les 100 blocs pour optimiser les performances
            if (i % 100 == 0 && i > 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
        entityManager.createNativeQuery(queryBuilder.toString()).executeUpdate();
    }


    public List<MachineDTO> getMachineCosts() {
        List<Object[]> objects = blockRepository.findQuantiteActuelleParMachine();
        List<MachineDTO> dtos = new ArrayList<>();
        for (Object[] object : objects) {
            Long machineId = (Long) object[0];

            // Check and convert volume
            Object volumeObj = object[1];
            BigDecimal volumeBigDecimal = (volumeObj instanceof BigDecimal) ? (BigDecimal) volumeObj : new BigDecimal((Double) volumeObj);

            // Check and convert coutProduction
            Object coutProductionObj = object[2];
            BigDecimal coutProductionBigDecimal = (coutProductionObj instanceof BigDecimal) ? (BigDecimal) coutProductionObj : new BigDecimal((Double) coutProductionObj);

            // Check and convert coutTheorique
            Object coutTheoriqueObj = object[3];
            BigDecimal coutTheoriqueBigDecimal = (coutTheoriqueObj instanceof BigDecimal) ? (BigDecimal) coutTheoriqueObj : new BigDecimal((Double) coutTheoriqueObj);

            // Convert BigDecimal to double
            double volume = volumeBigDecimal.doubleValue();
            double coutProduction = coutProductionBigDecimal.doubleValue();
            double coutTheorique = coutTheoriqueBigDecimal.doubleValue();

            // Create DTO
            MachineDTO dto = new MachineDTO(machineId, volume, coutProduction, coutTheorique);
            dtos.add(dto);
        }
        return dtos;
    }
    public List<MachineDTO> getMachineCosts(int year) {
        List<Object[]> objects = blockRepository.findQuantiteActuelleParMachineByYear(year);
        List<MachineDTO> dtos = new ArrayList<>();
        for (Object[] object : objects) {
            Long machineId = (Long) object[0];

            // Check and convert volume
            Object volumeObj = object[1];
            BigDecimal volumeBigDecimal = (volumeObj instanceof BigDecimal) ? (BigDecimal) volumeObj : new BigDecimal((Double) volumeObj);

            // Check and convert coutProduction
            Object coutProductionObj = object[2];
            BigDecimal coutProductionBigDecimal = (coutProductionObj instanceof BigDecimal) ? (BigDecimal) coutProductionObj : new BigDecimal((Double) coutProductionObj);

            // Check and convert coutTheorique
            Object coutTheoriqueObj = object[3];
            BigDecimal coutTheoriqueBigDecimal = (coutTheoriqueObj instanceof BigDecimal) ? (BigDecimal) coutTheoriqueObj : new BigDecimal((Double) coutTheoriqueObj);

            // Convert BigDecimal to double
            double volume = volumeBigDecimal.doubleValue();
            double coutProduction = coutProductionBigDecimal.doubleValue();
            double coutTheorique = coutTheoriqueBigDecimal.doubleValue();

            // Create DTO
            MachineDTO dto = new MachineDTO(machineId, volume, coutProduction, coutTheorique);
            dtos.add(dto);
        }
        return dtos;
    }


}
