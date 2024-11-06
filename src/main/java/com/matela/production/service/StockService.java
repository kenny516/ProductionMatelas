package com.matela.production.service;

import com.matela.production.entity.Stock;
import com.matela.production.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private BlockService blockService;
    @Autowired
    private ProduitService produitService;

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(Long id) {
        return stockRepository.findById(id);
    }

    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Optional<Stock> updateStock(Long id, Stock stockDetails) {
        return stockRepository.findById(id).map(stock -> {
            stock.setBlock(stockDetails.getBlock());
            stock.setReste(stockDetails.getReste());
            return stockRepository.save(stock);
        });
    }

    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }






}
