package com.capgemini.library.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Lector implements Serializable, UserDetails {

	private static final long serialVersionUID = 1130048379273572765L;

	@Id
	@Column
	private String id = UUID.randomUUID().toString();

	@Column(unique = true)
	private String username;

	@Column
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long nSocio;

	@Column
	private String nombre;

	@Column
	private String telefono;

	@Column
	private String email;

	@Column
	private String direccion;

	@OneToOne
	private Multa multa = null;

	@OneToMany
	private Set<Prestamo> prestamos = new HashSet<>();

	public Lector() {
		super();
	}

	public Lector(String id, String username, String password, Role role, String nombre, String telefono, String email,
			String direccion) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
		this.direccion = direccion;
	}

	public Lector(String username, String password, Role role, String nombre, String telefono, String email,
			String direccion) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(role.toString()));
		return roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
