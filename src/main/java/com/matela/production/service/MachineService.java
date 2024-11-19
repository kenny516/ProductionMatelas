package com.matela.production.service;

import com.matela.production.entity.Machine;
import com.matela.production.repository.MachineRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MachineService {

    private final MachineRepository machineRepository;

    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }


    public List<Machine> findAll() {
        return machineRepository.findAll();
    }

    public Optional<Machine> findById(Long id) {
        return machineRepository.findById(id);
    }

    public Machine save(Machine machine) {
        return machineRepository.save(machine);
    }

    public void deleteById(Long id) {
        machineRepository.deleteById(id);
    }

    public Optional<Machine> updateNom(Long id, String nom) {
        return machineRepository.findById(id).map(machine -> {
            machine.setNom(nom);
            return machineRepository.save(machine);
        });
    }
    public void importMachinesFromCsv(MultipartFile file) throws IOException {
        List<Machine> machines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            // Utilisation de Apache Commons CSV pour parser le CSV
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()  // Utiliser la première ligne comme en-tête
                    .withIgnoreHeaderCase()     // Ignorer la casse des en-têtes
                    .withTrim()                 // Supprimer les espaces avant et après les valeurs
                    .parse(reader);

            // Parcourir chaque ligne du fichier CSV
            for (CSVRecord record : records) {
                Machine machine = new Machine();
                machine.setId(Long.parseLong(record.get("id").trim()));   // Récupérer l'id depuis la colonne "id"
                machine.setNom(record.get("nom").trim());                 // Récupérer le nom depuis la colonne "nom"

                System.out.println("id = " + machine.getId());
                System.out.println("nom = " + machine.getNom());

                machines.add(machine);  // Ajouter la machine à la liste
            }
        }

        // Sauvegarder toutes les machines dans la base de données
        machineRepository.saveAll(machines);
        System.out.println("Importation des machines réussie !");
    }
}
