package com.capgemini.library.Library.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Prestamo implements Serializable {

	private static final long serialVersionUID = 5754304269210020193L;

	@Id
	@Column
	private String id = UUID.randomUUID().toString();
	@Column
	private Date fechaInicio;
	@Column
	private Date fechaFin;
	@Column(nullable = true)
	private Date fechaDevolucion;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Lector lector;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Copia copia;

	public Prestamo() {
		super();
	}

	public Prestamo(String id, Date fechaInicio, Date fechaFin) {
		super();
		this.id = id;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Lector getLector() {
		return lector;
	}

	public void setLector(Lector lector) {
		this.lector = lector;
	}

	public Copia getCopia() {
		return copia;
	}

	public void setCopia(Copia copia) {
		this.copia = copia;
	}

	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

}