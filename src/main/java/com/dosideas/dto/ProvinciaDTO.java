package com.dosideas.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa la información simplificada de una provincia, 
 * incluyendo el nombre del país al que pertenece SIN ID.
 * 
 * @author Gabriel Romero
 */
@Getter
@Setter
@Builder
public class ProvinciaDTO {

    private Long id;
    private String nombre;
    private String pais;

    public ProvinciaDTO() {
    }

    /**
     * Construye una instancia de ProvinciaDTO con los atributos especificados.
     * 
     * @param id El identificador de la provincia.
     * @param nombre El nombre de la provincia.
     * @param pais El nombre del país al que pertenece la provincia.
     */
    public ProvinciaDTO(Long id, String nombre, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
    }
}
