package com.calapaqui.tramites.models.entities;

import java.io.Serializable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tramites")
public class Tramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "pk_tramite")
	private Integer idTramite;

	@Column(name = "asunto")
	private String asunto;

	@Column(name = "documento")
	private String documento;
	
	@Column(name = "respuesta")
	private String respuesta;

	// fecha y hora ingreso en archivo
	@Column(name = "fecha_ingreso")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar fechaIngreso;

	@Column(name = "hora_ingreso")
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date horaIngreso;
	//@DateTimeFormat(pattern = "yyyy-MM-dd ")
	// fecha y hora ingreso a direccion
	@Column(name = "fecha_ingreso_direccion")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar fechaIngresoDireccion;

	@Column(name = "hora_ingreso_direccion")
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date horaIngresoDireccion;

	// fecha y hora ingreso en unidad
	@Column(name = "fecha_ingreso_unidad")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd ")
	private Calendar fechaIngresoUnidad;

	@Column(name = "hora_ingreso_unidad")
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date horaIngresoUnidad;

	@JoinColumn(name = "fk_solicitante", referencedColumnName = "pk_solicitante")
	@OneToOne
	private Solicitante solicitante;

	@JoinColumn(name = "fk_direccion", referencedColumnName = "pk_direccion")
	@OneToOne
	private Direccion direccion;

	@JoinColumn(name = "fk_unidad", referencedColumnName = "pk_unidad")
	@OneToOne
	private Unidad unidad;

	


	public Tramite() {
		super();
	}

	public Tramite(Integer idTramite) {
		super();
		this.idTramite = idTramite;
	}

	public Integer getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(Integer idTramite) {
		this.idTramite = idTramite;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Calendar getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Calendar fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Solicitante getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(Solicitante solicitante) {
		this.solicitante = solicitante;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	
	



	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public Date getHoraIngreso() {
		return horaIngreso;
	}

	public void setHoraIngreso(Date horaIngreso) {
		this.horaIngreso = horaIngreso;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	public Calendar getFechaIngresoDireccion() {
		return fechaIngresoDireccion;
	}

	public void setFechaIngresoDireccion(Calendar fechaIngresoDireccion) {
		this.fechaIngresoDireccion = fechaIngresoDireccion;
	}

	public Date getHoraIngresoDireccion() {
		return horaIngresoDireccion;
	}

	public void setHoraIngresoDireccion(Date horaIngresoDireccion) {
		this.horaIngresoDireccion = horaIngresoDireccion;
	}

	public Calendar getFechaIngresoUnidad() {
		return fechaIngresoUnidad;
	}

	public void setFechaIngresoUnidad(Calendar fechaIngresoUnidad) {
		this.fechaIngresoUnidad = fechaIngresoUnidad;
	}

	public Date getHoraIngresoUnidad() {
		return horaIngresoUnidad;
	}

	public void setHoraIngresoUnidad(Date horaIngresoUnidad) {
		this.horaIngresoUnidad = horaIngresoUnidad;
	}

	public String obtenerFechaIngreso() {
		SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MMM/yyyy");
		SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
		return sdfFecha.format(this.fechaIngreso.getTime()) + " - " + sdfHora.format(this.horaIngreso);
	}

	public String obtenerFechaIngresoDireccion() {
		SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MMM/yyyy");
		SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
		return sdfFecha.format(this.getFechaIngresoDireccion().getTime()) + " - "
				+ sdfHora.format(this.horaIngresoDireccion);
	}

	public String obtenerFechaIngresoUnidad() {
		SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MMM/yyyy");
		SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
		return sdfFecha.format(this.fechaIngresoUnidad.getTime()) + " - " + sdfHora.format(this.horaIngresoUnidad);
	}

}
