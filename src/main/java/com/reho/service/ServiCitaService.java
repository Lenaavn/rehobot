package com.reho.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reho.persistence.entities.ServiCita;
import com.reho.persistence.repository.ServiCitaRepository;

@Service
public class ServiCitaService {

    @Autowired
    private ServiCitaRepository serviCitaRepository;

    public List<ServiCita> findAll() {
        return serviCitaRepository.findAll();
    }

    public Optional<ServiCita> findById(int id) {
        return serviCitaRepository.findById(id);
    }

    public ServiCita create(ServiCita serviCita) {
        return serviCitaRepository.save(serviCita);
    }

    public ServiCita save(ServiCita serviCita) {
        return serviCitaRepository.save(serviCita);
    }

    public boolean delete(int id) {
        if (serviCitaRepository.existsById(id)) {
            serviCitaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existServiCita(int id) {
        return serviCitaRepository.existsById(id);
    }
}
