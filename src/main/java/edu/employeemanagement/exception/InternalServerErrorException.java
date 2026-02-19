package edu.employeemanagement.exception;

import edu.employeemanagement.handler.GlobalExceptionHandler;
import org.springframework.http.HttpStatus;

import static edu.employeemanagement.util.Constants.INTERNAL_SERVER_ERROR;

/**
 * Excepción para errores internos del servidor (500).
 *
 * <p><b>⚠️ Usar solo en casos excepcionales.</b>
 * La mayoría de errores inesperados son manejados automáticamente
 * por {@link GlobalExceptionHandler}.</p>
 */
public class InternalServerErrorException extends ApiException {

    /**
     * Constructor con mensaje de error.
     *
     * @param message descripción del error interno
     */
    public InternalServerErrorException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
    }

    /**
     * Constructor con mensaje y causa raíz.
     *
     * @param message descripción del error interno
     * @param cause   excepción original que causó el error
     */
    public InternalServerErrorException(String message, Throwable cause) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR, cause);
    }
}
