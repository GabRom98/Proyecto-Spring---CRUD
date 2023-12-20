package com.dosideas.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.dosideas.domain.Pais;
import com.dosideas.domain.Provincia;
import com.dosideas.repository.ProvinciaRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Pruebas unitarias para {@link ProvinciaService}.
 * Se utilizan mocks para simular el comportamiento de la capa de persistencia ({@link ProvinciaRepository}).
 */
@ExtendWith(MockitoExtension.class) // Inicializa los mocks antes de ejecutar las pruebas.
public class ProvinciaServiceTest {

    @Mock
    private ProvinciaRepository provinciaRepository;

    @InjectMocks
    private ProvinciaService provinciaService;

    // Datos de ejemplo para las pruebas
    private List<Provincia> provincias = new ArrayList<>();

    /**
     * Configuración inicial para las pruebas.
     * Se ejecuta antes de cada prueba.
     */
    @BeforeEach
    void setup() {
        provincias.add(Provincia.builder()
                .id(1L)
                .nombre("Buenos Aires")
                .pais(new Pais(1L, "Argentina"))
                .build());

        provincias.add(Provincia.builder()
                .id(2L)
                .nombre("Córdoba")
                .pais(new Pais(1L, "Argentina"))
                .build());
    }

    /**
     * Prueba para verificar que al buscar una provincia por un ID existente, se retorna la provincia correspondiente.
     */
    @Test
    public void buscarPorId_conIdExistente_retornaProvincia() {
        Provincia provincia = provincias.get(0);
        long idExistente = 1L;

        when(provinciaRepository.findById(provincia.getId()))
                .thenReturn(Optional.of(provincia));

        Provincia resultado = provinciaService.buscarPorId(idExistente);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(idExistente);
    }
    
    /**
     * Prueba para verificar que al buscar una provincia por un ID inexistente,
     * se retorna null.
     */
    @Test
    public void buscarPorId_conIdInexistente_retornaNull() {
        long idInexistente = 2L;

        when(provinciaRepository
                .findById(idInexistente))
                .thenReturn(Optional.empty());

        Provincia resultado = provinciaService.buscarPorId(2L);

        assertThat(resultado).isNull();
    }
    
    /**
     * Prueba para verificar que al buscar provincias por nombre caseInsensitive con un
     * nombre válido, debería devolver una lista de provincias.
     */
    @Test
    public void buscarProvinciasPorNombreExacto_CuandoNombreEsValido_DeberiaDevolverListaDeProvincias() {
        String nombre = "BuEnOs AirEs";

        when(provinciaRepository.findByNombreIgnoreCase(nombre))
                .thenReturn(provincias);

        List<Provincia> provinciasEncontradas = provinciaService
                .buscarProvinciasPorNombreExacto(nombre);

        assertThat(provinciasEncontradas).isEqualTo(provincias);
        verify(provinciaRepository, times(1))
                                            .findByNombreIgnoreCase(nombre);
    }

