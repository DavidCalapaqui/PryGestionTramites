package com.calapaqui.tramites.models.services;

import java.util.List;


import com.calapaqui.tramites.models.entities.Direccion;

//se definen los metodos que son implementados en las clases services
public interface IDireccionService {
	public void Save(Direccion d);
	public Direccion findById(Integer id);
	public void Delete(Integer id);
	public List<Direccion> FindAll();
}
