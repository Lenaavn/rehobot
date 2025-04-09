package com.reho.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reho.persistence.entities.Usuario;
import com.reho.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins="http://localhost:4200/") //si da error a la larga se borra
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<Usuario>> list() {
		return ResponseEntity.ok(this.usuarioService.findAll());
	}

	@GetMapping("/{idUsuario}")
	public ResponseEntity<Usuario> findById(@PathVariable int idUsuario) {
		if (this.usuarioService.existsUsuario(idUsuario)) {
			return ResponseEntity.ok(this.usuarioService.findById(idUsuario).get());
		}

		return ResponseEntity.notFound().build();

	}

	@PostMapping
	public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
		return ResponseEntity.ok(this.usuarioService.create(usuario));
	}

	@PutMapping("/{idUsuario}")
	// ResponseEntity<?> para permitir diferentes tipos de respuesta
	public ResponseEntity<?> update(@PathVariable int idUsuario, @RequestBody Usuario usuario) {
	    if (idUsuario != (usuario.getId())) {
	        return ResponseEntity.badRequest().body("El ID de la URL no coincide con el ID del cuerpo del usuario.");
	    }
	    
	    if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
	    	return ResponseEntity.badRequest().body("El atributo 'nombre' no puede estar vacío ni ser nulo para actualizar el usuario.");
	    }
	    
	    if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
	    	return ResponseEntity.badRequest().body("El atributo 'email' no puede estar vacío ni ser nulo para actualizar el usuario.");
	    }
	    
	    if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
	    	return ResponseEntity.badRequest().body("El atributo 'contrasena' no puede estar vacío ni ser nulo para actualizar el usuario.");
	    }
	    
	    if (usuario.getRol() == null) {
	    	return ResponseEntity.badRequest().body("El atributo 'rol' no puede estar nulo para actualizar el usuario.");
	    }
	    
	    if (!this.usuarioService.existsUsuario(idUsuario)) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.ok(this.usuarioService.save(usuario));
	}



	@DeleteMapping("/{idUsuario}")
	public ResponseEntity<Usuario> delete(@PathVariable int idUsuario) {
		if (this.usuarioService.delete(idUsuario)) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
