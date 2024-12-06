package com.sandrarujas.tarea3dwessandrarujas.repositorios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sandrarujas.tarea3dwessandrarujas.modelo.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository <Mensaje, Long>{
	
    @Query("SELECT m FROM Mensaje m WHERE m.fechaHora BETWEEN :primeraFecha AND :segundaFecha")
    ArrayList<Mensaje> buscarMensajePorFecha(@Param("primeraFecha") LocalDateTime primeraFecha,@Param("segundaFecha") LocalDateTime segundaFecha);

    @Query("SELECT m FROM Mensaje m WHERE m.persona.id = :idPersona")
    ArrayList<Mensaje>buscarMensajePorID(@Param("idPersona")long idPersona);

    @Query("SELECT m FROM Mensaje m INNER JOIN m.ejemplar e INNER JOIN e.planta p WHERE p.codigo = :codigoPlanta")
    ArrayList<Mensaje>buscarMensajePorPlanta(@Param("codigoPlanta") String codigoPlanta);

    @Query("SELECT m FROM Mensaje m WHERE m.ejemplar.id = :idEjemplar")
    ArrayList<Mensaje> buscarMensajePorEjemplar(@Param("idEjemplar")long idEjemplar);

}