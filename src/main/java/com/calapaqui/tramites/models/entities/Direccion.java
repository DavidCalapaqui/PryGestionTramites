package com.calapaqui.tramites.models.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="direcciones")
public class Direccion implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "pk_direccion")
	private Integer idDireccion;
	
	@Column(name = "nombre")
	private String nombre;
	
	@JsonIgnore
	@OneToMany(mappedBy="direccion", fetch=FetchType.LAZY) 
	private List<Unidad> unidades;
	
	
	
	public Direccion() {
		super();
	}

	public Direccion(Integer idDireccion) {
		super();
		this.idDireccion = idDireccion;
	}

	public Integer getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Integer idDireccion) {
		this.idDireccion = idDireccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	public List<Unidad> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidad> unidades) {
		this.unidades = unidades;
	}

	@Override
	public String toString() {
		return this.getNombre();
	}
	
	
	
	
	
}
