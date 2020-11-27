package com.calapaqui.tramites.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


import javax.servlet.http.HttpServletResponse;

import com.calapaqui.tramites.models.services.IDireccionService;

import com.calapaqui.tramites.models.services.ISolicitanteService;
import com.calapaqui.tramites.models.services.ITramiteService;
import com.calapaqui.tramites.models.services.IUnidadService;
import com.calapaqui.tramites.models.services.UsuarioService;

import com.calapaqui.tramites.models.entities.Direccion;
import com.calapaqui.tramites.models.entities.Solicitante;
import com.calapaqui.tramites.models.entities.Tramite;
import com.calapaqui.tramites.models.entities.Unidad;
import com.calapaqui.tramites.models.entities.Usuario;

@Controller
@RequestMapping(value = "/tramite")
public class TramiteController {

	@Autowired
	private ITramiteService srvTramite;

	@Autowired
	private IDireccionService srvDireccion;

	@Autowired
	private ISolicitanteService srvSolicitente;

	@Autowired
	private IUnidadService srvUnidad;

	@Autowired
	private UsuarioService srvUsuario;

	@GetMapping(value = "/create")
	public String Create(Model model) {
		Tramite tramite = new Tramite();
		List<Direccion> direcciones = srvDireccion.FindAll();
		model.addAttribute("tramite", tramite);
		model.addAttribute("direcciones", direcciones);
		model.addAttribute("titulo", "Nuevo Trámite");
		model.addAttribute("btnTitulo", "Guardar");
		model.addAttribute("isEdit", false);
		return "tramite/form";
	}

	@GetMapping(value = "/list")
	public String List(Model model) {

		List<Tramite> tramites = this.srvTramite.FindAll();

		model.addAttribute("tramites", tramites);
		return "tramite/list";
	}

	@GetMapping(value = "/listByDireccion")
	public String ListByDireccion(Model model) {
		Usuario usuario = this.BuscarUsuario();
		String depUsr = usuario.getDireccion().getNombre();
		System.out.println("Dep: " + depUsr);
		List<Tramite> tramites;

		if ((depUsr.equals("Alcaldia")) || (depUsr.equals("Archivo")) || (depUsr.isEmpty())) {

			tramites = srvTramite.FindAll();
			model.addAttribute("tramites", tramites);

		} else {
			tramites = srvTramite.findByDireccion(usuario.getDireccion());
			model.addAttribute("tramites", tramites);
		}

		return "tramite/list";
	}

	@GetMapping(value = "/listByUnidad")
	public String ListByUnidad(Model model) {
		Usuario usuario = this.BuscarUsuario();
		String depUsr = usuario.getDireccion().getNombre();
		System.out.println("Dep: " + depUsr);
		List<Tramite> tramites;

		if ((depUsr.equals("Alcaldia")) || (depUsr.equals("Archivo")) || (depUsr.isEmpty())) {

			tramites = srvTramite.FindAll();
			model.addAttribute("tramites", tramites);

		} else {
			tramites = srvTramite.findByUnidad(usuario.getUnidad());
			model.addAttribute("tramites", tramites);
		}

		return "tramite/list";
	}

	// METODOS PARA ASIGNAR UNA UNIDAD
	@GetMapping(value = "/assignUnit/{id}")
	public String AsignarUnidad(@PathVariable(value = "id") Integer id, Model model) {
		Tramite tramite = this.srvTramite.findById(id);
		// List<Direccion> direcciones = this.srvDireccion.FindAll();

		List<Unidad> unidades = this.BuscarUsuario().getDireccion().getUnidades();
		model.addAttribute("tramite", tramite);
		model.addAttribute("unidades", unidades);
		return "tramite/AsignarUnidad";
	}

