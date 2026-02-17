package edu.employeemanagement.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"success", "message", "errorCode", "data", "errors","timestamp", "path"})
public record ApiResponseDTO<T>(
        boolean success,
        String message,
        String errorCode,
        T data,
        Map<String, String> errors,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime timestamp,
        String path) {


    // ========== FACTORY METHODS PARA RESPUESTAS EXITOSAS ==========
    /**
     * Crea una respuesta exitosa con datos.
     *
     * @param data datos de la respuesta
     * @param message mensaje descriptivo
     * @param <T> tipo de datos
     * @return ApiResponseDTO de éxito con datos
     */
    public static <T> ApiResponseDTO<T> success(T data, String message) {
        return ApiResponseDTO.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Crea una respuesta exitosa sin datos (ej: DELETE).
     *
     * @param message mensaje descriptivo
     * @param <T> tipo de datos (será Void)
     * @return ApiResponseDTO de éxito sin datos
     */
    public static <T> ApiResponseDTO<T> success(String message) {
        return ApiResponseDTO.<T>builder()
                .success(true)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // ========== FACTORY METHODS PARA ERRORES ==========

    /**
     * Crea una respuesta de error simple.
     *
     * @param message mensaje de error para el usuario
     * @param errorCode código técnico del error
     * @param path endpoint que generó el error
     * @param <T> tipo de datos (será Void en errores)
     * @return ApiResponseDTO de error
     */
    public static <T> ApiResponseDTO<T> error(
            String message,
            String errorCode,
            String path
    ) {
        return ApiResponseDTO.<T>builder()
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }

    /**
     * Crea una respuesta de error con detalles de validación.
     *
     * @param message mensaje general de error
     * @param errors mapa con errores por campo
     * @param path endpoint que generó el error
     * @param <T> tipo de datos (será Void en errores)
     * @return ApiResponseDTO de error con detalles de validación
     */
    public static <T> ApiResponseDTO<T> validationError(
            String message,
            Map<String, String> errors,
            String path
    ) {
        return ApiResponseDTO.<T>builder()
                .success(false)
                .message(message)
                .errorCode("VALIDATION_ERROR")
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }


}


