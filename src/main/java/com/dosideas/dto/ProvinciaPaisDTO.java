package com.dosideas.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa todas las provincias de un pais sin mostrar el mismo
 * 
 * @author Gabriel Romero
 */
@Getter
@Setter
@Builder
public class ProvinciaPaisDTO {

    private Long id;
    private String nombre;

    public ProvinciaPaisDTO() {
    }

    /**
     * Construye una instancia de ProvinciaPaisDTO con los atributos especificados.
     * 
     * @param id El identificador de la provincia.
     * @param nombre El nombre de la provincia.
     */
    public ProvinciaPaisDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
