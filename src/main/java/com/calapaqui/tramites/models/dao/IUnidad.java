package com.calapaqui.tramites.models.dao;




import org.springframework.data.repository.CrudRepository;


import com.calapaqui.tramites.models.entities.Unidad;

public interface IUnidad extends CrudRepository<Unidad, Integer>{
	//public List<Unidad> listByDireccion(Direccion d);
}