    /**
     * Prueba para verificar que al buscar provincias por nombre con un
     * nombre nulo, debería lanzar IllegalArgumentException.
     */
    @Test
    public void buscarProvinciasPorNombreExacto_CuandoNombreEsNull_DeberiaLanzarIllegalArgumentException() {
        String nombre = null;

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> provinciaService
                .buscarProvinciasPorNombreExacto(nombre));
    }

    /**
     * Prueba para verificar que al buscar provincias por nombre con un
     * nombre que tiene menos de 3 caracteres, debería lanzar
     * IllegalArgumentException.
     */
    @Test
    public void buscarProvinciasPorNombreExacto_NombreTieneMenosDe3Caracteres_ThrowsIllegalArgumentException() {
        String nombre = "AB";

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> provinciaService
                .buscarProvinciasPorNombreExacto(nombre));
    }

    /**
     * Prueba para verificar que al obtener provincias por el nombre del país
     * con un nombre válido, debería retornar una lista de provincias.
     */
    @Test
    public void obtenerProvinciasPorNombrePais_conNombre_RetornarListaDeProvincias() {
        String nombrePais = "Argentina";

        when(provinciaRepository.findByNombrePais(nombrePais))
                .thenReturn(provincias);

        List<Provincia> result = provinciaService
                .obtenerProvinciasPorNombrePais(nombrePais);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);

        //Verificamos que se haya llamado al método del repositorio
        //Con parametro correcto
        verify(provinciaRepository, times(1))
                .findByNombrePais(nombrePais);
    }
    
    /**
     * Prueba que verifica nombrePais con valor nulo,
     * debería lanzar IllegalArgumentException.
     */
    @Test
    public void obtenerProvinciasPorNombrePais_conNombrePaisNull_deberiaLanzarExcepcion() {
        String nombrePais = null;

        assertThatExceptionOfType(IllegalArgumentException.class)
                                .isThrownBy(() -> provinciaService
                                .buscarProvinciasPorNombreExacto(nombrePais));

        // Verificamos que no se haya llamado al método del repositorio
        verify(provinciaRepository, times(0))
                                           .findByNombrePais(nombrePais);
    }

    /**
     * Verifica que el nombre del pais tenga al menos 3 caracteres,
     * si no debería lanzar IllegalArgumentException.
     */
    @Test
    public void obtenerProvinciasPorNombrePais_conNombrePaisMenosDe3Caracteres_deberiaLanzarExcepcion() {
        String nombrePais = "la";

        assertThatExceptionOfType(IllegalArgumentException.class)
                                .isThrownBy(() -> provinciaService
                                .buscarProvinciasPorNombreExacto(nombrePais));

        // Verificamos que no se haya llamado al método del repositorio
        verify(provinciaRepository, times(0))
                                            .findByNombrePais(nombrePais);
    }
    
    /**
     * Prueba para verificar que al intentar guardar una provincia con datos
     * válidos, debería guardar la provincia correctamente.
     */
    @Test
    public void guardarProvincia_conProvinciaValida_deberiaGuardar() {
        Provincia provincia = provincias.get(0);
        provincia.setId(null);
        
        assertThat(provincia.getId()).isNull();
        
        Provincia provinciaRespuesta = Provincia.builder()
                                .id(1L)
                                .nombre("Buenos Aires")
                                .pais(new Pais(1L, "Argentina"))
                                .build();
        
        when(provinciaRepository.save(provincia))
                            .thenReturn(provinciaRespuesta);
        
        Provincia provinciaGuardada = provinciaService
                                    .guardarProvincia(provincia);

        assertThat(provinciaGuardada).isNotNull();
        assertThat(provinciaGuardada.getId()).isNotNull();
        
        verify(provinciaRepository, times(1))
                                            .save(provincia);
    }
    
    /**
     * Prueba para verificar que al intentar guardar una provincia nula, debería
     * lanzar IllegalArgumentException.
     */
    @Test
    public void guardarProvincia_conProvinciaNull_deberiaLanzarExcepcion() {
        Provincia provinciaNull = null;

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> provinciaService
                .guardarProvincia(provinciaNull));

        // Verificamos que no se haya llamado al método del repositorio
        verify(provinciaRepository, times(0))
                .save(provinciaNull);
    }
    
    /**
     * Prueba para verificar que al intentar guardar una provincia con nombre
     * nulo, debería lanzar IllegalArgumentException.
     */
    @Test
    public void guardarProvincia_conNombreProvinciaNull_deberiaLanzarExcepcion() {
        Provincia provincia = provincias.get(0);
        provincia.setNombre(null);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> provinciaService
                .guardarProvincia(provincia));

        // Verificamos que no se haya llamado al método del repositorio
        verify(provinciaRepository, times(0))
                .save(provincia);
    }
    
    /**
     * Prueba para verificar que al intentar guardar una provincia con nombre
     * con menos de 3 caracteres, debería lanzar IllegalArgumentException.
     */
    @Test
    public void guardarProvincia_nombreMenor3Caracteres_deberiaLanzarExcepcion() {
        Provincia provincia = provincias.get(0);
        provincia.setNombre("hi");

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> provinciaService
                .guardarProvincia(provincia));

        // Verificamos que no se haya llamado al método del repositorio
        verify(provinciaRepository, times(0))
                .save(provincia);
    }
   
    /**
     * Prueba para comprobar que el service devuelve la provincia ingresada
     */
    @Test
    public void modificarProvincia_conProvinciaValida_deberiaModificarYDevolverProvincia() {    
        Provincia provinciaExistente = provincias.get(0);
 
        when(provinciaRepository.save(provinciaExistente)).thenReturn(provinciaExistente);


        Provincia provinciaModificada = provinciaService.modificarProvincia(provinciaExistente);


        // Verificamos que el repositorio haya sido invocado para guardar la provincia
        verify(provinciaRepository, times(1)).save(provinciaExistente);

        // Verificamos que la provincia devuelta sea la misma que la existente
        assertThat(provinciaModificada).isNotNull();
        assertThat(provinciaModificada.getId()).isEqualTo(provinciaExistente.getId());
        assertThat(provinciaModificada.getNombre()).isEqualTo(provinciaExistente.getNombre());
        assertThat(provinciaModificada.getPais()).isEqualTo(provinciaExistente.getPais());
    }
    
    /**
     * Prueba para verificar que si recibe null, arroje una exepcion
     */
    @Test
    public void modificarProvincia_conProvinciaNull_deberiaLanzarExcepcion() {
        // Act y Assert
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> provinciaService.modificarProvincia(null));

        // Verificamos que el repositorio no haya sido invocado
        verify(provinciaRepository, Mockito.never()).save(Mockito.any());
    }
    
    /**
     * Prueba para verificar que si recibe nombre provincia null, arroje una exepcion
     */
    @Test
    public void modificarProvincia_conNombreProvinciaNull_deberiaLanzarExcepcion() {
        Provincia provinciaConNombreNull = provincias.get(0);
        provinciaConNombreNull.setNombre(null);
        
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> provinciaService.modificarProvincia(provinciaConNombreNull));

        verify(provinciaRepository, Mockito.never()).save(Mockito.any());
    }

    /**
     * Prueba para verificar que si recibe nombre menor a 3 caracteres, arroje una
     * exepcion
     */
    @Test
    public void modificarProvincia_conNombreProvinciaCorto_deberiaLanzarExcepcion() {
        Provincia provinciaConNombreCorto = provincias.get(0);
        provinciaConNombreCorto.setNombre("ab");

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> provinciaService.modificarProvincia(provinciaConNombreCorto));

        // Verificamos que el repositorio no haya sido invocado
        verify(provinciaRepository, Mockito.never()).save(Mockito.any());
    }
    
    /**
     * Verifica si se llama correctamente al metodo deleteById del repositorio.
     */
    @Test
    public void eliminarProvincia_conIdvalido_deberiaEliminarProvincia() {
        long provinciaAEliminarId = provincias.get(0).getId();
        
        //Como devuelve void , tenemos que simular el comportamiento de alguna manera.
        doNothing().when(provinciaRepository).deleteById(provinciaAEliminarId);

        provinciaService.eliminarProvincia(provinciaAEliminarId);
        //tdd, verificamos que falle sin forzarlo
        //Creacion minima es parte del primer paso.
        
        //segundo paso implementacion de codigo y que ande todo
        //refactor tercero
        
        verify(provinciaRepository, times(1)).deleteById(provinciaAEliminarId);
    }
    
    /**
     * Prueba para ver si devuelve correctamente del repositorio a las listas
     */
    @Test
    public void buscarTodasLasProvincias_deberiaDevolverListaDeProvincias() {
        List<Provincia> provincias = Arrays.asList(
                new Provincia(1L, "Buenos Aires", new Pais(1L, "Argentina")),
                new Provincia(2L, "Córdoba", new Pais(1L, "Argentina"))
        );

        when(provinciaRepository.findAll()).thenReturn(provincias);

        List<Provincia> resultado = provinciaService.buscarTodasLasProvincias();

        assertThat(resultado).isNotNull();
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Buenos Aires");
        assertThat(resultado.get(1).getId()).isEqualTo(2L);
        assertThat(resultado.get(1).getNombre()).isEqualTo("Córdoba");
    }

}
