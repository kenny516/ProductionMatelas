package com.matela.production.service;


import com.matela.production.entity.Teta;
import com.matela.production.repository.TetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TetaService {
    @Autowired
    TetaRepository tetaRepository;

    public void createTeta(Teta teta) {
        tetaRepository.save(teta);
    }

    public Teta getTetaById(Long id) {
        return tetaRepository.findById(id).orElse(null);
    }

    public List<Teta> getALl(){
        return tetaRepository.findAll();
    }

    public void deleteTeta(Long id) {
        tetaRepository.deleteById(id);
    }

    public Teta updateTeta(Teta teta) {
        return tetaRepository.save(teta);
    }

    public Teta getTetaByValue(Double value) {
        return tetaRepository.findByValue(value);
    }


}
