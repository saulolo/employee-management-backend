package edu.employeemanagement.exception;


import org.springframework.http.HttpStatus;

import static edu.employeemanagement.util.Constants.DUPLICATE_EMAIL;

/**
 * Excepción lanzada cuando se intenta registrar un email duplicado.
 * Mapea a HTTP 409 CONFLICT.
 */
public class DuplicateEmailException extends ApiException {

    /**
     * Constructor con email duplicado.
     *
     * @param email email que ya existe en el sistema
     */
    public DuplicateEmailException(String email) {
        super(
                String.format("El email '%s' ya está registrado en el sistema.", email),
                HttpStatus.CONFLICT,
                DUPLICATE_EMAIL
        );
    }
}
