package com.calapaqui.tramites.models.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.calapaqui.tramites.models.dao.IDireccion;

import com.calapaqui.tramites.models.entities.Direccion;

@Service
public class DireccionService implements IDireccionService{
	
	//implementacion de las interfaces

	@Autowired
	private IDireccion dao;
	
	@Override
	@Transactional
	public void Save(Direccion d) {
		dao.save(d);
	}

	@Override
	@Transactional
	public Direccion findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	@Transactional
	public void Delete(Integer id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional
	public List<Direccion> FindAll() {
		return (List<Direccion>) dao.findAll();
	}

}
