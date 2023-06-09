package com.capgemini.library.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Reserva implements Serializable {

	private static final long serialVersionUID = 4429209086756420860L;

	@Id
	@Column
	private String id = UUID.randomUUID().toString();

	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaReserva;

	@ManyToOne
	@JoinColumn(name = "copia_id")
	private Copia copia;

	@ManyToOne
	@JoinColumn(name = "lector_id")
	private Lector lector;

	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaNotificacion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(LocalDate localDateTime) {
		this.fechaReserva = localDateTime;
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

	public LocalDate getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(LocalDate fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

}
