package com.sandrarujas.tarea3dwessandrarujas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sandrarujas.tarea3dwessandrarujas.modelo.Ejemplar;
import com.sandrarujas.tarea3dwessandrarujas.modelo.Planta;

@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar,Long>{
	
	

}
