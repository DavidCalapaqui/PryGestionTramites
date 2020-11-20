package com.calapaqui.tramites.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.calapaqui.tramites.models.entities.Usuario;



public interface IUsuario extends CrudRepository<Usuario, Integer>  {
	public Usuario findByNombre(String nombre);	
}
