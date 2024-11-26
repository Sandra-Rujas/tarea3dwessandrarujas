package com.sandrarujas.tarea3dwessandrarujas.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandrarujas.tarea3dwessandrarujas.modelo.Planta;
import com.sandrarujas.tarea3dwessandrarujas.repositorios.EjemplarRepository;
import com.sandrarujas.tarea3dwessandrarujas.repositorios.PlantaRepository;

@Service
public class ServiciosPlanta {

	@Autowired
	PlantaRepository plantarepo;
	
	
	public boolean validarPlanta(Planta p) {
		
		return true;
	}
	
	
	public void insertarPlanta(Planta p) {
		plantarepo.save(p);
		
		
	}
	
}
