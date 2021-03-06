package com.calapaqui.tramites.models.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
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
		//retorn la lista de los tramites ingresados en una fecha
		List<Tramite> tramitePorFecha = new ArrayList<Tramite>();
		List<Tramite> allTramites = (List<Tramite>)dao.findAll();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		String fechaAConsultar = sdf.format(fecha);
		//recorre todos los registros de la BD
		for(Tramite t: allTramites) {
			
			String fechaDB = sdf.format(t.getFechaIngreso().getTime());
			//coinciden las fechas a consultar se agrega el tramite a la lista tramitePorFecha
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
			
		//recorre todos los tramites de la BD
		for(Tramite t: allTramites) {
			//compara si las un tramite de todos los registros se encuentran entre el rango y se agrega a la lista rangoTramites 
			if(		(desde.before(t.getFechaIngreso().getTime()) || desde.equals(t.getFechaIngreso().getTime())) 
																&& 
					(hasta.after(t.getFechaIngreso().getTime()) || hasta.equals(t.getFechaIngreso().getTime())) ) {
				rangoTramites.add(t);
			}
		}
		
		return rangoTramites;
	}
	
//	@Override
//	@Transactional
//	public String CargarArchivo(MultipartFile file) {
//		
//		Path directorioRecursos = Paths.get("src//main//resources//static//uploads");
//		String rootPath = directorioRecursos.toFile().getAbsolutePath();
//		try {
//	
//			byte[] bytes = file.getBytes();
//			Path rutaCompleta = Paths.get(rootPath + "//" + file.getOriginalFilename());
//			Files.write(rutaCompleta, bytes);
//	
//		} catch (IOException e) {
//	
//			e.printStackTrace();
//		}
//		
//		return file.getOriginalFilename();
//	}
	//recibe el multipartfile que llega del front, obtiene los bytes del documento y los retorna
	@Override
	@Transactional
	public byte[] CargarArchivo(MultipartFile file) {
		
		
		byte[] bytes = null;
				
		//Path directorioRecursos = Paths.get("src//main//resources//static//uploads");
		//Path directorioRecursos = carpetaArchivos.toPath();
		//String rootPath = directorioRecursos.toFile().getAbsolutePath();
		try {
	
			bytes = file.getBytes();
			//tramite.setDoc(bytes);
			//Path rutaCompleta = Paths.get(rootPath + "//" + file.getOriginalFilename());
			//Files.write(rutaCompleta, bytes);
	
		} catch (IOException e) {
	
			e.printStackTrace();
		}
		
		return bytes;
	}
	
	//esta funcion muestra el archivo pdf en la misma ventana utilizando el response del servidor
	@Override
	public void writePDFToResponse(Integer id, HttpServletResponse response) {
		response.setContentType("application/pdf");
		response.setHeader("Cache-Control", "max-age=2628000");
		
		
		//obtiene los bytes del archivo de la bbdd
		byte[] pdf = dao.findById(id).get().getDoc();
		//esta variable no se utiliza peroporsiacaso :v
		String strPDF = Base64.getEncoder().encodeToString(pdf);
		
		try {

			//limpia el outputstream
			response.getOutputStream().flush();
			InputStream bos = new ByteArrayInputStream(pdf);
			int tamanoInput = bos.available();
			byte[] datosPDF = new byte[tamanoInput];
			bos.read(datosPDF,0,tamanoInput);
			//escribe el pdf en la salida
			response.getOutputStream().write(datosPDF);
			response.getOutputStream().flush();
			
			bos.close();
			
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		

		
	}
	
	

}
