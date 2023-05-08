package com.capgemini.library.Library.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Lector implements Serializable {

	private static final long serialVersionUID = 1130048379273572765L;

	@Id
	@Column
	private String id = UUID.randomUUID().toString();

	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long nSocio;

	@Column
	private String nombre;

	@Column
	private String telefono;

	@Column
	private String direccion;

	@OneToOne
	private Prestamo prestamo;

	@OneToOne
	private Multa multa;

	public Lector() {
		super();
	}

	public Lector(String id, Long nSocio, String nombre, String telefono, String direccion, Prestamo prestamo) {
		super();
		this.id = id;
		this.nSocio = nSocio;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		this.prestamo = prestamo;
	}

	public Lector(Long nSocio, String nombre, String telefono, String direccion, Prestamo prestamo) {
		super();
		this.nSocio = nSocio;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		this.prestamo = prestamo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getnSocio() {
		return nSocio;
	}

	public void setnSocio(Long nSocio) {
		this.nSocio = nSocio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Prestamo getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	public Multa getMulta() {
		return multa;
	}

	public void setMulta(Multa multa) {
		this.multa = multa;
	}

}
