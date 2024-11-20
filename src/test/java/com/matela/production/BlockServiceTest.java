package com.matela.production;

import com.matela.production.repository.BlockRepository;
import com.matela.production.repository.MachineRepository;
import com.matela.production.service.BlockService;
import com.matela.production.service.FormuleService;
import com.matela.production.service.MachineService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

class BlockServiceTest {

    private final BlockRepository blockRepository = Mockito.mock(BlockRepository.class);
    private final BlockService blockService = new BlockService(blockRepository);
    private final MachineRepository machineRepository = Mockito.mock(MachineRepository.class);
    private final MachineService machineService = new MachineService(machineRepository);
    private final FormuleService formuleService = Mockito.mock(FormuleService.class);

    @Test
    void testImportBlocksFromCsv() throws IOException {
        // Étape 1 : Simuler un fichier CSV
        String csvContent = """
                id,nom,longueur,largeur,epaisseur,cout_production,volume,machine_id,block_mere,date_production
                1, 'Block A1', 2.00, 1.50, 0.50, 300.00, 1.50, 1, NULL,2024-11-10
                """;

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "blocks.csv",
                "text/csv",
                csvContent.getBytes()
        );
        // Étape 2 : Appeler la méthode à tester
       // blockService.generateQuery(file);

//        String csvContentMachine = """
//                id,nom
//                1,Machine 1
//                2,Machine 2
//                """;
//        MockMultipartFile fileMachine = new MockMultipartFile(
//                "file",
//                "blocks.csv",
//                "text/csv",
//                csvContentMachine.getBytes()
//        );
//
//        //machineService.importMachinesFromCsv(fileMachine);
//
//        blockService.generateRandomBlocks(1000000, 5000, 0, 3);
//        blockService.generateRandomBlocksQueryNative(1000000, 5000, 0, 3);
//
//        verify(machineRepository, times(1)).saveAll(anyList());
    }
}
