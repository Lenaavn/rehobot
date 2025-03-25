package com.reho.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping
	public ResponseEntity<Pago> create(@RequestBody Pago pago){
		return ResponseEntity.ok(this.pagoService.create(pago));
	}
	
	@PutMapping("/{idPago}")
	public ResponseEntity<Pago> update(@PathVariable int idPago, @RequestBody Pago pago){
		if(this.pagoService.existPago(idPago)) {
			return ResponseEntity.ok(this.pagoService.save(pago));
		}
		
		if(idPago != pago.getId()) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{idPago}")
	public ResponseEntity<Pago> delete(@PathVariable int idPago){
		if(this.pagoService.delete(idPago)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}	
	
	// metodo activar - desactivar pago que esta en pizzapedido

}
