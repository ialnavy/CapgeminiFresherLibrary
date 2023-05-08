package com.capgemini.library.Library.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class Autor {

	@Id
	@Column
	private String id = UUID.randomUUID().toString();

}
