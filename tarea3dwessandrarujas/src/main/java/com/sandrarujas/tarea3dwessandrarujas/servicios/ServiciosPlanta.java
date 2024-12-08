package com.sandrarujas.tarea3dwessandrarujas.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandrarujas.tarea3dwessandrarujas.modelo.Planta;
import com.sandrarujas.tarea3dwessandrarujas.repositorios.EjemplarRepository;
import com.sandrarujas.tarea3dwessandrarujas.repositorios.PlantaRepository;

import jakarta.transaction.Transactional;

@Service
public class ServiciosPlanta {

    @Autowired
    private PlantaRepository plantaRepository;

    /**
     * Método para insertar una nueva planta.
     * 
     * @param planta Objeto planta que queremos insertar
     * 
     */
    public void insertar(Planta planta) {
    	plantaRepository.saveAndFlush(planta);
    }

    
    /**
     * Método para obtener todas las plantas en una colección.
     * 
     * @return Colección de todas las plantas
     */
    @Transactional
    public List<Planta> verPlantas() {
    	return plantaRepository.findAllByOrderByNombreComunAsc();
    }

    
    /**
     * Método para buscar una planta por su ID.
     * 
     * @param id ID de la planta
     * @return Planta seleccionada por su ID o en caso de no existir 'null'
     */
    public Planta buscarPorID(long id) {
        Optional<Planta> plantas = plantaRepository.findById(id);
        return plantas.orElse(null);
    }

    /**
     * Método para actualizar el nombre común de una planta.
     * 
     * @param codigo Código de la planta
     * @param nComun Nuevo nombre común
     * @return true si se ha actualizado correctamente el nombre
     */
    @Transactional
    public boolean actualizarNombreComun(String codigo, String nComun) { 
        Optional<Planta> plantas = plantaRepository.findByCodigo(codigo);
        if (plantas.isPresent()) {
            Planta planta = plantas.get();
            planta.setNombreComun(nComun);
            plantaRepository.saveAndFlush(planta);
            return true;
        }
        return false;
    }

    /**
     * Método para actualizar el nombre científico de una planta.
     * 
     * @param codigo Código de la planta
     * @param nCientifico Nuevo nombre científico
     * @return true si se ha actualizado correctamente, false si no
     */
    @Transactional
    public boolean actualizarNombreCientifico(String codigo, String nCientifico) {
        Optional<Planta> plantas = plantaRepository.findByCodigo(codigo);
        if (plantas.isPresent()) {
            Planta p = plantas.get();
            p.setNombreCientifico(nCientifico);
            plantaRepository.saveAndFlush(p);
            return true;
        }
        return false;
    }

    /**
     * Método para comprobar si existe el código de una planta.
     * 
     * @param codigo Código de la planta
     * @return true si el código ya existe, false si no
     */
    public boolean codigoExistente(String codigo) {
        return plantaRepository.existsByCodigo(codigo);
    }

    /**
     * Método para validar una planta.
     * 
     * @param p Planta a validar
     * @return true si la planta es válida, false si no
     */
    public boolean validarPlanta(Planta p) {
        if (p.getCodigo() == null || p.getCodigo().isEmpty()) {
            return false;
        }
        if (p.getCodigo().length() < 3 || p.getCodigo().length() > 50) {
            return false;
        }
        if (p.getNombreCientifico() == null || p.getNombreComun() == null) {
            return false;
        }
        if (p.getNombreCientifico().isEmpty() || p.getNombreComun().isEmpty()) {
            return false;
        }
        if (!p.getCodigo().matches("^[A-Za-z0-9]+$")) {
            return false;
        }
        if (p.getNombreCientifico().length() < 3 || p.getNombreCientifico().length() > 100) {
            return false;
        }
        if (p.getNombreComun().length() < 3 || p.getNombreComun().length() > 100) {
            return false;
        }
        if (!p.getNombreCientifico().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")
                || !p.getNombreComun().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            return false;
        }
        return true;
    }

    /**
     * Método para validar el código de una planta.
     * 
     * @param codigo Código a validar
     * @return true si el código es válido, false si no
     */
    public boolean validarCodigo(String codigo) {
        if (codigo == null || codigo.isEmpty()) {
            return false;
        }
        if (!codigo.matches("^[A-Za-z0-9]+$")) {
            return false;
        }
        if (codigo.length() < 3 || codigo.length() > 50) {
            return false;
        }
        return true;
    }
    
    public Planta buscarPorCodigo(String codigo) {
        Optional<Planta> plantas = plantaRepository.findByCodigo(codigo);
        return plantas.orElse(null);
    }
}
