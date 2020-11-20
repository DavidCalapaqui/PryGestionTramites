package com.calapaqui.tramites.models.services;

import java.util.List;
import com.calapaqui.tramites.models.entities.Respuesta;

public interface IRespuestaService {
	
		public void Save(Respuesta r);
		public Respuesta findById(Integer id);
		public void Delete(Integer id);
		public List<Respuesta> FindAll();
	

}