	@PostMapping(value = "/putUnit")
	public String PonerUnidad(@RequestParam("file") MultipartFile file, Model model, String txtIdTram,
			String slcUnidades, String txtRespuesta, String auxFile) {

		Integer idTramite = Integer.valueOf(txtIdTram);
		Integer idUnidad = Integer.valueOf(slcUnidades);
		Calendar fecha = Calendar.getInstance();
		java.util.Date hora = Calendar.getInstance().getTime();

		Tramite tramite = this.srvTramite.findById(idTramite);

		if (!file.isEmpty()) {
			Path directorioRecursos = Paths.get("src//main//resources//static//uploads");
			String rootPath = directorioRecursos.toFile().getAbsolutePath();
			try {

				byte[] bytes = file.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + file.getOriginalFilename());
				Files.write(rutaCompleta, bytes);

				tramite.setDocumento(file.getOriginalFilename());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			tramite.setDocumento(auxFile);
		}

		tramite.setFechaIngresoUnidad(fecha);
		tramite.setHoraIngresoUnidad(hora);
		tramite.setRespuesta(txtRespuesta);
		tramite.setUnidad(this.srvUnidad.findById(idUnidad));

		this.srvTramite.Save(tramite);
		return "redirect:/tramite/listByDireccion";

	}

	// METODOS PARA ASIGNAR DIRECCION
	@PostMapping(value = "/putDireccion")
	public String PonerDireccion(@RequestParam("file") MultipartFile file, Model model, String txtIdTram,
			String slcDirecciones, String txtRespuesta, String auxFile) {

		Integer idTramite = Integer.valueOf(txtIdTram);
		Integer idDireccion = Integer.valueOf(slcDirecciones);
		Calendar fecha = Calendar.getInstance();
		java.util.Date hora = Calendar.getInstance().getTime();

		Tramite tramite = this.srvTramite.findById(idTramite);

		if (!file.isEmpty()) {
			Path directorioRecursos = Paths.get("src//main//resources//static//uploads");
			String rootPath = directorioRecursos.toFile().getAbsolutePath();
			try {

				byte[] bytes = file.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + file.getOriginalFilename());
				Files.write(rutaCompleta, bytes);

				tramite.setDocumento(file.getOriginalFilename());

			} catch (IOException e) {

				e.printStackTrace();
			}
		} else {
			tramite.setDocumento(auxFile);
		}

		tramite.setFechaIngresoDireccion(fecha);
		tramite.setHoraIngresoDireccion(hora);
		tramite.setRespuesta(txtRespuesta);
		tramite.setDireccion(this.srvDireccion.findById(idDireccion));

		this.srvTramite.Save(tramite);
		return "redirect:/tramite/listByDireccion";

	}

	@GetMapping(value = "/assignDireccion/{id}")
	public String AsignarDireccion(@PathVariable(value = "id") Integer id, Model model) {
		Tramite tramite = this.srvTramite.findById(id);
		List<Direccion> direcciones = this.srvDireccion.FindAll();
		model.addAttribute("tramite", tramite);
		model.addAttribute("direcciones", direcciones);
		return "tramite/AsignarDireccion";
	}

	// metodos para responder un tramite
	@GetMapping(value = "/replyProcedure/{id}")
	public String ResponderTramite(@PathVariable(value = "id") Integer id, Model model) {
		Tramite tramite = this.srvTramite.findById(id);
		model.addAttribute("tramite", tramite);

		return "tramite/ResponderTramite";
	}

	@PostMapping(value = "/sendReply")
	public String ResponderTramite(@RequestParam("file") MultipartFile file, Model model, String txtRespuesta,
			String txtIdTram, String auxFile) {
		Integer idTramite = Integer.valueOf(txtIdTram);

		Tramite tramite = this.srvTramite.findById(idTramite);

		Calendar fecha = Calendar.getInstance();
		java.util.Date hora = Calendar.getInstance().getTime();

		if (!file.isEmpty()) {
			Path directorioRecursos = Paths.get("src//main//resources//static//uploads");
			String rootPath = directorioRecursos.toFile().getAbsolutePath();
			try {

				byte[] bytes = file.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + file.getOriginalFilename());
				Files.write(rutaCompleta, bytes);

				tramite.setDocumento(file.getOriginalFilename());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			tramite.setDocumento(auxFile);
		}

		tramite.setRespuesta(txtRespuesta);
		this.srvTramite.Save(tramite);

		return "redirect:/tramite/listByDireccion";

	}

