package com.dosideas.service;

import com.dosideas.domain.Provincia;
import com.dosideas.repository.ProvinciaRepository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio que maneja operaciones relacionadas con la entidad Provincia.
 * 
 * @author Gabriel Romero
 */
@Service
@Transactional
public class ProvinciaService implements IProvinciaService {
//REFLECTION, BUSCAR. Implementacion en tiempo de ejecucion de metodos de interfaz
    @Autowired
    private ProvinciaRepository provinciaRepository;
    
    @Override
    public Provincia buscarPorId(long id) {
        return provinciaRepository.findById(id).orElse(null);
    }

    @Override
    public Provincia guardarProvincia(Provincia provincia) {
        validarProvincia(provincia);
        // Aseguramos que el ID sea null antes de intentar guardar
        provincia.setId(null);
        return provinciaRepository.save(provincia);
    }

    @Override
    public List<Provincia> obtenerProvinciasPorNombrePais(String nombrePais) {
        validarNombre(nombrePais);
        return provinciaRepository.findByNombrePais(nombrePais);
    }

    @Override
    public List<Provincia> buscarProvinciasPorNombreExacto(String nombre) {
        validarNombre(nombre);
        return provinciaRepository.findByNombreIgnoreCase(nombre);
    }
    
    @Override
    public Provincia modificarProvincia(Provincia provincia) {
        validarProvincia(provincia);

        return provinciaRepository.save(provincia);
    }
    
    @Override
    public void eliminarProvincia(long id) {
        provinciaRepository.deleteById(id);
    }
    
    @Override
    public List<Provincia> buscarTodasLasProvincias() {
        return provinciaRepository.findAll();
    }
    
    //FUNCIONES VALIDADORAS.
    private void validarNombre(String nombre) {
        if (nombre == null || nombre.length() < 3) {
            throw new IllegalArgumentException(
                    "El nombre no puede ser null y debe tener al menos 3 caracteres.");
        }
    }

    private void validarProvincia(Provincia nombreProvincia) {
        if (nombreProvincia == null || nombreProvincia.getNombre() == null
                || nombreProvincia.getNombre().length() < 3) {
            throw new IllegalArgumentException("Se debe ingresar una provincia, "
                    + "El nombre no puede ser null " + "y debe tener al menos 3 caracteres.");
        }
    }

}
