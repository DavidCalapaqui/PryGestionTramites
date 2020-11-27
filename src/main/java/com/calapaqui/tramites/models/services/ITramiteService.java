package com.calapaqui.tramites.models.services;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.calapaqui.tramites.models.entities.Direccion;
import com.calapaqui.tramites.models.entities.Solicitante;
import com.calapaqui.tramites.models.entities.Tramite;
import com.calapaqui.tramites.models.entities.Unidad;

public interface ITramiteService {

	public void Save(Tramite t);
	public Tramite findById(Integer id);
	public List<Tramite> findBySolicitante(Solicitante s);
	public void Delete(Integer id);
	public List<Tramite> FindAll();
	public List<Tramite> findByDireccion(Direccion d);
	public List<Tramite> findByUnidad(Unidad u);
	public List<Tramite> reporteFecha(Date fecha );
	public List<Tramite> reporteEntreFechas(Date desde, Date hasta);
	public byte[] CargarArchivo(MultipartFile file);
	public void writePDFToResponse(Integer id, HttpServletResponse response);
	

	
}
