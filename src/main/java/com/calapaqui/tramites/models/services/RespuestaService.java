package com.calapaqui.tramites.models.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calapaqui.tramites.models.dao.IRespuesta;
import com.calapaqui.tramites.models.entities.Respuesta;

@Service
public class RespuestaService implements IRespuestaService {

	@Autowired
	private IRespuesta dao;
	
	@Override
	@Transactional
	public void Save(Respuesta r) {
		dao.save(r);
		
	}

	@Override
	@Transactional
	public Respuesta findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	@Transactional
	public void Delete(Integer id) {
		dao.deleteById(id);
		
	}

	@Override
	@Transactional
	public List<Respuesta> FindAll() {
		// TODO Auto-generated method stub
		return (List<Respuesta>) dao.findAll();
	}

}
