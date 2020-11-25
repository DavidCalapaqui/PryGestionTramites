package com.calapaqui.tramites.models.services;

import java.util.List;

import com.calapaqui.tramites.models.entities.Unidad;

public interface IUnidadService {
	public void Save(Unidad u);
	public Unidad findById(Integer id);
	public void Delete(Integer id);
	public List<Unidad> FindAll();
	//public List<Unidad> listByDireccion(Direccion d);
}
