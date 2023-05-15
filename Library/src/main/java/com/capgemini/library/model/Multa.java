package com.capgemini.library.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Multa implements Serializable {

	private static final long serialVersionUID = -6356594322345914791L;

	@Id
	@Column
	private String id = UUID.randomUUID().toString();

	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fInicio;

	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fFin;

	@OneToOne
	private Lector lector;

	public Multa() {
		super();
	}

	public Multa(String id, LocalDate fInicio, LocalDate fFin, Lector lector) {
		super();
		this.id = id;
		this.fInicio = fInicio;
		this.fFin = fFin;
		this.lector = lector;
	}

	public Multa(LocalDate fInicio, LocalDate fFin, Lector lector) {
		super();
		this.fInicio = fInicio;
		this.fFin = fFin;
		this.lector = lector;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getfInicio() {
		return fInicio;
	}

	public void setfInicio(LocalDate fInicio) {
		this.fInicio = fInicio;
	}

	public LocalDate getfFin() {
		return fFin;
	}

	public void setfFin(LocalDate fFin) {
		this.fFin = fFin;
	}

	public Lector getLector() {
		return lector;
	}

	public void setLector(Lector lector2) {
		this.lector = lector2;
	}

}
