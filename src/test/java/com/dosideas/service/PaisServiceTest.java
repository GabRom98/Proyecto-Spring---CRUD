package com.dosideas.service;

import com.dosideas.domain.Pais;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Esta clase representa un test de componente. Los tests de componentes se
 * encargan de testear una clase en particular, y todas las dependencias que la
 * misma tiene.
 *
 * Este test comprueba el funcionamiento de la clase PaisRepository. Esta clase
 * utiliza Spring Data para acceder a la base de datos. El proyecto utiliza una
 * base de datos en memoria (HSQLDB) que Spring levanta y configura de manera
 * automática. Cuando se crea el contexto de Spring, se levanta esta base de
 * datos y se ejecuta el archivo schema.sql (buscar en "Other Sources") que crea
 * las tablas PAIS y PROVINCIA, y le inserta datos de prueba.
 *
 * Para ejecutar este test en NetBeans: click derecho > "Test File" (CTRL + F6)
 *
 * Podemos ver que esta clase tiene las siguientes anotaciones:
 *
 * @SpringBootTest: Si utilizamos una aplicación con Spring Boot, nos da
 * diversas features de Spring Boot (ver Javadoc). Si en la clase
 * ApplicationConfig utilizamos la anotacion @SpringBootApplication, no hace
 * falta pasarle la clase como parámetro
 *
 */
@SpringBootTest
public class PaisServiceTest {

    /**
     * La instancia bajo test. La anotación @Autowired hará que Spring la
     * inyecte automáticamente. En los tests no se puede usar constructor como
     * mecanismo de inyección de dependencias.
     */
    @Autowired
    private PaisService paisService;

    /**
     * Test de buscarPorId method con un id existente. El metodo debe encontrar
     * un Pais con el id buscado.
     */
    @Test
    public void buscarPorId_conIdExistente_retornaProvinciaConEseId() {
        Long id = 1L;

        Pais pais = paisService.buscarPorId(id);

        assertThat(pais).isNotNull();
        assertThat(pais.getId()).isEqualTo(id);

    }

    /**
     * Test de buscarPorId method con un id inexistente. El metodo debe devolver
     * null al buscar un id que no existe.
     */
    @Test
    public void buscarPorId_conIdInexistente_retornaNull() {
        Long id = 21L;

        Pais pais = paisService.buscarPorId(id);
        assertThat(pais).isNull();
    }

    /**
     * Test de buscarPorId method con un id null. El metodo debe tirar una
     * InvalidDataAccessApiUsageException al intengar invocar al metodo con un
     * null.
     */
    @Test
    public void buscarPorId_conIdNull_lanzaExcepcion() {
        assertThatExceptionOfType(InvalidDataAccessApiUsageException.class)
                .isThrownBy(() -> paisService.buscarPorId(null));

    }
}
