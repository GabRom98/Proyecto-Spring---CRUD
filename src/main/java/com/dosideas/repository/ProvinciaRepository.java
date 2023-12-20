package com.dosideas.repository;

import com.dosideas.domain.Provincia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Provincia.
 * 
 * @author Gabriel Romero
 */
//Introspeccion //a
@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {
    /**
     * Busca provincias por el nombre completo utilizando Query Method.
     * CaseInsensitive
     *
     * @param nombre El nombre de la provincia.
     * @return La lista de provincias encontradas.
     */
    List<Provincia> findByNombreIgnoreCase(String nombre);

    /**
     * Busca provincias por el nombre del país utilizando JPQL.
     *
     * @param nombrePais El nombre del país.
     * @return La lista de provincias encontradas.
     */
    @Query("SELECT p FROM Provincia p WHERE p.pais.nombre = :nombrePais")
    List<Provincia> findByNombrePais(@Param("nombrePais") String nombrePais);

    /**
     * Busca provincias por el nombre que contiene ciertos caracteres (insensible a mayúsculas y minúsculas).
     *
     * @param nombre El nombre de la provincia.
     * @return La lista de provincias encontradas.
     */
    @Query("SELECT p FROM Provincia p WHERE p.nombre ILIKE %:nombre%")
    List<Provincia> buscarProvinciasPorNombreILike(@Param("nombre") String nombre);
}
