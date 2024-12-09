package com.sandrarujas.tarea3dwessandrarujas.servicios;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandrarujas.tarea3dwessandrarujas.modelo.Mensaje;
import com.sandrarujas.tarea3dwessandrarujas.repositorios.MensajeRepository;

@Service
public class ServiciosMensaje {
	
	@Autowired
	MensajeRepository mensajeRepository;
	
	 public void addMensaje(Mensaje mensaje) {
		 mensajeRepository.save(mensaje);
	    }

	    public ArrayList<Mensaje> buscarMensajePorFecha(LocalDate primeraFecha, LocalDate segundaFecha) {
	        ArrayList<Mensaje> mensajes = mensajeRepository.buscarMensajePorFecha(primeraFecha, segundaFecha);
	        return new ArrayList<>(mensajes);
	    }

	    public ArrayList<Mensaje> verMensajes() {
	        return (ArrayList<Mensaje>) mensajeRepository.findAll();
	    }

	    public ArrayList<Mensaje> buscarMensajesPorPersona(long idPersona) {
	        ArrayList<Mensaje> mensajes = mensajeRepository.buscarMensajePorID(idPersona);
	        return new ArrayList<>(mensajes);
	    }

	    public ArrayList<Mensaje> buscarMensajesPorPlanta(String codigoPlanta) {
	        ArrayList<Mensaje> mensajes = mensajeRepository.buscarMensajePorPlanta(codigoPlanta);
	        return new ArrayList<>(mensajes);
	    }

	    public ArrayList<Mensaje> buscarMensajesPorEjemplar(long idEjemplar) {
	        ArrayList<Mensaje> mensajes = mensajeRepository.buscarMensajePorEjemplar(idEjemplar);
	        return new ArrayList<>(mensajes);
	    }

	    public boolean validarMensaje(String mensaje) {
	        if (mensaje == null || mensaje.trim().isEmpty()) {
	            System.out.println("El mensaje está vacio.");
	            return false;
	        }
	   
	        return true;
	    }

	
}

