package com.matela.production;

import com.matela.production.repository.BlockRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlockServiceTest {

    @Autowired
    private BlockRepository blockRepository ;

    @Test
    void testImportBlocksFromCsv()  {
        System.out.println(blockRepository.prixVolumique());
    }
}
