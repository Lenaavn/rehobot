package com.reho.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reho.persistence.entities.Pago;
import com.reho.service.PagoService;

@RestController
@RequestMapping("/pagos")
public class PagoController {
	
	@Autowired
	private PagoService pagoService;
	
	@GetMapping
	public ResponseEntity<List<Pago>> list(){
		return ResponseEntity.ok(this.pagoService.findAll());
	}
	
	@GetMapping("/{idPago}")
	public ResponseEntity<Pago> findById(@PathVariable int idPago) {
		if(this.pagoService.existPago(idPago)) {
			return ResponseEntity.ok(this.pagoService.findById(idPago).get());
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PutMapping("/{idPago}")
	// ResponseEntity<?> para permitir diferentes tipos de respuesta
	public ResponseEntity<?> update(@PathVariable int idPago, @RequestBody Pago pago) {
	    if (!this.pagoService.existPago(idPago)) {
	        return ResponseEntity.notFound().build();
	    }

	    if (idPago != pago.getId()) {
	        return ResponseEntity.badRequest().body("El ID de la URL no coincide con el ID del cuerpo del pago.");
	    }

	    Pago pagoExistente = this.pagoService.findById(idPago).orElseThrow(() -> 
	            new IllegalArgumentException("Pago no encontrado con ID: " + idPago));

	    // Mantener la asociación con la cita existente
	    pago.setCita(pagoExistente.getCita());

	    return ResponseEntity.ok(this.pagoService.save(pago));
	}	
	
	// metodo activar - desactivar pago que esta en pizzapedido

}
