package edu.employeemanagement.controller;

import edu.employeemanagement.domain.dto.request.EmployeeRequestDTO;
import edu.employeemanagement.domain.dto.response.ApiResponseDTO;
import edu.employeemanagement.domain.dto.response.EmployeeResponseDTO;
import edu.employeemanagement.service.interfaces.IEmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.employeemanagement.util.Constants.*;

@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final IEmployeeService employeeService;


    public EmployeeController(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    /**
     * Crea un nuevo empelado
     *
     * @param employeeRequestDTO datos del empelado
     * @return empleado creado
     */
    @PostMapping
    public ResponseEntity<ApiResponseDTO<EmployeeResponseDTO>> createEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        EmployeeResponseDTO createdEmployee = employeeService.createEmployee(employeeRequestDTO);
        ApiResponseDTO<EmployeeResponseDTO> response = ApiResponseDTO.success(
                createdEmployee,
                EMPLOYEE_CREATED
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Obtiene todos los empleados
     *
     * @return lista de empleados
     */
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<EmployeeResponseDTO>>> getAllEmployees() {
        List<EmployeeResponseDTO> employees = employeeService.findAllEmployees();

        ApiResponseDTO<List<EmployeeResponseDTO>> response = ApiResponseDTO.success(
                employees,
                EMPLOYEES_RETRIEVED
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un empleado por su ID
     *
     * @param id identificador único del empleado
     * @return el empleado encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<EmployeeResponseDTO>> getEmployeeById(@PathVariable Long id) {
        EmployeeResponseDTO employeeResponseDTO = employeeService.findEmployeeById(id);

        ApiResponseDTO<EmployeeResponseDTO> response = ApiResponseDTO.success(
                employeeResponseDTO,
                EMPLOYEE_FOUND
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene un empleado por su email
     *
     * @param email correo electrónico del empleado
     * @return el empleado encontrado
     */
    @GetMapping("/by-email")
    public ResponseEntity<ApiResponseDTO<EmployeeResponseDTO>> getEmployeeByEmail(@RequestParam String email) {
        EmployeeResponseDTO employeeResponseDTO = employeeService.findEmployeeByEmail(email);

        ApiResponseDTO<EmployeeResponseDTO> response = ApiResponseDTO.success(
                employeeResponseDTO,
                EMPLOYEE_FOUND
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Actualiza un empleado existente
     * @param id identifcador unico del empleado
     * @param employeeRequestDTO nuevos datos del empleado
     * @return empleado actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<EmployeeResponseDTO>> updateEmployee(@PathVariable Long id,
                                                                              @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        EmployeeResponseDTO employeeResponseDTO = employeeService.updateEmployee(id, employeeRequestDTO);

        ApiResponseDTO<EmployeeResponseDTO> response = ApiResponseDTO.success(
                employeeResponseDTO,
                EMPLOYEE_UPDATED
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Eliminado un empleado por su ID
     * @param id identificador unico del empleado
     * @return confirmación de eliminación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployeeById(id);

        ApiResponseDTO<Void> response = ApiResponseDTO.success(
                null,
                EMPLOYEE_DELETED
        );

        log.info("Empleado con ID {} eliminado exitosamente. ", id);
        return ResponseEntity.ok(response);
    }

}
