package com.reho.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reho.persistence.entities.Pago;
import com.reho.persistence.repository.PagoRepository;

@Service
public class PagoService {
	
	@Autowired
	private PagoRepository  pagoRepository;
	
	public List<Pago> findAll(){
		return this.pagoRepository.findAll();
	}
	
	public boolean existPago(int idPago){
		return this.pagoRepository.existsById(idPago);
	}
	
	public Optional<Pago> findById(int idPago){
		return this.pagoRepository.findById(idPago);
	}
	
	public Pago create(Pago pago) {
		return this.pagoRepository.save(pago);
	}
	
	public Pago save(Pago pago) {
		return this.pagoRepository.save(pago);
	}
	
	public boolean delete(int idPago) {
		boolean result = false;
		
		if(this.pagoRepository.existsById(idPago)) {
			this.pagoRepository.deleteById(idPago);
			result = true;
		}
		
		return result;
	}

}