//	@GetMapping(value = "/retrieve/{id}")
//	public String Retrieve(@PathVariable(value = "id") Integer id, Model model) {
//		Tramite tramite = this.srvTramite.findById(id);
//		model.addAttribute("tramite", tramite);
//
//		return "tramite/retrieve";
//	}
	
	@GetMapping(value = "/retrieve/{id}")
	public String Retrieve(@PathVariable(value = "id") Integer id, Model model) {
		Tramite tramite = this.srvTramite.findById(id);
		String usrName = System.getProperty("user.name");
		File carpetaArchivos = new File("C:\\Users\\" + usrName + "\\Downloads\\ArchivosPDF");
		model.addAttribute("tramite", tramite);
		model.addAttribute("ruta", carpetaArchivos.toString()+"\\");
		
		

		return "tramite/retrieve";
	}

	@GetMapping(value = "/update/{id}")

	public String Update(@PathVariable(value = "id") Integer id, Model model) {
		Tramite tramite = this.srvTramite.findById(id);
		List<Direccion> direcciones = srvDireccion.FindAll();
		model.addAttribute("btnTitulo", "Actualizar");
		model.addAttribute("tramite", tramite);
		model.addAttribute("direcciones", direcciones);
		model.addAttribute("isEdit", true);
		model.addAttribute("titulo", "Actualizando el trámite N° " + tramite.getIdTramite());

//		System.out.println("Fecha Ingreso: " + tramite.getFechaIngreso());
//		System.out.println("Hora Ingreso: " + tramite.getHoraIngreso());
		return "tramite/form";

	}

	@GetMapping(value = "/delete/{id}")
	public String delete(@PathVariable(value = "id") Integer id, Model model, RedirectAttributes flash) {
		this.srvTramite.Delete(id);
		flash.addFlashAttribute("success", "Se ha eliminado con exito");
		return "redirect:/tramite/list";
	}

	@GetMapping(value = "/searchOneById/{id}", produces = "application/json")
	public @ResponseBody Tramite searchOne(@PathVariable(value = "id") String id, Model model) {
		Integer idTramite = Integer.valueOf(id);
		Tramite tramite = this.srvTramite.findById(idTramite);
		System.out.println(tramite);
		return tramite;
	}

	// devuelve el usuario en sesion
	public Usuario BuscarUsuario() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		String userName = userDetails.getUsername();
		Usuario usuario = this.srvUsuario.findUserByName(userName);
		return usuario;
	}

