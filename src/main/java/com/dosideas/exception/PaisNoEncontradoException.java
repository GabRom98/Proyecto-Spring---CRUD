package com.dosideas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción lanzada en caso de no encontrar el pais buscado.
 *
 * Por medio de la anotación @ResponseStatus indicamos que, en caso de que esta
 * excepción sea lanzada, se le asociará el código de error NOT_FOUND (404). De
 * esta forma el cliente verá dicho error.
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PaisNoEncontradoException extends Exception {

    public PaisNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
