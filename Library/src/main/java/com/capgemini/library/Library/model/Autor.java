package com.capgemini.library.Library.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Autor implements Serializable {

	private static final long serialVersionUID = -9098486109466500407L;

	@Id
	@Column
	private String id = UUID.randomUUID().toString();

	@Column
	private String nombre;

	@Column
	private String nacionalidad;

	@Column
	private Date fechaNacimiento;

	@OneToMany
	private Set<Libro> obras;

	public Autor() {
		super();
	}

	public Autor(String id, String nombre, String nacionalidad, Date fechaNacimiento) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.fechaNacimiento = fechaNacimiento;
	}

	public Autor(String nombre, String nacionalidad, Date fechaNacimiento) {
		super();
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Set<Libro> getObras() {
		return obras;
	}

	public void setObras(Set<Libro> obras) {
		this.obras = obras;
	}

}
