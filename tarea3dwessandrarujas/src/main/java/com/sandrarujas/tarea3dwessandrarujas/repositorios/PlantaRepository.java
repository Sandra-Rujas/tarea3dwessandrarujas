package com.sandrarujas.tarea3dwessandrarujas.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sandrarujas.tarea3dwessandrarujas.modelo.Planta;

import jakarta.transaction.Transactional;

@Repository
public interface PlantaRepository extends JpaRepository <Planta, Long>{
	

	@Modifying
	@Transactional
	@Query("UPDATE Planta p SET p.nombreComun = :nombreComun WHERE p.codigo = :codigo")
	int actualizarNombreComun(@Param("codigo") String codigo, @Param("nombreComun") String nombreComun);

	@Modifying
	@Transactional
	@Query("UPDATE Planta p SET p.nombreCientifico = :nombreCientifico WHERE p.codigo = :codigo")
	int actualizarNombreCientifico(@Param("codigo") String codigo, @Param("nombreCientifico") String nombreCientifico);

    Optional<Planta> findByCodigo(String codigo);
    
	Optional<Planta> findById(Long id);

	boolean existsByCodigo(String codigo);
	
	
	List<Planta> findAllByOrderByNombreComunAsc();

}