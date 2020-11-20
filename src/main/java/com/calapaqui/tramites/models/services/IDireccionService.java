package com.calapaqui.tramites.models.services;

import java.util.List;


import com.calapaqui.tramites.models.entities.Direccion;


public interface IDireccionService {
	public void Save(Direccion d);
	public Direccion findById(Integer id);
	public void Delete(Integer id);
	public List<Direccion> FindAll();
}
