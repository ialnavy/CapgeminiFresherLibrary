package com.capgemini.library.Library.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

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
	private Date fInicio;

	@Column
	private Date fFin;

	@OneToOne
	private Lector lector;

	public Multa() {
		super();
	}

	public Multa(String id, Date fInicio, Date fFin, Lector lector) {
		super();
		this.id = id;
		this.fInicio = fInicio;
		this.fFin = fFin;
		this.lector = lector;
	}

	public Multa(Date fInicio, Date fFin, Lector lector) {
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

	public Date getfInicio() {
		return fInicio;
	}

	public void setfInicio(Date fInicio) {
		this.fInicio = fInicio;
	}

	public Date getfFin() {
		return fFin;
	}

	public void setfFin(Date fFin) {
		this.fFin = fFin;
	}

	public Lector getLector() {
		return lector;
	}

	public void setLector(Lector lector2) {
		this.lector = lector2;
	}

}
