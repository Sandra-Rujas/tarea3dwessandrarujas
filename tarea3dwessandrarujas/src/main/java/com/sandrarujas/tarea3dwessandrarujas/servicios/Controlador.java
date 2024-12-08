package com.sandrarujas.tarea3dwessandrarujas.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Controlador {
	
    private String usuarioConectado;
	
	@Autowired
	ServiciosPlanta svPlanta;
	
	@Autowired
	ServiciosEjemplar svEjemplar;
	
	@Autowired
	ServiciosPersona svPersona;
	
	@Autowired
	ServiciosCredencial svCredencial;
	
	@Autowired
	ServiciosMensaje svMensaje;
	
	

	// Métodos para obtener las clases servicios de cada clase en las fachadas
	
	public ServiciosPlanta getServiciosPlanta() {
		return svPlanta;
	}

	public ServiciosEjemplar getServiciosEjemplar() {
		return svEjemplar;
	}
	
	public ServiciosPersona getServiciosPersona() {
		return svPersona;
	}
	
	public ServiciosCredencial getServiciosCredencial() {
		return svCredencial;
	}
	
	public ServiciosMensaje getServiciosMensaje() {
		return svMensaje;
	}
	
	

	/**
	 * Método para iniciar sesión con un usuario 
	 */
    public void iniciarSesion(String usuario) {
        this.usuarioConectado = usuario;
    }
    
    
    /**
	 * Método para obtener el usuario conectado 
	 */
    public String obtenerUsuarioConectado() {
        return usuarioConectado;
    }
    
    /**
	 * Método para cerrar sesión 
	 */
	public void cerrarSesion() {
		this.usuarioConectado = null;
	}
	
	

}


