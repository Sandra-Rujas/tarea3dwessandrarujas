package com.sandrarujas.tarea3dwessandrarujas.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sandrarujas.tarea3dwessandrarujas.modelo.Planta;

@Repository
public interface PlantaRepository extends JpaRepository <Planta, Long>{
	
	
	@Query("SELECT p FROM Planta p WHERE p.id = :id")
    Planta buscarPorID(@Param("id") Long id);

    @Query("UPDATE Planta p SET p.nombreComun = :nombreComun WHERE p.codigo = :codigo")
    int actualizarNombreComun(@Param("codigo") String codigo, @Param("nombreComun") String nombreComun);

    @Query("UPDATE Planta p SET p.nombreCientifico = :nombreCientifico WHERE p.codigo = :codigo")
    int actualizarNombreCientifico(@Param("codigo") String codigo, @Param("nombreCientifico") String nombreCientifico);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Planta p WHERE p.codigo = :codigo")
    boolean codigoExistente(@Param("codigo") String codigo);

	Optional <Planta> findByCodigo(String codigo);

	boolean existeCodigo(String codigo);

	List<Planta> listaPlantas();

}