package com.matela.production.service;

import com.matela.production.entity.Block;
import com.matela.production.entity.Machine;
import com.matela.production.repository.BlockRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class BlockService {

    @Autowired
    private BlockRepository blockRepository;

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

    public void importBlocksFromCsv(MultipartFile file) {
        try (InputStreamReader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim()
                    .parse(reader);

            int batchSize = 1;
            int count = 0;

            for (CSVRecord record : records) {
                Long id = Long.parseLong(record.get("id").trim());
                String nom = record.get("nom").trim();
                Double longueur = Double.parseDouble(record.get("longueur").trim());
                Double largeur = Double.parseDouble(record.get("largeur").trim());
                Double epaisseur = Double.parseDouble(record.get("epaisseur").trim());
                Double coutProduction = Double.parseDouble(record.get("cout_production").trim());
                Double volume = Double.parseDouble(record.get("volume").trim());
                Long machine = Long.parseLong(record.get("machine_id").trim());
                Long blockMere = record.isSet("block_mere") && !record.get("block_mere").trim().isEmpty()
                        ? Long.parseLong(record.get("block_mere").trim())
                        : null;
                String dateProduction = record.get("date_production").trim();

                blockRepository.insertBlock(id, nom, longueur, largeur, epaisseur, coutProduction, volume, machine, blockMere, dateProduction);

                if (++count % batchSize == 0) {
                    System.out.println("id = " + id);
                    System.out.println("nom = " + nom);
                    System.out.println("longueur = " + longueur);
                    System.out.println("largeur = " + largeur);
                    System.out.println("epaisseur = " + epaisseur);
                    System.out.println("coutProduction = " + coutProduction);
                    System.out.println("volume = " + volume);
                    System.out.println("machine = " + machine);
                    System.out.println("blockMere = " + blockMere);
                    System.out.println("dateProduction = " + dateProduction);
                }
            }

            System.out.println("Importation réussie avec requêtes natives !");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'importation des données CSV.");
        }
    }


    public double prixRevientVolumique(int nbLine){
        double prVolumique = 0;
        double volumeTotal = 0;
        List<Block> blocks = blockRepository.findBlocklimit(nbLine);
        for (Block block : blocks) {
            volumeTotal += block.getVolumeb();
            prVolumique += block.getCoutProduction();
        }
        return prVolumique/volumeTotal;
    }


    public void generateRandomBlocks(int numBlocks) {
        Random random = new Random();
        List<Block> blocks = new ArrayList<>(numBlocks);

        for (int i = 0; i < numBlocks; i++) {
            // Générer des données aléatoires pour chaque bloc
            Block block = new Block();
            block.setNom("Bloc " + (char) ('A' + random.nextInt(26)));
            block.setLongueur(1 + random.nextDouble() * 5);
            block.setLargeur(1 + random.nextDouble() * 5);
            block.setEpaisseur(0.5 + random.nextDouble() * 2);
            block.setCoutProduction(50 + random.nextDouble() * 500);
            block.setVolumeb(block.getLongueur() * block.getLargeur() * block.getEpaisseur());
            block.setMachine(new Machine(1 + random.nextLong(10)));
            blocks.add(block);

            if (blocks.size() == 100) {
                blockRepository.saveAll(blocks);
                blocks.clear();
            }

        if (!blocks.isEmpty()) {
            blockRepository.saveAll(blocks);
        }
    }


}
