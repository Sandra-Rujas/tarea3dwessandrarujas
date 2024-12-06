package com.sandrarujas.tarea3dwessandrarujas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sandrarujas.tarea3dwessandrarujas.modelo.Persona;

public interface PersonaRepository  extends JpaRepository <Persona, Long>{

	boolean emailExistente(String email);

	 @Query("SELECT c.persona.id FROM Credenciales c WHERE c.usuario = :usuario")
	 Long idUsuarioAutenticado(@Param("usuario") String usuario);
	 
	 Persona findByNombreContainingIgnoreCase(String nombre);

}