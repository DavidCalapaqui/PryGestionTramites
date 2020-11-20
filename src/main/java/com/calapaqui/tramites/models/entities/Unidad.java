package com.calapaqui.tramites.models.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="unidades")
public class Unidad  implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "pk_unidad")
	private Integer idUnidad;
	
	@Column(name = "nombre")
	private String nombre;
	

	@JoinColumn(name="fk_direccion",referencedColumnName="pk_direccion")
	@ManyToOne
	private Direccion direccion;


	public Unidad() {
		super();
	}


	public Unidad(Integer idUnidad) {
		super();
		this.idUnidad = idUnidad;
	}


	public Integer getIdUnidad() {
		return idUnidad;
	}


	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Direccion getDireccion() {
		return direccion;
	}


	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}


	@Override
	public String toString() {
		return this.getNombre();
	}
	
	
}
