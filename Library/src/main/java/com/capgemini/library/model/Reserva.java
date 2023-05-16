package com.capgemini.library.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	private LocalDateTime fechaReserva;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "copia_id")
	private Copia copia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lector_id")
	private Lector lector;

	private LocalDateTime fechaNotificacion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getFechaReserva() {
		return fechaReserva;
	}

	public void setFechaReserva(LocalDateTime localDateTime) {
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

	public LocalDateTime getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(LocalDateTime fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

}
