package com.sandrarujas.tarea3dwessandrarujas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.sandrarujas.tarea3dwessandrarujas.modelo.Planta;
import com.sandrarujas.tarea3dwessandrarujas.servicios.ServiciosEjemplar;
import com.sandrarujas.tarea3dwessandrarujas.servicios.ServiciosPlanta;


public class Principal implements CommandLineRunner {
	
	//La anterior capa controladora
	@Autowired 
	ServiciosPlanta servplant;
	
	
	@Autowired 
	ServiciosEjemplar servejemplar;
	
	
	//Antiguo Main
	@Override
	public void run(String... args) throws Exception {
		System.out.println("INI");
		
		Planta p =new Planta();
		servplant.validarPlanta(p);
		
		servplant.validarPlanta(p);
		
		
		System.out.println("----------------");
		
		System.out.println("FIN");
	}

}
