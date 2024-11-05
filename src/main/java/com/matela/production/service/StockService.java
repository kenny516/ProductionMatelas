package com.matela.production.service;

import com.matela.production.model.Stock;
import com.matela.production.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock getStockById(Long id) {
        return stockRepository.findById(id).orElse(null);
    }

    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock updateStock(Long id, Stock stockDetails) {
        Stock stock = stockRepository.findById(id).orElse(null);
        if (stock != null) {
            stock.setBlock(stockDetails.getBlock());
            stock.setReste(stockDetails.getReste());
            return stockRepository.save(stock);
        }
        return null;
    }

    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }
}