//	@PostMapping(value = "/save")
//	public String Save(@Validated Tramite tramite, BindingResult result, Model model,
//			@RequestParam("file") MultipartFile file, String txtAsunto,String txtSumilla, String txtSol, String auxFile,
//			RedirectAttributes flash) {
//
//		// Hora y fecha del sistema
//		Calendar fechaSistema = Calendar.getInstance();
//		java.util.Date horaSistema = Calendar.getInstance().getTime();
//
//		// id del soicitante
//		Integer idSoli = Integer.valueOf(txtSol);
//		Solicitante soli = this.srvSolicitente.findById(idSoli);
//		
//		
//		// se está creando un nuevo
//		if (tramite.getIdTramite() == null) {
//			tramite.setFechaIngreso(fechaSistema);
//			tramite.setHoraIngreso(horaSistema);
//			tramite.setAsunto(txtAsunto);
//			tramite.setSolicitante(soli);
//			tramite.setDocumento(this.srvTramite.CargarArchivo(file));
//			this.srvTramite.Save(tramite);
//			
//			flash.addFlashAttribute("success","Tramite registrado exitosamente");
//
//		} else {
//			// se está actualizando
//			Tramite tramiteBD = this.srvTramite.findById(tramite.getIdTramite());
//			tramite.setFechaIngreso(tramiteBD.getFechaIngreso());
//			tramite.setHoraIngreso(tramiteBD.getHoraIngreso());
//			tramite.setAsunto(txtAsunto);
//			tramite.setRespuesta(txtSumilla);;
//			tramite.setSolicitante(soli);
//			if (!file.isEmpty()) {
//				tramite.setDocumento(this.srvTramite.CargarArchivo(file));
//			}else {
//				tramite.setDocumento(auxFile);
//			}
//			this.srvTramite.Save(tramite);
//			flash.addFlashAttribute("success","Tramite actualizado exitosamente");
//		}
//
//
//		return "redirect:/tramite/list";
//	}
	
	@PostMapping(value = "/save")
	public String Save(@Validated Tramite tramite, BindingResult result, Model model,
			@RequestParam("file") MultipartFile file, String txtAsunto,String txtSumilla, String txtSol, String auxFile,
			RedirectAttributes flash) {

		// Hora y fecha del sistema
		Calendar fechaSistema = Calendar.getInstance();
		java.util.Date horaSistema = Calendar.getInstance().getTime();

		// id del soicitante
		Integer idSoli = Integer.valueOf(txtSol);
		Solicitante soli = this.srvSolicitente.findById(idSoli);
		
		
		// se está creando un nuevo
		if (tramite.getIdTramite() == null) {
			tramite.setFechaIngreso(fechaSistema);
			tramite.setHoraIngreso(horaSistema);
			tramite.setAsunto(txtAsunto);
			tramite.setSolicitante(soli);
			tramite.setDoc(this.srvTramite.CargarArchivo(file));
			this.srvTramite.Save(tramite);
			
			flash.addFlashAttribute("success","Tramite registrado exitosamente");

		} else {
			// se está actualizando
			Tramite tramiteBD = this.srvTramite.findById(tramite.getIdTramite());
			tramite.setFechaIngreso(tramiteBD.getFechaIngreso());
			tramite.setHoraIngreso(tramiteBD.getHoraIngreso());
			tramite.setAsunto(txtAsunto);
			tramite.setRespuesta(txtSumilla);;
			tramite.setSolicitante(soli);
			if (!file.isEmpty()) {
				tramite.setDoc(this.srvTramite.CargarArchivo(file));
			}
			else {
				tramite.setDoc(tramiteBD.getDoc());
			}
			this.srvTramite.Save(tramite);
			flash.addFlashAttribute("success","Tramite actualizado exitosamente");
		}


		return "redirect:/tramite/list";
	}
	
	
	
	

	// =======  REPORTES ================

	@GetMapping(value = "/reportOneDate")
	public String reporteFecha(Model model) {
		// Calendar fecha = Calendar.getInstance();
		Tramite tramite = new Tramite();
		model.addAttribute("tramite", tramite);
		model.addAttribute("title", "Reporte de trámites ingresados en una fecha");
		return "tramite/reporteFecha";
	}

	@GetMapping(value = "/reportBetweenDates")
	public String reporteEntreFechas(Model model) {
		// Calendar fecha = Calendar.getInstance();
		Tramite tramite = new Tramite();
		model.addAttribute("tramite", tramite);
		model.addAttribute("title", "Reporte de trámites ingresados en un intervalo de fechas");
		return "tramite/reporteEntreFechas";
	}

	// consulta segun la feha y devuelve un array json con la lista de trámites
	@GetMapping(value = "/getTramitesOneDate/{date}", produces = "application/json")
	public @ResponseBody List<Tramite> getTramitesOneDate(@PathVariable(value = "date") String date, Model model) {
		String fechaFormat = date.substring(8, 10) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4);

		System.out.println("Date to query: " + date);
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaDate = null;
		try {
			fechaDate = formato.parse(date);
		} catch (ParseException ex) {
			System.out.println(ex);
		}

		List<Tramite> tramitesFecha = this.srvTramite.reporteFecha(fechaDate);

		System.out.println("Fecha: " + fechaDate);
		System.out.println("Fecha: " + fechaFormat);
		return tramitesFecha;
	}

	@GetMapping(value = "/getTramitesBetweenDates/{desde}/{hasta}", produces = "application/json")
	public @ResponseBody List<Tramite> getTramitesEntreFechas(@PathVariable String desde, @PathVariable String hasta,
			Model model) {
		System.out.println("Desde: " + desde + " Hasta: " + hasta);

		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

		Date desdeDate = null;
		Date hastaDate = null;
		try {
			desdeDate = formato.parse(desde);
			hastaDate = formato.parse(hasta);
		} catch (ParseException ex) {
			System.out.println(ex);
		}

		return this.srvTramite.reporteEntreFechas(desdeDate, hastaDate);
	}
	
	@GetMapping(value="/showPDF/{idTramite}")
	public void getPDFFile(@PathVariable Integer idTramite, HttpServletResponse response) {
		System.out.println("Llego al controlador");
		this.srvTramite.writePDFToResponse(idTramite, response);
	}
}


