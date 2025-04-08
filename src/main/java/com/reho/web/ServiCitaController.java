package com.reho.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.reho.persistence.entities.ServiCita;
import com.reho.service.ServiCitaService;

@RestController
@RequestMapping("/servicitas")
public class ServiCitaController {

    @Autowired
    private ServiCitaService serviCitaService;

    @GetMapping
    public ResponseEntity<List<ServiCita>> list() {
        return ResponseEntity.ok(this.serviCitaService.findAll());
    }

    @GetMapping("/{idServiCita}")
    public ResponseEntity<ServiCita> findById(@PathVariable int id) {
        Optional<ServiCita> serviCita = this.serviCitaService.findById(id);
        return serviCita.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServiCita> create(@RequestBody ServiCita serviCita) {
        return ResponseEntity.ok(this.serviCitaService.create(serviCita));
    }

    @PutMapping("/{idServiCita}")
    // ResponseEntity<?> para permitir diferentes tipos de respuesta
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody ServiCita serviCita) {
        if (id != serviCita.getId()) {
            return ResponseEntity.badRequest().body("El ID de la URL no coincide con el ID del cuerpo del serviCita.");
        }
        if (!this.serviCitaService.existServiCita(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.serviCitaService.save(serviCita));
    }

    @DeleteMapping("/{idServiCita}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (this.serviCitaService.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
