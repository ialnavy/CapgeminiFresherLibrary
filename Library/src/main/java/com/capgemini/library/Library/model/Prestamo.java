package com.capgemini.library.Library.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	private Date fechaPrestamo;

	@Column
	private Date fechaDevolucion;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "lector_id", referencedColumnName = "id")
	private Lector lector;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "copia_id", referencedColumnName = "id")
	private Copia copia;

	public Prestamo() {
		super();
	}

	public Prestamo(String id, Date fechaPrestamo, Date fechaDevolucion, Lector lector, Copia copia) {
		super();
		this.id = id;
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
		this.lector = lector;
		this.copia = copia;
	}

	public Prestamo(Date fechaPrestamo, Date fechaDevolucion, Lector lector, Copia copia) {
		super();
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
		this.lector = lector;
		this.copia = copia;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
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

}