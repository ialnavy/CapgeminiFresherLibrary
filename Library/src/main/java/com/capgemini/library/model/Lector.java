package com.capgemini.library.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Multa multa = null;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "lector", cascade = CascadeType.ALL)
	private Set<Prestamo> prestamos = new HashSet<>();

	public Lector() {
		super();
	}

	public Lector(String id, Long nSocio, String nombre, String telefono, String direccion) {
		super();
		this.id = id;
		this.nSocio = nSocio;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
	}

	public Multa multar(int n) {
		if (this.multa != null) {
			throw new IllegalStateException("El lector ya tiene una multa activa");
		}
		Multa multa = new Multa(LocalDate.now(), LocalDate.now().plusDays(n), this);
		this.multa = multa;
		return multa;
	}

	public Set<Prestamo> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(Set<Prestamo> prestamos) {
		this.prestamos = prestamos;
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

	public Multa getMulta() {
		return multa;
	}

	public void setMulta(Multa multa) {
		this.multa = multa;
	}

}
