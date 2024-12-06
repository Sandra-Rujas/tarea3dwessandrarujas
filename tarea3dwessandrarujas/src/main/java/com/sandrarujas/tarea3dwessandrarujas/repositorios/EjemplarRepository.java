package com.sandrarujas.tarea3dwessandrarujas.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sandrarujas.tarea3dwessandrarujas.modelo.Ejemplar;
import com.sandrarujas.tarea3dwessandrarujas.modelo.Planta;

import jakarta.transaction.Transactional;


@Repository
public interface EjemplarRepository extends JpaRepository <Ejemplar, Long>{

	 @Query("SELECT COUNT(e) FROM Ejemplar e")
    int totalEjemplares();

	 @Query("SELECT e FROM Ejemplar e WHERE e.planta.codigo = :codigoPlanta")
	 List<Ejemplar> ejemplaresPorPlanta(@Param("codigoPlanta") String codigoPlanta);

	@Transactional
    @Modifying
    @Query("UPDATE Ejemplar e SET e.nombre = :nuevoNombre WHERE e.id = :idEjemplar")
    int editarNombre(@Param("idEjemplar") Long idEjemplar, @Param("nuevoNombre") String nuevoNombre);

}

