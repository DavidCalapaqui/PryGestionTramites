package com.calapaqui.tramites.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.calapaqui.tramites.models.entities.Direccion;
import com.calapaqui.tramites.models.services.IDireccionService;

import java.util.List;

@Controller
@RequestMapping(value = "/direccion")
public class DireccionController {
	
	@Autowired
	private IDireccionService srvDireccion;
	
	//devuelve la lista de direcciones de la BBDD
	@GetMapping(value="list")
	public @ResponseBody List<Direccion> list(){
		return srvDireccion.FindAll();
	}
}
