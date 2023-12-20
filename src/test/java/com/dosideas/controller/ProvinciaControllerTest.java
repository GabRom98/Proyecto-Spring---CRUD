package com.dosideas.controller;

import com.dosideas.controller.rest.ProvinciaRestController;
import com.dosideas.domain.Pais;
import com.dosideas.domain.Provincia;
import com.dosideas.service.IProvinciaService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.any;

/**
 * Pruebas unitarias para el controlador {@link ProvinciaRestController}. Estas
 * pruebas se centran en la lógica de la interacción HTTP para las operaciones
 * relacionadas con provincias.
 *
 * @author Gabriel Romero
 */
@WebMvcTest(ProvinciaRestController.class)
public class ProvinciaControllerTest {

    @Autowired
    private MockMvc mockMvc; // Inyecta una instancia de MockMvc para realizar solicitudes HTTP simuladas.

    @MockBean
    private IProvinciaService provinciaService; // Simula el servicio de provincias.

    @Autowired
    private ObjectMapper objectMapper; // Convertidor de objetos a JSON.

    /**
     * Prueba para verificar que al buscar una provincia por su ID existente, se
     * retorna una respuesta HTTP 200 (OK).
     *
     * @throws Exception Si hay algún error durante la ejecución de la prueba.
     */
    @Test
    public void buscarPorId_conIdExistente_retornaProvincia() throws Exception {
        Mockito.when(provinciaService.buscarPorId(Mockito.anyLong()))
                .thenReturn(new Provincia(1L, "Cordoba", new Pais()));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/provincia/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    /**
     * Prueba para verificar que al buscar una provincia por un ID nulo, se
     * retorna una respuesta HTTP 400 (Bad Request).
     *
     * @throws Exception Si hay algún error durante la ejecución de la prueba.
     */
    @Test
    public void buscarPorId_conIdNull_retornaBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/provincia/null")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    /**
     * Prueba para verificar que al buscar una provincia por un ID inexistente,
     * se retorna una respuesta HTTP 404 (Not Found).
     *
     * @throws Exception Si hay algún error durante la ejecución de la prueba.
     */
    @Test
    public void buscarPorId_conIdInexistente_retornaNotFound() throws Exception {
        Mockito.when(provinciaService.buscarPorId(Mockito.anyLong()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/provincia/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    /**
     * Prueba para verificar que al buscar provincias por un nombre Provincia
     * inexistente, se retorna una respuesta HTTP 404 (Not Found).
     *
     * @throws Exception Si hay algún error durante la ejecución de la prueba.
     */
    @Test
    public void buscarProvinciasPorNombre_conStringIncorrecto_retornaNotFound() throws Exception {
        Mockito.when(provinciaService.buscarProvinciasPorNombreExacto(Mockito.anyString()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/provincia")
                .param("nombre", "hola")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    /**
     * Prueba para verificar que al buscar provincias por un nombre correcto, se
     * retorna una respuesta HTTP 200 (OK) con la lista de provincias
     * correspondientes.
     *
     * @throws Exception Si hay algún error durante la ejecución de la prueba.
     */
    @Test
    public void buscarProvinciasPorNombre_conStringCorrecto_retornaPaises() throws Exception {
        List<Provincia> provincias = Arrays.asList(
                new Provincia(1L, "Provincia1", new Pais(1L, "Pais1")),
                new Provincia(2L, "Provincia2", new Pais(1L, "Pais1"))
        );

        Mockito.when(provinciaService.buscarProvinciasPorNombreExacto(Mockito.anyString()))
                .thenReturn(provincias);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/provincia")
                .param("nombre", "nombreEjemplo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // verifica que el cuerpo de la respuesta contiene la cantidad esperada de provincias.
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(provincias.size()))
                .andReturn();
    }

    /**
     * Prueba para verificar que al obtener provincias por un país válido, se
     * retorna una respuesta HTTP 200 (OK) con la lista de provincias
     * correspondientes.
     *
     * @throws Exception Si hay algún error durante la ejecución de la prueba.
     */
    @Test
    public void obtenerProvinciasPorPais_paisValido_retornaPaises() throws Exception {
        List<Provincia> provinciasEncontradas = Arrays.asList(
                new Provincia(1L, "Provincia1", new Pais(1L, "Pais1")),
                new Provincia(2L, "Provincia2", new Pais(1L, "Pais1"))
        );

        Mockito.when(provinciaService.obtenerProvinciasPorNombrePais(Mockito.anyString()))
                .thenReturn(provinciasEncontradas);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/provincia/pais")
                .param("nombre", "nombrePaisExistente")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(provinciasEncontradas.size()))
                .andReturn();
    }

    /**
     * Prueba para verificar que al obtener provincias por un país no existente
     * o incompleto, se retorna una respuesta HTTP 404 (Not Found).
     *
     * @throws Exception Si hay algún error durante la ejecución de la prueba.
     */
    @Test
    public void obtenerProvinciasPorPais_paisInvalido_retornaNotFound() throws Exception {

        List<Provincia> provinciasNoEncontradas = Collections.emptyList();

        Mockito.when(provinciaService.obtenerProvinciasPorNombrePais(Mockito.anyString()))
                .thenReturn(provinciasNoEncontradas);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/provincia/pais")
                .param("nombre", "nombrePaisNoExistente")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    /**
     * Prueba para verificar que al guardar una provincia válida, se retorna una
     * respuesta HTTP 201 (Created) con la provincia guardada y sus detalles.
     *
     * Razon por la cual no se pide en el objeto Provincia el nombre del pais,
     * es porque esta misma tiene @JsonIgnoreProperties("nombre")
     *
     * @throws Exception Si hay algún error durante la ejecución de la prueba.
     */
    @Test
    public void testGuardarProvincia() throws Exception {
        // Datos de ejemplo para la provincia que se va a guardar
        Provincia provinciaParaGuardar = new Provincia(null, "Nueva Provincia", new Pais(1L, "Argentina"));
        Provincia provinciaGuardada = new Provincia(1L, "Nueva Provincia", new Pais(1L, "Argentina"));

        Mockito.when(provinciaService.guardarProvincia(Mockito.any(Provincia.class)))
                .thenReturn(provinciaGuardada);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/provincia/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(provinciaParaGuardar)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                // En la respuesta se espera:
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("Nueva Provincia"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pais.id").value(1L))
                .andReturn();
    }

    /**
     * Verifica si el controlador actualiza correctamente una provincia
     * existente con una provincia válida.
     *
     * Escenario: - Se actualiza una provincia existente con un nombre
     * modificado.
     *
     * Resultado Esperado: - Código de estado 200 OK. - El nombre de la
     * provincia en la respuesta debe ser el nuevo nombre modificado.
     *
     * @throws Exception Si hay algún error durante la ejecución de la prueba.
     */
    @Test
    public void actualizarProvincia_conProvinciaValida_retornaProvinciaActualizada() throws Exception {
        Provincia provinciaExistente = new Provincia(21L, "Tierra del Fuego", new Pais(1L, "Argentina"));
        Provincia provinciaAActualizar = new Provincia(21L, "SE MODIFICÓ TIERRA DEL FUEGO", new Pais(1L, "Argentina"));

        Mockito.when(provinciaService.buscarPorId(provinciaAActualizar.getId())).thenReturn(provinciaExistente);
        Mockito.when(provinciaService.modificarProvincia(any(Provincia.class))).thenReturn(provinciaAActualizar);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/provincia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(provinciaAActualizar)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value("SE MODIFICÓ TIERRA DEL FUEGO"));
    }

    /**
     * Verifica si el controlador maneja correctamente el caso en el que se
     * proporciona una provincia nula.
     *
     * Escenario: - Se intenta actualizar con una provincia nula.
     *
     * Resultado Esperado: - Código de estado 400 Bad Request.
     *
     * @throws Exception Si hay algún error durante la ejecución de la prueba.
     */
    @Test
    public void actualizarProvincia_conProvinciaNula_retornaBadRequest() throws Exception {
        // Act y Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/provincia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Verifica si el controlador maneja correctamente el caso en el que se
     * proporciona una provincia con un ID nulo.
     *
     * Escenario: - Se intenta actualizar con una provincia que tiene un ID
     * nulo.
     *
     * Resultado Esperado: - Código de estado 400 Bad Request.
     *
     * @throws Exception Si hay algún error durante la ejecución de la prueba.
     */
    @Test
    public void actualizarProvincia_conIdNulo_retornaBadRequest() throws Exception {
        // Arrange
        Provincia provinciaAActualizar = new Provincia(null, "SE MODIFICÓ TIERRA DEL FUEGO", new Pais(1L, "Argentina"));

        // Act y Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/provincia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(provinciaAActualizar)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * Verifica si el controlador maneja correctamente el caso en el que se
     * intenta actualizar una provincia inexistente.
     *
     * Escenario: - Se intenta actualizar una provincia que no existe.
     *
     * Resultado Esperado: - Código de estado 404 Not Found.
     *
     * @throws Exception Si hay algún error durante la ejecución de la prueba.
     */
    @Test
    public void actualizarProvincia_conProvinciaInexistente_retornaNotFound() throws Exception {
        Provincia provinciaAActualizar = new Provincia(1L, "SE MODIFICÓ TIERRA DEL FUEGO", new Pais(1L, "Argentina"));
        
        Mockito.when(provinciaService.buscarPorId(Mockito.anyLong()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/provincia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(provinciaAActualizar)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Mockito.verify(provinciaService, Mockito.times(1)).buscarPorId(Mockito.anyLong());
        Mockito.verify(provinciaService, Mockito.never()).modificarProvincia(Mockito.any(Provincia.class));
    }
    
//    @Test
//    public void eliminarProvincia_conIdValido_deberiaEliminarYDevolverOK() throws Exception {
//        Long idProvincia = 1L;
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .delete("/api/provincia/{id}", idProvincia))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        Mockito.verify(provinciaService, Mockito.times(1)).eliminarProvincia(idProvincia);
//    }
//
//    @Test
//    public void eliminarProvincia_conIdNull_deberiaDevolverBadRequest() throws Exception {
//
//        mockMvc.perform(MockMvcRequestBuilders
//                // el compilador necesita entender que estás proporcionando 
//                // un objeto nulo y no simplemente un valor nulo
//                .delete("/api/provincia/{id}", (Object) null))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//                
//        Mockito.verify(provinciaService, Mockito.never()).eliminarProvincia(Mockito.anyLong());
//    }
    
    /**
     * Verifica si devuelve la lista, y revisa si se duelven los mismos objetos
     * en formato DTO.
     *
     * @throws Exception Si hay algún error durante la ejecución de la prueba.
     */
    @Test
    public void listarLasProvincias_alHacerUnaRequest_deberiaRetornarListaDTO() throws Exception {

        List<Provincia> provincias = Arrays.asList(
                new Provincia(1L, "Buenos Aires", new Pais(1L, "Argentina")),
                new Provincia(2L, "Córdoba", new Pais(1L, "Argentina"))
        );

        Mockito.when(provinciaService.buscarTodasLasProvincias()).thenReturn(provincias);

        // Act
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/provincia/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre", is("Buenos Aires")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pais", is("Argentina")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nombre", is("Córdoba")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].pais", is("Argentina")));

        // Verifica que el servicio haya sido invocado una vez
        Mockito.verify(provinciaService, Mockito.times(1)).buscarTodasLasProvincias();
    }

}
