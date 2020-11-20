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
@Table(name="respuestas")
public class Respuesta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "pk_respuesta")
	private Integer idRespuesta;
	
	@JoinColumn(name="fk_direccion", referencedColumnName="pk_direccion")
	@OneToOne
	private Direccion direccion;
	
	@Column(name = "fecha_respuesta")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar fechaRespuesta;
	
	@Column(name = "hora_respuesta")
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")	
	private Date horaRespuesta;
	
	@Column(name = "respuesta")
	private String respuesta;
	


	public Respuesta() {
		super();
	}



	public Respuesta(Integer idRespuesta) {
		super();
		this.idRespuesta = idRespuesta;
	}



	public Integer getIdRespuesta() {
		return idRespuesta;
	}

	public void setIdRespuesta(Integer idRespuesta) {
		this.idRespuesta = idRespuesta;
	}

	

	public Direccion getDireccion() {
		return direccion;
	}



	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}



	public Calendar getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(Calendar fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	public Date getHoraRespuesta() {
		return horaRespuesta;
	}

	public void setHoraRespuesta(Date horaRespuesta) {
		this.horaRespuesta = horaRespuesta;
	}
	
	
	
	public String getRespuesta() {
		return respuesta;
	}



	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}



	public String obtenerFecha() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		return sdf.format(this.fechaRespuesta.getTime());
	}
	
	public String obtenerHora() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(this.horaRespuesta);
	}



	@Override
	public String toString() {
		return getRespuesta();
	}
	
	
}
