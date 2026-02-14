package edu.employeemanagement.domain.mapper;

import edu.employeemanagement.domain.dto.request.EmployeeRequestDTO;
import edu.employeemanagement.domain.dto.response.EmployeeResponseDTO;
import edu.employeemanagement.domain.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Componente encargado de convertir la entidad {@link Employee}
 * a suo DTO {@link EmployeeResponseDTO} asociado.
 */
@Component
public class EmployeeMapper {


    // ========== ENTITY → RESPONSE DTO ==========

    /**
     * Convierte un objeto {@link Employee} a {@link EmployeeResponseDTO}.
     *
     * @param employee la entidad Employee a convertir.
     * @return EmployeeResponseDTO resultante o null si la entrada es null.
     */
    public EmployeeResponseDTO toEmployeeResponseDTO(Employee employee) {
        if (employee == null) {
            return null;
        }
        return EmployeeResponseDTO.builder()
                .idEmployee(employee.getIdEmployee())
                .name(employee.getName())
                .lastname(employee.getLastname())
                .email(employee.getEmail())
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .build();
    }

    /**
     * Convierte una lista de entidades {@link Employee} a una lista de {@link EmployeeResponseDTO}.
     *
     * @param employeeList lista de entidades Employee a convertir.
     * @return lista de DTOs (puede ser vacía, nunca null).
     */
    public List<EmployeeResponseDTO> toEmployeeResponseList(List<Employee> employeeList) {
        if (employeeList == null || employeeList.isEmpty()) {
            return Collections.emptyList();
        }
        return employeeList.stream()
                .map(this::toEmployeeResponseDTO)
                .toList();
    }


    // ========== REQUEST DTO → ENTITY ==========

    /**
     * Convierte un {@link EmployeeRequestDTO} a una entidad {@link Employee}.
     * Usado para CREAR un nuevo empleado.
     *
     * @param employeeRequestDTO el DTO con los datos del empleado.
     * @return entidad Employee sin ID (será asignado por la BD).
     */
    public Employee toEntity(EmployeeRequestDTO employeeRequestDTO) {
        if (employeeRequestDTO == null) {
            return null;
        }

        return Employee.builder()
                .name(employeeRequestDTO.getName())
                .lastname(employeeRequestDTO.getLastname())
                .email(employeeRequestDTO.getEmail())
                .build();
    }

    /**
     * Actualiza una entidad {@link Employee} existente con datos de {@link EmployeeRequestDTO}
     * Usado para EDITAR un empleado.
     *
     * @param employee la entidad existente a actualizar.
     * @param employeeRequestDTO el DTO con los nuevos datos.
     */
    public void updateEntityFromDTO(Employee employee, EmployeeRequestDTO employeeRequestDTO) {
        if (employee != null &&  employeeRequestDTO!= null) {
            employee.setName(employeeRequestDTO.getName());
            employee.setLastname(employeeRequestDTO.getLastname());
            employee.setEmail(employeeRequestDTO.getEmail());
        }
    }

}
