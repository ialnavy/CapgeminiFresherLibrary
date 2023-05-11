package com.capgemini.library.Library.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaInicio;

	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaFin;

	@Column(nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaDevolucion;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Lector lector;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Copia copia;

	public Prestamo() {
		super();
	}

	public Prestamo(String id, LocalDate fechaInicio, LocalDate fechaFin) {
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

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
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

	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(LocalDate fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

}