package com.sandrarujas.tarea3dwessandrarujas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sandrarujas.tarea3dwessandrarujas.modelo.Credencial;


@Repository
public interface CredencialRepository extends JpaRepository<Credencial, Long> {

	/*Método que nos permite comprobar si el usuario ya existe*/
	boolean existsByUsuario(String usuario);

	/*Método que permite comprobar a través de consulta si un usuario ya existe dentro de la bbdd*/
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Credencial c WHERE c.usuario = :usuario AND c.password = :password")
	boolean existsByUsuarioAndPassword(@Param("usuario") String usuario, @Param("password") String password);

}