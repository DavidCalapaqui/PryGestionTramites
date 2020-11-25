package com.calapaqui.tramites.models.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.calapaqui.tramites.models.dao.IUsuario;
import com.calapaqui.tramites.models.entities.Rol;
import com.calapaqui.tramites.models.entities.Usuario;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private IUsuario dao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = dao.findByNombre(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario " + username + " no encontrado");
		}

		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		for (Rol rol : usuario.getRoles()) {
			roles.add(new SimpleGrantedAuthority(rol.getNombre()));
		}

		if (roles.isEmpty()) {
			throw new UsernameNotFoundException("Usuario " + username + " no tiene roles asignados");
		}
		return new User(usuario.getNombre(), usuario.getPassword(), usuario.getHabilitado(), true, true, true, roles);
	}

	@Transactional
	public void save(Usuario usuario) {
		dao.save(usuario);
	}

	@Transactional
	public List<Usuario> findAll() {
		return (List<Usuario>) dao.findAll();
	}
	
	@Transactional
	public Usuario findById(Integer id) {
		return dao.findById(id).get();
	}

	@Transactional
	public Usuario findByNombre(String nombre) {
		return dao.findByNombre(nombre);
	}

	@Transactional
	public Usuario findUserByName(String username) {

		return dao.findByNombre(username);

	}

	// devuelve el usuario en sesion
	public Usuario ObtenerUsuarioEnSesion() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		String userName = userDetails.getUsername();
		Usuario usuario = findUserByName(userName);
		return usuario;
	}
	
	
	
	
	public Usuario updateUser(Usuario fromUser) throws Exception {
		Usuario toUser = ObtenerUsuarioEnSesion();
		mapUser(fromUser, toUser);
		return dao.save(toUser);
	}
	
	protected void mapUser(Usuario from,Usuario to) {
		to.setNombre(from.getNombre());
		//to.setFirstName(from.getFirstName());
		//to.setLastName(from.getLastName());
		//to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
	}
	
}
