package com.sandrarujas.tarea3dwessandrarujas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sandrarujas.tarea3dwessandrarujas.modelo.Planta;

@Repository
public interface PlantaRepository extends JpaRepository<Planta,Long>{
	
	
	/*public void insertarPlanta(Planta p) {
		this.saveAndFlush(p);
	}*/

}
