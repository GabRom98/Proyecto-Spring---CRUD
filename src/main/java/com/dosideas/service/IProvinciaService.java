package com.dosideas.service;

import com.dosideas.domain.Provincia;
import java.util.List;

/**
 * Interfaz que define los servicios relacionados con la entidad Provincia.
 * 
 * @author Gabriel Romero
 */
public interface IProvinciaService {

    /**
     * Busca una provincia por su ID.
     *
     * @param id El ID de la provincia a buscar.
     * @return La provincia encontrada o null si no se encuentra.
     */
    Provincia buscarPorId(long id);

    /**
     * Guarda una provincia.
     *
     * @param provincia La provincia a guardar.
     * @return La provincia guardada.
     */
    Provincia guardarProvincia(Provincia provincia);

    /**
     * Obtiene todas las provincias de un país por su nombre.
     *
     * @param nombrePais El nombre del país.
     * @return La lista de provincias encontradas.
     */
    List<Provincia> obtenerProvinciasPorNombrePais(String nombrePais);

    /**
     * Busca provincias por su nombre exacto.
     *
     * @param nombre nombre exaco, formato caseInsensitive
     * @return La lista de provincias encontradas.
     */
    List<Provincia> buscarProvinciasPorNombreExacto(String nombre);
    
    /**
     * Busca provincias por su nombre exacto.
     *
     * @param provincia Valor a modificar en en la db
     * @return La lista de provincias encontradas.
     */
    Provincia modificarProvincia(Provincia provincia);
    
    /**
     * Busca provincias por su nombre exacto.
     *
     * @param id provincia a eliminar en la BD
     */
    void eliminarProvincia(long id);
    
    /**
     * Devuelve Todas las provincias.
     *
     * @return La lista de provincias creadas.
     */
    List<Provincia> buscarTodasLasProvincias();
}
