package com.reho.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reho.persistence.entities.Servicio;
import com.reho.persistence.repository.ServicioRepository;

@Service
public class ServicioService {
    
    @Autowired
    private ServicioRepository servicioRepository;
    
    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }
    
    public Optional<Servicio> findById(int id) {
        return servicioRepository.findById(id);
    }
    
    public Servicio create(Servicio servicio) {
        return servicioRepository.save(servicio);
    }
    
    public Servicio save(Servicio servicio) {
        return servicioRepository.save(servicio);
    }
    
    public boolean delete(int id) {
        if (servicioRepository.existsById(id)) {
            servicioRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public boolean existServicio(int id) {
        return servicioRepository.existsById(id);
    }
}