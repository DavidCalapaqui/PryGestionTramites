package com.calapaqui.tramites.models.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.calapaqui.tramites.models.dao.ITramite;

import com.calapaqui.tramites.models.entities.Direccion;
import com.calapaqui.tramites.models.entities.Solicitante;
import com.calapaqui.tramites.models.entities.Tramite;
import com.calapaqui.tramites.models.entities.Unidad;

@Service
public class TramiteService implements ITramiteService{

	@Autowired
	private ITramite dao;
	
	@PersistenceContext
	private EntityManager em;
	
//	@Autowired
//	private IDireccion daoDir;
//	
	@Override
	@Transactional
	public void Save(Tramite t) {
		dao.save(t);
		
	}

	@Override
	@Transactional
	public Tramite findById(Integer id) {
		return dao.findById(id).get();
	}


	@Override
	@Transactional
	public void Delete(Integer id) {
		dao.deleteById(id);
	}

	@Override
	@Transactional
	public List<Tramite> FindAll() {
		return (List<Tramite>)dao.findAll();
	}

	@Override
	@Transactional
	public List<Tramite> findByDireccion(Direccion d) {
		// TODO Auto-generated method stub
		return dao.findByDireccion(d);
	}

	@Override
	@Transactional
	public List<Tramite> findByUnidad(Unidad u) {
		return dao.findByUnidad(u);
	}
	
	@Override
	@Transactional
	public List<Tramite> findBySolicitante(Solicitante s) {
		return  dao.findBySolicitante(s);
	}

	@Override
	public List<Tramite> reporteFecha(Date fecha) {
		List<Tramite> tramitePorFecha = new ArrayList<Tramite>();
		List<Tramite> allTramites = (List<Tramite>)dao.findAll();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		String fechaAConsultar = sdf.format(fecha);
		
		for(Tramite t: allTramites) {
			
			String fechaDB = sdf.format(t.getFechaIngreso().getTime());
			
			if(fechaDB.equals(fechaAConsultar)) {
				
				tramitePorFecha.add(t);
			}
		}
		return tramitePorFecha;
	}



	@Override
	public List<Tramite> reporteEntreFechas(Date desde, Date hasta) {
		List<Tramite> rangoTramites = new ArrayList<Tramite>();
		List<Tramite> allTramites = (List<Tramite>)dao.findAll();
			
		
		for(Tramite t: allTramites) {
			
			if(		(desde.before(t.getFechaIngreso().getTime()) || desde.equals(t.getFechaIngreso().getTime())) 
																&& 
					(hasta.after(t.getFechaIngreso().getTime()) || hasta.equals(t.getFechaIngreso().getTime())) ) {
				rangoTramites.add(t);
			}
		}
		
		return rangoTramites;
	}
	
	@Override
	@Transactional
	public String CargarArchivo(MultipartFile file) {
		
		Path directorioRecursos = Paths.get("src//main//resources//static//uploads");
		String rootPath = directorioRecursos.toFile().getAbsolutePath();
		try {
	
			byte[] bytes = file.getBytes();
			Path rutaCompleta = Paths.get(rootPath + "//" + file.getOriginalFilename());
			Files.write(rutaCompleta, bytes);
	
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
		return file.getOriginalFilename();
	}
	


}
