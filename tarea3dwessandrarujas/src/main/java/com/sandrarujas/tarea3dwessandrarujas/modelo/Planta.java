package com.sandrarujas.tarea3dwessandrarujas.modelo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="plantas")
public class Planta implements Serializable {
	
	private static final long serialVersionUID=1L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private String codigoplanta;
	
	@Column
	private String nombrecomun;
	
	@Column
	private String nombrecientifico;
	
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="idplanta")
	private List <Ejemplar> ejemplares=new LinkedList<Ejemplar>();
	
	
	//Constructor
	public Planta() {}

	
	//Getters y Setters.
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombrecomun() {
		return nombrecomun;
	}

	public void setNombrecomun(String nombrecomun) {
		this.nombrecomun = nombrecomun;
	}

	public String getNombrecientifico() {
		return nombrecientifico;
	}

	public void setNombrecientifico(String nombrecientifico) {
		this.nombrecientifico = nombrecientifico;
	}

	public List<Ejemplar> getEjemplares() {
		return ejemplares;
	}

	public void setEjemplares(List<Ejemplar> ejemplares) {
		this.ejemplares = ejemplares;
	}
	
	
	
	
}
