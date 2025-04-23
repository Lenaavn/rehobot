package com.reho.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reho.persistence.entities.Usuario;
import com.reho.persistence.entities.enums.Rol;
import com.reho.persistence.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<Usuario> findAll() {
		return this.usuarioRepository.findAll();
	}

	public boolean existsUsuario(int idUsuario) {
		return this.usuarioRepository.existsById(idUsuario);
	}

	public Optional<Usuario> findById(int idUsuario) {
		return this.usuarioRepository.findById(idUsuario);
	}

	public Usuario create(Usuario usuario) {

		if (usuario.getRol() == null) {
			usuario.setRol(Rol.USER);
		}

		return usuarioRepository.save(usuario);
	}

	public Usuario save(Usuario usuario) {

		Usuario existingUsuario = usuarioRepository.findById(usuario.getId()).get();

		if (usuario.getNombre() != null && !usuario.getNombre().trim().isEmpty()) {
			existingUsuario.setNombre(usuario.getNombre());
		}
		if (usuario.getEmail() != null && !usuario.getEmail().trim().isEmpty()) {
			existingUsuario.setEmail(usuario.getEmail());
		}
		if (usuario.getContrasena() != null && !usuario.getContrasena().trim().isEmpty()) {
			existingUsuario.setContrasena(usuario.getContrasena());
		}
		if (usuario.getRol() != null) {
			existingUsuario.setRol(usuario.getRol());
		}
		if (usuario.getTelefono() != null && !usuario.getTelefono().trim().isEmpty()) {
			existingUsuario.setTelefono(usuario.getTelefono());
		}

		return usuarioRepository.save(existingUsuario);
	}

	public boolean delete(int idUsuario) {
		boolean result = false;

		if (this.usuarioRepository.existsById(idUsuario)) {
			this.usuarioRepository.deleteById(idUsuario);
			result = true;
		}

		return result;
	}

	public boolean existsUsuario(Integer idUsuario) {
		return usuarioRepository.existsById(idUsuario);
	}

	public boolean existsByEmail(String email) {
		return usuarioRepository.existsByEmail(email);

	}

	public boolean existsByTelefono(String telefono) {
		return usuarioRepository.existsByTelefono(telefono);
	}
	
	// Método para obtener los usuarios ordenados alfabeticamente
	// Comparator.comparing -> sirve para simplificar la comparación de objetos.
	public List<Usuario> findAllOrdenadosPorNombre() {
        List<Usuario> usuarios = this.usuarioRepository.findAll();
        usuarios.sort(Comparator.comparing(Usuario::getNombre));
        return usuarios;
    }

}
