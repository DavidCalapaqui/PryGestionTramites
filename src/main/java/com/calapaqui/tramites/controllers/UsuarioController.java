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

	private final String TAB_FORM = "formTab";
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

	private void baseAttributerForUserForm(Model model, Usuario usuario, String activeTab) {
		model.addAttribute("userForm", usuario);
		model.addAttribute("userList", this.service.findAll());
		// model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute(activeTab, "active");
	}

	@GetMapping("/editUser")
	public String getEditUserForm(Model model) {
		Usuario userToEdit = this.BuscarUsuario();

		baseAttributerForUserForm(model, userToEdit, TAB_FORM);
		model.addAttribute("title", "Actualizando información de " + userToEdit.getNombre());
		model.addAttribute("usuario", userToEdit);
		System.out.println("Usuario: " + userToEdit.getNombre());

		return "usuario/user-configuration";
	}

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
	
	@GetMapping(value="/validCurrentPassword/{currentPassword}", produces="application/json")
	public @ResponseBody Boolean validCurrentPassword(@PathVariable(value="currentPassword") String password, Model model) {
		Usuario sessUser = this.service.ObtenerUsuarioEnSesion();
		String passDB = sessUser.getPassword();

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
