package com.capgemini.library.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Copia implements Serializable {

	private static final long serialVersionUID = -1645129037600814474L;

	@Id
	@Column
	private String id = UUID.randomUUID().toString();

	@Enumerated(EnumType.STRING)
	private EstadoCopia estado;

	@ManyToOne
	private Libro libro;

	@OneToOne
	private Prestamo prestamo;

	public Copia() {
		super();
	}

	public Copia(String id, EstadoCopia estado) {
		super();
		this.id = id;
		this.estado = estado;
	}

	public Copia(EstadoCopia estado) {
		super();
		this.estado = estado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EstadoCopia getEstado() {
		return estado;
	}

	public void setEstado(EstadoCopia estado) {
		this.estado = estado;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	public Prestamo getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

}
