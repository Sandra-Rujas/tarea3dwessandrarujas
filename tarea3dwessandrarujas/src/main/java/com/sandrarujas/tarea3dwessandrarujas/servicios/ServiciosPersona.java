package com.sandrarujas.tarea3dwessandrarujas.servicios;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandrarujas.tarea3dwessandrarujas.modelo.Persona;
import com.sandrarujas.tarea3dwessandrarujas.repositorios.PersonaRepository;

@Service
public class ServiciosPersona {
	
	@Autowired
	private PersonaRepository personaRepository;

	    public void insertar(Persona persona) {
	    	personaRepository.saveAndFlush(persona);
	    }

	    public Collection<Persona> verTodos() {
	        return personaRepository.findAll();
	    }

	    public boolean emailExistente(String email) {
	        return personaRepository.existsByEmail(email);
	    }

	    public long idUsuarioAutenticado(String usuario) {
	        Long idPersona = personaRepository.idUsuarioAutenticado(usuario);
	        return (idPersona != null) ? idPersona : -1;
	    }
	    

	    public Persona buscarPorNombre(String nombre){
	    	return personaRepository.findByNombreContainingIgnoreCase(nombre);
	    }
	    
	    public boolean validarPersona(Persona persona) {
	        if (persona == null) {
	            return false;
	        }
	        if (persona.getNombre() == null || persona.getNombre().isEmpty()) {
	            return false;
	        }
	        if (persona.getNombre().length() < 3 || persona.getNombre().length() > 25) {
	            return false;
	        }
	        if (persona.getEmail() == null || persona.getEmail().isEmpty()) {
	            return false;
	        }
	        if (persona.getEmail().length() < 5 || !persona.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$") || persona.getEmail().length() > 40) {
	            return false;
	        }
	        return true;
	    }



}
