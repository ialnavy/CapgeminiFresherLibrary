package com.capgemini.library.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	private Integer anyo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "autor_id")
	private Autor autor;

	@OneToMany(mappedBy = "libro")
	private Set<Copia> ejemplar = new HashSet<>();

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

	public Integer getAnyo() {
		return anyo;
	}

	public void setAnyo(Integer anyo) {
		this.anyo = anyo;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}// hola

	public Set<Copia> getEjemplar() {
		return ejemplar;
	}

	public void setEjemplar(Set<Copia> ejemplar) {
		this.ejemplar = ejemplar;
	}

}
