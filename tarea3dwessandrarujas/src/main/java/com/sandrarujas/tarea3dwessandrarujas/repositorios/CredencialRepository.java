package com.sandrarujas.tarea3dwessandrarujas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sandrarujas.tarea3dwessandrarujas.modelo.Credencial;

@Repository
public interface CredencialRepository extends JpaRepository <Credencial, Long>{

	boolean usuarioExiste(String usuario);
	
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Credenciales c WHERE c.usuario = :usuario AND c.password = :password")
    boolean credencialCorrecta(@Param("usuario") String usuario, @Param("password") String password);

}