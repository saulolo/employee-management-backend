package edu.employeemanagement.service.impl;

import edu.employeemanagement.domain.dto.request.EmployeeRequestDTO;
import edu.employeemanagement.domain.dto.response.EmployeeResponseDTO;
import edu.employeemanagement.domain.entity.Employee;
import edu.employeemanagement.domain.mapper.EmployeeMapper;
import edu.employeemanagement.exception.DuplicateEmailException;
import edu.employeemanagement.exception.ResourceNotFoundException;
import edu.employeemanagement.repository.EmployeeRepository;
import edu.employeemanagement.service.interfaces.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static edu.employeemanagement.util.Constants.*;

@Slf4j
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {
        log.info("Creando empleado con email: {}", employeeRequestDTO.getEmail());
        boolean isExistingEmployee = employeeRepository.existsByEmail(employeeRequestDTO.getEmail());

        // Validar email
        if (isExistingEmployee) {
            log.warn("Intento de crear empleado con email duplicado: {} ", employeeRequestDTO.getEmail());
            throw new DuplicateEmailException(employeeRequestDTO.getEmail());
        }

        // Convertir DTO a entidad
        Employee employee = employeeMapper.toEntity(employeeRequestDTO);
        //Guarda en BD
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Empleado creado con éxito: {} ID: {}", savedEmployee.getName(), savedEmployee.getIdEmployee());
        // Convertir entidad a DTO de respuesta
        return employeeMapper.toEmployeeResponseDTO(savedEmployee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> findAllEmployees() {
        log.info("Recuperando todos los empleados.");
        List<Employee> employeeList = employeeRepository.findAll();
        log.info("Se encontraron {} empleados en el sistema.", employeeList.size());
        return employeeMapper.toEmployeeResponseList(employeeList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDTO findEmployeeById(Long id) {
        log.info("Buscando empleado con ID: {}", id);
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, id)));
        log.debug("Empleado encontrado: {} con ID: {}", existingEmployee.getName(), id);
        return employeeMapper.toEmployeeResponseDTO(existingEmployee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDTO findEmployeeByEmail(String email) {
        log.info("Buscando empleado con email: {}", email);
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND_EMAIL, email)));
        log.info("Empleado encontrado: {} con email: {}", employee.getName(), employee.getEmail());
        return employeeMapper.toEmployeeResponseDTO(employee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employeeRequestDTO) {
        log.info("Actualizando empleado con ID: {}", id);
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, id)));

        log.debug("Empleado encontrado: {} {}", existingEmployee.getName(), existingEmployee.getLastname());
        employeeMapper.updateEntityFromDTO(existingEmployee, employeeRequestDTO);

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        log.info("Empleado actualizado exitosamente: {} (ID: {})", updatedEmployee.getName(), updatedEmployee.getIdEmployee());

        return employeeMapper.toEmployeeResponseDTO(updatedEmployee);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteEmployeeById(Long id) {
        log.info("Eliminando empleado con ID: {}", id);
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(EMPLOYEE_NOT_FOUND, id)));

        employeeRepository.delete(existingEmployee);
        log.info("Empleado eliminado exitosamente: {} (ID: {})", existingEmployee.getName(), existingEmployee.getIdEmployee());

    }
}
