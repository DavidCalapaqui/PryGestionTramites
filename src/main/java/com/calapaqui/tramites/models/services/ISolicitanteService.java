package com.calapaqui.tramites.models.services;

import java.util.List;

import com.calapaqui.tramites.models.entities.Solicitante;

public interface ISolicitanteService {

	public void Save(Solicitante s);
	public Solicitante findById(Integer id);
	public void Delete(Integer id);
	public List<Solicitante> FindAll();
	public List<Solicitante> FindByIdentificacion(String identificacaion);
	public Solicitante FindOneByIdentificacion(String identificacaion);
	
	
}
