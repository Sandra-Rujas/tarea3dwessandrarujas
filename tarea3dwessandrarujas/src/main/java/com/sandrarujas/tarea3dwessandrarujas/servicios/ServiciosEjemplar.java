package com.sandrarujas.tarea3dwessandrarujas.servicios;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandrarujas.tarea3dwessandrarujas.modelo.Ejemplar;
import com.sandrarujas.tarea3dwessandrarujas.repositorios.EjemplarRepository;

import jakarta.transaction.Transactional;


@Service
public class ServiciosEjemplar {

    @Autowired
    private EjemplarRepository ejemplarRepository;

    public void insertar(Ejemplar e) {
    	ejemplarRepository.save(e);
    }

    /**
     * Método para obtener todos los ejemplares en una colección.
     * 
     * @return Colección con todos los ejemplares.
     */
    public Collection<Ejemplar> verEjemplares() {
        return ejemplarRepository.findAll();
    }

    /**
     * Método para obtener un ejemplar a través de un id.
     *
     * @param id ID del ejemplar 
     * @return objeto ejemplar.
     */
    public Ejemplar buscarPorID(long id) {
        Optional<Ejemplar> ejemplares = ejemplarRepository.findById(id);
        return ejemplares.orElse(null);
    }
    
    
    /**
     * Método para cambiar el nombre de un ejemplar.
     *
     * @param idEjemplar id del ejemplar a modificar
     * @param nombre Nuevo nombre para el ejemplar
     * @return true si se ha modificado alguna fila.
     */
    @Transactional
    public boolean cambiarNombre(long idEjemplar, String nombre) {
        int filas = ejemplarRepository.editarNombre(idEjemplar, nombre);
        return filas > 0;
    }

    /**
     * Método para obtener el número total de ejemplares que hay.
     *
     * @return entero con el número total de ejemplares.
     */
    public int contarEjemplares() {
        return ejemplarRepository.totalEjemplares();
    }

    /**
     * Método para obtener los ejemplares con un tipo de planta concreto.
     *
     * @param codigo Codigo de la planta
     * @return arraylist con todos los ejemplares con ese tipo de planta.
     */
    public ArrayList<Ejemplar> ejemplaresPorTipoPlanta(String codigo) {
        List<Ejemplar> ejemplares = ejemplarRepository.ejemplaresPorPlanta(codigo);
        return new ArrayList<>(ejemplares);
    }

    /**
     * Método para comprobar si un ejemplar está correctamente creado.
     *
     * @param e Ejemplar para comprobar 
     * @return true si el ejemplar está correctamente validado.
     */
    public boolean validarEjemplar(Ejemplar e) {
        if (e.getPlanta() == null || e.getPlanta().getCodigo() == null || e.getPlanta().getCodigo().isEmpty()) {
            return false;
        }
        if (e.getPlanta().getCodigo().length() < 3 || e.getPlanta().getCodigo().length() > 20) {
            return false;
        }
        if (e.getNombre() == null || e.getNombre().isEmpty() || e.getNombre().length() < 3) {
            return false;
        }
        return true;
    }
    
    /**
     * Método para eliminar un ejemplar
     *
     * @param id ID del ejemplar
     * @return true si se ha conseguido eliminar un ejemplar.
     */
    public boolean borrarEjemplar(Long id) {
        try {
            if (ejemplarRepository.existsById(id)) {
                ejemplarRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el ejemplar: " + e.getMessage());
            return false;
        }
    }
}



