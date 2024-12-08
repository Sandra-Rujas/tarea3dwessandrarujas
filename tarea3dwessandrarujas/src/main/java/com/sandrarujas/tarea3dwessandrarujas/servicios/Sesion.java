package com.sandrarujas.tarea3dwessandrarujas.servicios;

import org.springframework.stereotype.Component;

@Component
public class Sesion {

	private String usuario;

	public Sesion() {
		this.usuario = "";
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public void cerrarSesion() {
		this.setUsuario("");
	}

}
