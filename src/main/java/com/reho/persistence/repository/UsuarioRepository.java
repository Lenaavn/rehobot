package com.reho.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reho.persistence.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{

}
