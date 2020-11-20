package com.calapaqui.tramites.models.dao;



import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.calapaqui.tramites.models.entities.Direccion;
import com.calapaqui.tramites.models.entities.Solicitante;
import com.calapaqui.tramites.models.entities.Tramite;
import com.calapaqui.tramites.models.entities.Unidad;
import java.util.Calendar;
public interface ITramite extends CrudRepository<Tramite, Integer> {
	
	public List<Tramite> findBySolicitante(Solicitante sol);
	public List<Tramite> findByDireccion(Direccion d);
	public List<Tramite> findByUnidad(Unidad u);
//	public List<Tramite> reporteFecha(Calendar fecha );
//	public List<Tramite> reporteEntreFechas(Calendar desde, Calendar hasta);

}
