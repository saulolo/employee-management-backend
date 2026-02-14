package edu.employeemanagement.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonPropertyOrder({"idEmployee", "name", "lastname", "email", "createdAt", "updateAt"})
@JsonInclude(JsonInclude.Include.NON_NULL)  // ← No incluir campos null en JSON
public record EmployeeResponseDTO(
        Long idEmployee,
        String name,
        String lastname,
        String email,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime updatedAt) {
}
