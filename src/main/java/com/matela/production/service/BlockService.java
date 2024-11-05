package com.matela.production.service;

import com.matela.production.model.Block;
import com.matela.production.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Block updateBlock(Long id, Block blockDetails) {
        Block block = blockRepository.findById(id).orElse(null);
        if (block != null) {
            block.setLongueur(blockDetails.getLongueur());
            block.setLargeur(blockDetails.getLargeur());
            block.setEpaisseur(blockDetails.getEpaisseur());
            block.setCoutProduction(blockDetails.getCoutProduction());
            return blockRepository.save(block);
        }
        return null;
    }

    public void deleteBlock(Long id) {
        blockRepository.deleteById(id);
    }
}
