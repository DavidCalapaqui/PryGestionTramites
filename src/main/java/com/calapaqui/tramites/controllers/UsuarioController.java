package com.calapaqui.tramites.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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


import com.calapaqui.tramites.models.entities.Rol;
import com.calapaqui.tramites.models.entities.Usuario;
import com.calapaqui.tramites.models.services.UsuarioService;

@Controller
@RequestMapping(value = "/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@Autowired
	private BCryptPasswordEncoder encoder;

	//retorna formulario para crear un usuario. Funcion disponible en  .../usuario/create
	//solo se puede entrar con un usuario admin y esribiendo la URL
	@GetMapping(value = "/create")
	public String registro(Model model) {
		Usuario usuario = new Usuario();
		// List<Direccion> direcciones = this.srvDireccion.FindAll();
		// model.addAttribute("direcciones", direcciones);
		model.addAttribute("usuario", usuario);
		model.addAttribute("title", "Registro de nuevo usuario");
		return "usuario/form";
	}
	
	@GetMapping(value = "/configuration")
	public String cambiarContrasena(Model model) {
		Usuario usuario = this.BuscarUsuario();
		model.addAttribute("usuario", usuario);
		model.addAttribute("title", "Actualizando contraseña de " + usuario.getNombre());
		return "usuario/form";
	}

	
	//devuelve la vista para actualizar la contraseña
	@GetMapping("/editUser")
	public String getEditUserForm(Model model) {
		Usuario userToEdit = this.BuscarUsuario();

		model.addAttribute("title", "Actualizando información de " + userToEdit.getNombre());
		model.addAttribute("usuario", userToEdit);
		System.out.println("Usuario: " + userToEdit.getNombre());

		return "usuario/user-configuration";
	}
	//actualiza la contraseña
	@PostMapping("/postEditUser")
	public String postEditUserForm(@Validated Usuario user, BindingResult result, Model model, 
			String newPassword, RedirectAttributes flash) {
	
		try {
			
			Usuario userToEdit = this.service.findById(this.service.ObtenerUsuarioEnSesion().getIdusuario());
			userToEdit.setPassword(encoder.encode(newPassword));
			service.save(userToEdit);
			flash.addFlashAttribute("success", "Contraseña actualizada con exito.");
		} catch (Exception ex) {
			flash.addFlashAttribute("error", "No se pudo actualizar la contraseña");
			
		}

		return "redirect:/";

	}
	
	//retorna verdadero o falso si la contraseña que se está escribiendo al cambiar la contraseña es la que esta registrada en a BD
	@GetMapping(value="/validCurrentPassword/{currentPassword}", produces="application/json")
	public @ResponseBody Boolean validCurrentPassword(@PathVariable(value="currentPassword") String password, Model model) {
		Usuario sessUser = this.service.ObtenerUsuarioEnSesion();
		String passDB = sessUser.getPassword();
		//compara la contraseña registrada en la BD con la que se esta introducion
		if(encoder.matches(password, passDB)) {
			System.out.println("Coinciden las contraseñas");
			return true;
		}
		else {
					System.out.println("no coinciden las contraseñas");
			return false;
		}
		
		
	}

	// devuelve el usuario en sesion
	public Usuario BuscarUsuario() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		String userName = userDetails.getUsername();
		Usuario usuario = this.service.findUserByName(userName);
		return usuario;
	}
	
	//guarda un usuario
	@PostMapping(value = "/save")
	public String save(@Validated Usuario usuario, BindingResult result, Model model, RedirectAttributes flash) {
		try {
			if (result.hasErrors()) {
				model.addAttribute("title", "Registro de nuevo usuario");
				model.addAttribute("usuario", usuario);
				return "usuario/form";
			}
			String pass = usuario.getPassword();
			usuario.setPassword(encoder.encode(pass));

			usuario.getRoles().add(new Rol("ROLE_USER"));
			usuario.setHabilitado(true);
			service.save(usuario);
			flash.addFlashAttribute("success", "El usuario fue agregado con éxito.");
		} catch (Exception ex) {
			flash.addFlashAttribute("error", "El usuario no pudo ser agregado.");
		}
		return "redirect:/login";
	}
}
