package com.calapaqui.tramites.models.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calapaqui.tramites.models.dao.IUnidad;
import com.calapaqui.tramites.models.entities.Direccion;
import com.calapaqui.tramites.models.entities.Unidad;

@Service
public class UnidadService implements IUnidadService {

	@Autowired
	private IUnidad dao;
	
	
	@Override
	@Transactional
	public void Save(Unidad u) {
		dao.save(u);
	}

	@Override
	@Transactional
	public Unidad findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	@Transactional
	public void Delete(Integer id) {
		dao.deleteById(id);
		
	}

	@Override
	@Transactional
	public List<Unidad> FindAll() {
		return (List<Unidad>) dao.findAll();
	}

	/*@Override
	@Transactional
	public List<Unidad> listByDireccion(Direccion d) {
		return (List<Unidad>) dao.listByDireccion(d);
	}*/

}
