package com.calapaqui.tramites.models.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="solicitantes")
public class Solicitante implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "pk_solicitante")
	private Integer idSolicitante;
	
	
	@Column(name= "identificacion")
	private String identificacion;
	
	@Column(name = "nombre")
	private String nombre;

	public Solicitante() {
		super();
	}

	public Solicitante(Integer idSolicitante, String identificacion, String nombre) {
		super();
		this.idSolicitante = idSolicitante;
		this.identificacion = identificacion;
		this.nombre = nombre;
	}

	public Integer getIdSolicitante() {
		return idSolicitante;
	}

	public void setIdSolicitante(Integer idSolicitante) {
		this.idSolicitante = idSolicitante;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return this.getNombre();
	}
	
	

	
}
