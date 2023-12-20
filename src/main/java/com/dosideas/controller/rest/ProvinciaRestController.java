package com.dosideas.controller.rest;

import com.dosideas.domain.Provincia;
import com.dosideas.dto.ProvinciaDTO;
import com.dosideas.dto.ProvinciaPaisDTO;
import com.dosideas.service.IProvinciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Controller que maneja Requests relacionadas a Provincias
 * Raiz: localhost:8080/api/provincia
 * 
 * @author Gabriel Romero
 */
@RestController
@RequestMapping("/api/provincia")
public class ProvinciaRestController {

    @Autowired
    private IProvinciaService provinciaService;

     /**
     * Busca una provincia por su ID.
     *
     * @param id El ID de la provincia a buscar.
     * @return ResponseEntity con la ProvinciaDTO si se encuentra, o not found si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProvinciaDTO> buscarProvinciaPorId(@PathVariable Long id) {
        //Asegurar que no llegue un valor null de id a service ya que es long.
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<Provincia> provincia = Optional.ofNullable(provinciaService.buscarPorId(id));
        
        //Metodo de Optional, si esta vacio o no
        return provincia.map(p -> ResponseEntity.status(HttpStatus.OK).body(ProvinciaDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .pais(p.getPais().getNombre())
                .build()))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

     /**
     * Busca provincias por su nombre exacto.
     *
     * @param nombre El nombre exacto de la provincia a buscar. CaseInsesitive
     * @return ResponseEntity con la lista de ProvinciaDTO si se encuentran, o not found si no hay coincidencias.
     */
    @GetMapping()
    public ResponseEntity<List<ProvinciaDTO>> buscarProvinciasPorNombre(
            @RequestParam String nombre) {
        List<Provincia> provincias = provinciaService.buscarProvinciasPorNombreExacto(nombre);

        // Mapear cada Provincia a ProvinciaDTO
        List<ProvinciaDTO> provinciasDTO = provincias.stream()
                .map(provincia -> new ProvinciaDTO(
                        provincia.getId(),
                        provincia.getNombre(),
                        provincia.getPais().getNombre()))
                .collect(Collectors.toList());

        return ResponseEntity.status(provinciasDTO.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK)
            .body(provinciasDTO);
    }

     /**
     * Obtiene provincias por el nombre del país Completo.
     *
     * @param nombre nombre Pais entero en el que se encuentra la provincia
     * @return ResponseEntity con la lista de ProvinciaPaisDTO si se encuentran, o not found si no hay coincidencias.
     */
    @GetMapping("/pais")
    public ResponseEntity<List<ProvinciaPaisDTO>> obtenerProvinciasPorPais(
            @RequestParam String nombre) {

        List<Provincia> provincias = provinciaService.obtenerProvinciasPorNombrePais(nombre);

        // Mapear cada Provincia a ProvinciaPaisDTO
        List<ProvinciaPaisDTO> provinciasDTO = provincias.stream()
                .map(provincia -> ProvinciaPaisDTO.builder()
                        .id(provincia.getId())
                        .nombre(provincia.getNombre())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.status(provinciasDTO.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK)
            .body(provinciasDTO);
    }
    
    
    /**
     * Guarda una nueva provincia En el ID del Pais marcado.
     *
     * @param provincia La provincia a ser guardada.
     * @return ResponseEntity con el objeto Provincia guardado y el código de
     * estado HTTP 201 (CREATED).
     */
    @PostMapping("/guardar")
    public ResponseEntity<Provincia> guardarProvincia(@RequestBody Provincia provincia) {
        
        Provincia provinciaGuardada = provinciaService.guardarProvincia(provincia);

        return ResponseEntity.status(HttpStatus.CREATED).body(provinciaGuardada);
    }
    
    /**
     * Actualiza una provincia existente.
     *
     * @param provinciaAActualizar Provincia con los nuevos datos.
     * @return ResponseEntity con el resultado de la operación.
     */
    @PutMapping()
    public ResponseEntity<Provincia> actualizarProvincia(
            @RequestBody Provincia provinciaAActualizar) {//Spring verifica el id Aut.
        
        //Verificamos que no sea null el id, porque buscar por id es long
        if (provinciaAActualizar == null || provinciaAActualizar.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Provincia provinciaExistente = provinciaService.buscarPorId(provinciaAActualizar.getId());
        if (provinciaExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // En nuestro caso solo cambia el nombre
        provinciaExistente.setNombre(provinciaAActualizar.getNombre());

        // Guardar la provincia actualizada
        Provincia provinciaGuardada = provinciaService.modificarProvincia(provinciaExistente);

        return ResponseEntity.status(HttpStatus.OK).body(provinciaGuardada);
    }
    
    /**
     * Elimina una provincia por su ID.
     *
     * @param id Provincia a eliminar
     * @return HttpStatus OK si se logro eliminar la provincia o 
     * Bad request si el id es null
     */
    //si no se pasa parametro por defecto tira 404
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> eliminarEmpleado(@PathVariable Long id) {
//        provinciaService.eliminarProvincia(id);
//        return ResponseEntity.status(HttpStatus.OK).body("Provincia Eliminada Correctamente");
//    }
    
     /**
     * Trae todas las provincias
     * @return una response personalizada con DTO.
     * Devolviendo solamente id, nombre y nombre pais
     */
    @GetMapping("/todos")
    public List<ProvinciaDTO> listarLasProvincias() {
        List<Provincia> provincias = provinciaService.buscarTodasLasProvincias();

        // Mapear cada Provincia a ProvinciaDTO
        List<ProvinciaDTO> provinciasDTO = provincias.stream()
                .map(provincia -> new ProvinciaDTO(
                provincia.getId(),
                provincia.getNombre(),
                provincia.getPais().getNombre()))
                .collect(Collectors.toList());

        return provinciasDTO;
    }
    
}
