package com.matela.production;

import com.matela.production.repository.BlockRepository;
import com.matela.production.repository.MachineRepository;
import com.matela.production.service.BlockService;
import com.matela.production.service.MachineService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class BlockServiceTest {

    private final BlockRepository blockRepository = Mockito.mock(BlockRepository.class);
    private final BlockService blockService = new BlockService(blockRepository);
    private final MachineRepository machineRepository = Mockito.mock(MachineRepository.class);
    private final MachineService machineService = new MachineService(machineRepository);

    @Test
    void testImportBlocksFromCsv() throws IOException {
        // Étape 1 : Simuler un fichier CSV
        String csvContent = """
                id,nom,longueur,largeur,epaisseur,cout_production,volume,machine_id,blockMere,date_production
                1,Bloc S1,2.00,1.00,0.50,200.00,1.00,1,,2024-11-05
                2,Bloc S2,1.50,1.00,0.50,150.00,0.75,2,,2024-11-08
                3,Bloc R1,3.00,2.00,1.00,400.00,6.00,1,1,2024-11-12
                4,Bloc R2,3.50,2.50,1.20,500.00,7.00,2,3,2024-11-15
                """;

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "blocks.csv",
                "text/csv",
                csvContent.getBytes()
        );
        // Étape 2 : Appeler la méthode à tester
        blockService.importBlocksFromCsv(file);

        String csvContentMachine = """
                id,nom
                1,Machine 1
                2,Machine 2
                """;
        MockMultipartFile fileMachine = new MockMultipartFile(
                "file",
                "blocks.csv",
                "text/csv",
                csvContentMachine.getBytes()
        );

        //machineService.importMachinesFromCsv(fileMachine);

        blockService.generateRandomBlocks(1000000,5000,0,3);
        blockService.generateRandomBlocksQueryNative(1000000,5000,0,3);

        verify(machineRepository, times(1)).saveAll(anyList());
    }
}
