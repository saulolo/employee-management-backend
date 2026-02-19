package edu.employeemanagement.service.interfaces;

import edu.employeemanagement.domain.dto.request.EmployeeRequestDTO;
import edu.employeemanagement.domain.dto.response.EmployeeResponseDTO;
import edu.employeemanagement.exception.ResourceNotFoundException;
import edu.employeemanagement.exception.DuplicateEmailException;

import java.util.List;

public interface IEmployeeService {


    /**
     * Crea un nuevo empleado en el sistema
     * <p>Valida que el email sea único antes de crear el registro.</p>
     *
     * @param employeeRequestDTO datos del empelado a crear
     * @return un EmployeeResponseDTO con los datos del empleado creado
     * @throws DuplicateEmailException si el email ya existe
     *
     */
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO);

    /**
     * Obtiene la lista de todos los empleados creados
     *
     * @return lista de los estudiantes creados mediante EmployeeResponseDTO
     */
    List<EmployeeResponseDTO> findAllEmployees();

    /**
     * Obtiene un empleado por su ID
     *
     * @param id identificador único del empleado
     * @return un empleado por su ID mediante un EmployeeResponseDTO
     * @throws ResourceNotFoundException si no se encuentra el empleado
     */
    EmployeeResponseDTO findEmployeeById(Long id);

    /**
     * Obtiene un empleado por su email
     *
     * @param email correo electrónico del empleado
     * @return EmployeeResponseDTO con los datos del empleado
     */
    EmployeeResponseDTO findEmployeeByEmail(String email);

    /**
     * Actualiza los datos de un empleado existente
     *
     * @param id                 identificador único del empleado
     * @param employeeRequestDTO datos actualizados del empelado
     * @return EmployeeResponseDTO con los datos del empleado actualizado
     * @throws ResourceNotFoundException si no se encuentra el empleado
     * @throws DuplicateEmailException si el nuevo email ya está en uso
     */
    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employeeRequestDTO);


    /**
     * Elimina un empleado del sistema por su ID
     *
     * @param id identificador único del empleado
     * @throws ResourceNotFoundException si no se encuentra el empleado
     */
    void deleteEmployeeById(Long id);
}
