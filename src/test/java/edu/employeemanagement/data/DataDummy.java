package edu.employeemanagement.data;

import edu.employeemanagement.domain.dto.request.EmployeeRequestDTO;
import edu.employeemanagement.domain.dto.response.EmployeeResponseDTO;
import edu.employeemanagement.domain.entity.Employee;

import java.time.LocalDateTime;
import java.util.List;

public class DataDummy {

    // ========== Valores por defecto ==========
    public static final Long DEFAULT_ID = 1L;
    public static final String DEFAULT_NAME = "Saultest";
    public static final String DEFAULT_LASTNAME = "Echeverritest";
    public static final String DEFAULT_EMAIL = "echeverri@exampletest.com";
    public static final LocalDateTime DEFAULT_CREATED_AT = LocalDateTime.of(2024, 2, 19, 10, 0);
    public static final LocalDateTime DEFAULT_UPDATED_AT = LocalDateTime.of(2024, 2, 19, 10, 0);

    public static final Long SECOND_ID = 2L;
    public static final String SECOND_NAME = "Felipetest";
    public static final String SECOND_LASTNAME = "Vasqueztest";
    public static final String SECOND_EMAIL = "vasquez@exampletest.com";
    public static final LocalDateTime SECOND_CREATED_AT = LocalDateTime.of(2024, 2, 19, 10, 10);
    public static final LocalDateTime SECOND_UPDATED_AT = LocalDateTime.of(2024, 2, 19, 10, 15);

    public static final Long THIRD_ID = 3L;
    public static final String THIRD_NAME = "Carlostest";
    public static final String THIRD_LASTNAME = "Ramíreztest";
    public static final String THIRD_EMAIL = "carlostest@example.com";
    public static final LocalDateTime THIRD_CREATED_AT = LocalDateTime.of(2024, 2, 19, 10, 20);
    public static final LocalDateTime THIRD_UPDATED_AT = LocalDateTime.of(2024, 2, 19, 10, 30);

    // ========== Entities ==========
    public static Employee firstEmployee() {
        return Employee.builder()
                .idEmployee(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .lastname(DEFAULT_LASTNAME)
                .email(DEFAULT_EMAIL)
                .createdAt(DEFAULT_CREATED_AT)
                .updatedAt(DEFAULT_UPDATED_AT)
                .build();
    }

    public static Employee secondEmployee() {
        return Employee.builder()
                .idEmployee(SECOND_ID)
                .name(SECOND_NAME)
                .lastname(SECOND_LASTNAME)
                .email(SECOND_EMAIL)
                .createdAt(SECOND_CREATED_AT)
                .updatedAt(SECOND_UPDATED_AT)
                .build();
    }

    public static Employee thirdEmployee() {
        return Employee.builder()
                .idEmployee(THIRD_ID)
                .name(THIRD_NAME)
                .lastname(THIRD_LASTNAME)
                .email(THIRD_EMAIL)
                .createdAt(THIRD_CREATED_AT)
                .updatedAt(THIRD_UPDATED_AT)
                .build();
    }

    // ========== DTOs Request ==========
    public static EmployeeRequestDTO defaultEmployeeRequestDTO() {
        return EmployeeRequestDTO.builder()
                .name(DEFAULT_NAME)
                .lastname(DEFAULT_LASTNAME)
                .email(DEFAULT_EMAIL)
                .build();
    }

    // ========== DTOs Response ==========
    public static EmployeeResponseDTO defaultEmployeeResponseDTO() {
        return EmployeeResponseDTO.builder()
                .idEmployee(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .lastname(DEFAULT_LASTNAME)
                .email(DEFAULT_EMAIL)
                .createdAt(DEFAULT_CREATED_AT)
                .updatedAt(DEFAULT_UPDATED_AT)
                .build();
    }

    public static EmployeeResponseDTO customEmployeeResponseDTO(Long id, String name, String lastname, String email,
                                                                LocalDateTime createdAt, LocalDateTime updatedAt) {
        return EmployeeResponseDTO.builder()
                .idEmployee(id)
                .name(name)
                .lastname(lastname)
                .email(email)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    // ========== Listas ==========
    public static List<Employee> employeeList() {
        return List.of(firstEmployee(), secondEmployee(), thirdEmployee());
    }

    public static List<EmployeeResponseDTO> employeeResponseList() {
        return List.of(
                defaultEmployeeResponseDTO(),
                customEmployeeResponseDTO(SECOND_ID, SECOND_NAME, SECOND_LASTNAME, SECOND_EMAIL, SECOND_CREATED_AT, SECOND_UPDATED_AT),
                customEmployeeResponseDTO(THIRD_ID, THIRD_NAME, THIRD_LASTNAME, THIRD_EMAIL, THIRD_CREATED_AT, THIRD_UPDATED_AT)
        );
    }
}
