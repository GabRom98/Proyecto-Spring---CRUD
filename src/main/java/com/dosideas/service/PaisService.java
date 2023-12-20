package com.dosideas.service;

import com.dosideas.domain.Pais;
import com.dosideas.repository.PaisRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Esta es la clase de negocio que expone operaciones relacionadas con el objeto
 * de dominio Pais.
 *
 * La anotación @Service es de Spring, y le indica a Spring que esta clase
 * deberá ser tratada como clase de servicios. Spring registrará esta clase para
 * ser inyectada en donde sea necesario.
 *
 * La anotación @Transactional indica que todos los métodos de esta clase
 * deberán ser transaccionales (necesario para JPA).
 *
 * El constructor de esta clase recibe su dependencia obligatoria. Esto le
 * indica a Spring que para constuir una instancia de esta clase deberá primero
 * conseguir una instancia de su dependencia.
 *
 */
@Service
@Transactional
public class PaisService {
    // Spring "sabe" cómo manejar la inyección de dependencias incluso sin la anotación @Autowired. 
    //El uso del constructor para la inyección de dependencias es suficiente 
    //para que Spring comprenda cómo proporcionar la implementación necesaria de PaisRepository.
    private final PaisRepository paisRepository;

    public PaisService(PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    public Pais buscarPorId(Long id) {
        return paisRepository.findById(id).orElse(null);
    }

    public List<Pais> buscarTodos() {
        return paisRepository.findAll();
    }

}
