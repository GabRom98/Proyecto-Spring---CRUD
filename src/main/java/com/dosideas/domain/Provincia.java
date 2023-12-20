package com.dosideas.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa una provincia.
 * 
 * @author Gabriel Romero
 */
@Getter
@Setter
@Builder
@Entity
public class Provincia implements Serializable {
/**
 * Serializable para almacenar o transmitir objetos de manera eficiente.
 * 
 * 
 */
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_pais")
    private Pais pais;
    
    // Constructores
    public Provincia() {
    }

    public Provincia(Long id, String nombre, Pais pais) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
    }

}
