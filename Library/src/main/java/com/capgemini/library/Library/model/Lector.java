package com.capgemini.library.Library.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Lector {
	@Id
	@Column
	private Identifier nSocio;
	@Column
	private String nombre;
	@Column
	private String telefono;
	@Column
	private String direccion;


	public void devolver(Identifier id, Date fechaAct) {
		if(prestamos.notEmpty()) {
			
		}
	}
	
	public void prestar(Identifier id, Date fechaAct) {
		if(multar==0) {
			
		}
	}

	public Identifier getnSocio() {
		return nSocio;
	}

	public void setnSocio(Identifier nSocio) {
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
}
