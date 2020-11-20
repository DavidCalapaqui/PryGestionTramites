package com.calapaqui.tramites.models.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calapaqui.tramites.models.dao.ISolicitante;
import com.calapaqui.tramites.models.entities.Solicitante;

@Service
public class SolicitanteService implements ISolicitanteService {

	@Autowired
	private ISolicitante dao;
	
	
	@Override
	@Transactional
	public void Save(Solicitante s) {
		dao.save(s);
		
	}

	@Override
	@Transactional
	public Solicitante findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	@Transactional
	public void Delete(Integer id) {
		dao.deleteById(id);
		
	}

	@Override
	@Transactional
	public List<Solicitante> FindAll() {
		return (List<Solicitante>) dao.findAll();
	}

	@Override
	@Transactional
	public List<Solicitante> FindByIdentificacion(String identificacaion) {
	
		return dao.findByIdentificacion(identificacaion);
	}

	@Override
	@Transactional
	public Solicitante FindOneByIdentificacion(String identificacaion) {
		return (Solicitante)dao.findOneByIdentificacion(identificacaion);
	}

	
}
