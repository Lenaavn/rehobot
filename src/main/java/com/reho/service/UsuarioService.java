package com.reho.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reho.persistence.entities.Usuario;
import com.reho.persistence.repository.UsuarioRepository;

@Service
public class UsuarioService {

	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<Usuario> findAll(){
		return this.usuarioRepository.findAll();
	}
	
	public boolean existsUsuario(int idUsuario){
		return this.usuarioRepository.existsById(idUsuario);
	}
	
	public Optional<Usuario> findById(int idUsuario){
		return this.usuarioRepository.findById(idUsuario);
	}
	
	public Usuario create(Usuario usuario) {
		return this.usuarioRepository.save(usuario);
	}
	
	public Usuario save(Usuario usuario) {
		return this.usuarioRepository.save(usuario);
	}
	
	public boolean delete(int idUsuario) {
		boolean result = false;
		
		if(this.usuarioRepository.existsById(idUsuario)) {
			this.usuarioRepository.deleteById(idUsuario);
			result = true;
		}
		
		return result;
	}



	
}
