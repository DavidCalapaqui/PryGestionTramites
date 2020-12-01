package com.calapaqui.tramites.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.calapaqui.tramites.models.entities.Direccion;

//extiende las funciones de la interfaz crud repository para realizar el crud de las entidades. <entidad, tipo de dato de la clave primaria de la entidad>
public interface IDireccion extends CrudRepository<Direccion, Integer>{

}
