package com.capgemini.library.Library.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Prestamo implements Serializable {

	private static final long serialVersionUID = 1767484640032941785L;

	@Id
	@Column
	private String id = UUID.randomUUID().toString();

	@Column
	private Date inicio;

	@Column
	private Date fin;

	@ManyToOne
	private Copia copia;

	@OneToOne
	private Lector lector;

	public Prestamo() {
		super();
	}

	public Prestamo(String id, Date inicio, Date fin) {
		super();
		this.id = id;
		this.inicio = inicio;
		this.fin = fin;
	}

	public Prestamo(Date inicio, Date fin) {
		super();
		this.inicio = inicio;
		this.fin = fin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public Copia getCopia() {
		return copia;
	}

	public void setCopia(Copia copia) {
		this.copia = copia;
	}

	public Lector getLector() {
		return lector;
	}

	public void setLector(Lector lector) {
		this.lector = lector;
	}

}
