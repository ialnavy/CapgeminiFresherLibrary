package com.capgemini.library.Library.model;

import java.sql.Date;

public class Lector {
	
	private Identifier nSocio;
	
	private String nombre;
		
	private String telefono;
	
	private String direccion;
	
	public void devolver(Identifier id, Date fechaAct) {
		if(prestamos.notEmpty()) {
			
		}
	}
	
	public void prestar(Identifier id, Date fechaAct) {
		if(multa==0) {
			
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
