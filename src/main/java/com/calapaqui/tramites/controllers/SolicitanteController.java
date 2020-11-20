package com.calapaqui.tramites.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.calapaqui.tramites.models.entities.Solicitante;
import com.calapaqui.tramites.models.entities.Tramite;
import com.calapaqui.tramites.models.services.ISolicitanteService;
import com.calapaqui.tramites.models.services.TramiteService;

@Controller("solicitante")
@RequestMapping(value = "/solicitante")
public class SolicitanteController {

	@Autowired
	private ISolicitanteService srvSolicitante;

	@Autowired
	private TramiteService srvTramite;

	@GetMapping(value = "/create")
	public String Create(Model model) {
		Solicitante solicitante = new Solicitante();
		model.addAttribute("title", "Nuevo Registro de Solicitante");
		model.addAttribute("solicitante", solicitante);
		return "solicitante/form";

	}

	@GetMapping(value = "/update/{id}")
	public String Update(@PathVariable(value = "id") Integer id, Model model) {
		Solicitante solicitante = this.srvSolicitante.findById(id);
		model.addAttribute("solicitante", solicitante);
		model.addAttribute("title", "Actualizando información de " + solicitante.getNombre());
		return "solicitante/form";
	}

	@GetMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id") Integer id, Model model, RedirectAttributes flash) {
		// buscar si el solicitante tiene tramites
		Solicitante solicitante = this.srvSolicitante.findById(id);
		List<Tramite> tramitesSol = this.srvTramite.findBySolicitante(solicitante);

		if (tramitesSol.size() != 0) {
			flash.addFlashAttribute("error", "No se puede eliminar este solicitante porque tiene trámites asignados");
		} else {
			this.srvSolicitante.Delete(id);
			flash.addFlashAttribute("success", "Se ha eliminado con exito");
		}
		return "redirect:/solicitante/list";
	}

	@GetMapping(value = "/list")
	public String List(Model model) {
		List<Solicitante> solicitantes = this.srvSolicitante.FindAll();
		model.addAttribute("solicitantes", solicitantes);
		model.addAttribute("title", "Listado de solicitantes");
		return "solicitante/list";
	}

	@PostMapping(value = "/save")
	public String Save(@Validated Solicitante solicitante, BindingResult result, RedirectAttributes flash,
			Model model) {
		
		Solicitante solDB = this.srvSolicitante.FindOneByIdentificacion(solicitante.getIdentificacion());
		//si se está actualizando
		if(solicitante.getIdSolicitante() != null) {
			//pero escribe una identificacion que ya existe
			if(solDB != null) {
				flash.addFlashAttribute("error","Ya existe un solicitante registrado con esa identificacion");
				return "redirect:/solicitante/list";
			}else {
				flash.addFlashAttribute("success","Solicitante actualizado");
				srvSolicitante.Save(solicitante);
				return "redirect:/solicitante/list";
			}
			
		}else {
			//si no se está actualizando valida que no esté ya registrado y que no haya id para que sea un nuevo registro
			if(solDB == null && solicitante.getIdSolicitante() == null) {
				srvSolicitante.Save(solicitante);
				flash.addFlashAttribute("success","Solicitante registrado");
			}else {
				flash.addFlashAttribute("error","Ya existe un solicitante registrado con esa identificacion");
				return "redirect:/solicitante/list";
			}
		}
		
		return "redirect:/tramite/create";

	}

	@GetMapping(value = "/search/{criteria}", produces = "application/json")
	public @ResponseBody List<Solicitante> search(@PathVariable(value = "criteria") String criteria, Model model) {
		List<Solicitante> lista = this.srvSolicitante.FindByIdentificacion(criteria);
		System.out.println(lista);
		return lista;
	}

	@GetMapping(value = "/searchOne/{criteria}", produces = "application/json")
	public @ResponseBody Solicitante searchOne(@PathVariable(value = "criteria") String criteria, Model model) {

		Solicitante solicitante = null;

		try {

			solicitante = this.srvSolicitante.FindOneByIdentificacion(criteria);
		} catch (Exception e) {
			System.out.println("No se encontró el solicitante");

		}
		return solicitante;

//		if(solicitante.equals(null)) {
//			System.out.println("No se encontró el solicitante");
//		}else {
//			return solicitante;
//			
//		}
//		return solicitante;
		// System.out.println(solicitante);

	}

}
