package com.calapaqui.tramites.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.calapaqui.tramites.models.entities.Solicitante;

public interface ISolicitante extends CrudRepository<Solicitante, Integer>{
	
	public List<Solicitante> findByIdentificacion(String identificacion);
	public Solicitante findOneByIdentificacion(String identificacion);
}
