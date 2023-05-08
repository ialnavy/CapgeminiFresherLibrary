package com.capgemini.library.Library.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Libro implements Serializable {

	private static final long serialVersionUID = -4058886698375661365L;

	@Id
	@Column
	private String id = UUID.randomUUID().toString();

	@Column
	private String titulo;

	@Enumerated(EnumType.STRING)
	private TipoLibro tipo;

	@Column
	private String editorial;

	@Column
	private int anyo;

	@ManyToOne
	private Autor autor;

	@OneToMany
	private Copia ejemplar;

	public Libro() {
		super();
	}

	public Libro(String id, String titulo, TipoLibro tipo, String editorial, int anyo) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.tipo = tipo;
		this.editorial = editorial;
		this.anyo = anyo;
	}

	public Libro(String titulo, TipoLibro tipo, String editorial, int anyo) {
		super();
		this.titulo = titulo;
		this.tipo = tipo;
		this.editorial = editorial;
		this.anyo = anyo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public TipoLibro getTipo() {
		return tipo;
	}

	public void setTipo(TipoLibro tipo) {
		this.tipo = tipo;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public int getAnyo() {
		return anyo;
	}

	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Copia getEjemplar() {
		return ejemplar;
	}

	public void setEjemplar(Copia ejemplar) {
		this.ejemplar = ejemplar;
	}

}
