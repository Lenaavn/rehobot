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
	public ResponseEntity<Pago> update(@PathVariable int idPago, @RequestBody Pago pago) {
	    if (!this.pagoService.existPago(idPago)) {
	        return ResponseEntity.notFound().build();
	    }

	    // Asegúrate de que el ID en el cuerpo coincide con el ID en la URL
	    if (idPago != pago.getId()) {
	        return ResponseEntity.badRequest().build();
	    }

	    // Obtener el pago existente para mantener la asociación con la cita
	    Pago pagoExistente = this.pagoService.findById(idPago).orElseThrow(() -> 
	            new IllegalArgumentException("Pago no encontrado con ID: " + idPago));

	    // Mantener la asociación con la cita existente
	    pago.setCita(pagoExistente.getCita());

	    // Guardar el pago actualizado
	    return ResponseEntity.ok(this.pagoService.save(pago));
	}	
	
	// metodo activar - desactivar pago que esta en pizzapedido

}
