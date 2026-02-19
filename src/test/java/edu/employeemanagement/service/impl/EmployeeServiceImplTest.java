package edu.employeemanagement.service.impl;

import edu.employeemanagement.data.DataDummy;
import edu.employeemanagement.domain.dto.request.EmployeeRequestDTO;
import edu.employeemanagement.domain.dto.response.EmployeeResponseDTO;
import edu.employeemanagement.domain.entity.Employee;
import edu.employeemanagement.domain.mapper.EmployeeMapper;
import edu.employeemanagement.exception.DuplicateEmailException;
import edu.employeemanagement.exception.ResourceNotFoundException;
import edu.employeemanagement.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static edu.employeemanagement.data.DataDummy.employeeList;
import static edu.employeemanagement.data.DataDummy.employeeResponseList;
import static edu.employeemanagement.util.Constants.EMPLOYEE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmployeeServiceImpl - Unit Tests")
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeRequestDTO employeeRequestDTO;
    private EmployeeResponseDTO employeeResponseDTO;

    @BeforeEach
    void setUp() {
        employee = DataDummy.firstEmployee();
        employeeRequestDTO = DataDummy.defaultEmployeeRequestDTO();
        employeeResponseDTO = DataDummy.defaultEmployeeResponseDTO();
    }

    @Test
    @DisplayName("Create Employee - Success")
    void testCreateEmployee() {
        // Given
        when(employeeRepository.existsByEmail(employeeRequestDTO.getEmail())).thenReturn(false);
        when(employeeMapper.toEntity(employeeRequestDTO)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toEmployeeResponseDTO(employee)).thenReturn(employeeResponseDTO);

        // When
        EmployeeResponseDTO result = employeeService.createEmployee(employeeRequestDTO);

        // Then
        assertThat(result).isEqualTo(employeeResponseDTO);
        verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("Create Employee - Duplicate Email Exception")
    void testCreateEmployee_DuplicateEmail() {
        // Given
        when(employeeRepository.existsByEmail(employeeRequestDTO.getEmail())).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> employeeService.createEmployee(employeeRequestDTO))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    @DisplayName("Find All Employees - Success")
    void testFindAllEmployees() {
        // Given
        List<Employee> entities = employeeList();
        List<EmployeeResponseDTO> dtos = employeeResponseList();

        when(employeeRepository.findAll()).thenReturn(entities);
        when(employeeMapper.toEmployeeResponseList(entities)).thenReturn(dtos);

        // When
        List<EmployeeResponseDTO> result = employeeService.findAllEmployees();

        // Then
        assertThat(result).hasSize(3).isEqualTo(dtos);
        verify(employeeRepository).findAll();
    }

    @Test
    @DisplayName("Find Employee By Id - Success")
    void testFindEmployeeById() {
        // Given
        when(employeeRepository.findById(employee.getIdEmployee())).thenReturn(Optional.of(employee));
        when(employeeMapper.toEmployeeResponseDTO(employee)).thenReturn(employeeResponseDTO);

        // When
        EmployeeResponseDTO result = employeeService.findEmployeeById(employee.getIdEmployee());

        // Then
        assertThat(result).isEqualTo(employeeResponseDTO);
        verify(employeeRepository).findById(employee.getIdEmployee());
    }

    @Test
    @DisplayName("Find Employee By Id - Not Found Exception")
    void testFindEmployeeById_NotFound() {
        // Given
        Long idNotFound = 99L;
        when(employeeRepository.findById(idNotFound)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> employeeService.findEmployeeById(idNotFound))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(String.format(EMPLOYEE_NOT_FOUND, idNotFound));
    }

    @Test
    @DisplayName("Find Employee By Email - Success")
    void testFindEmployeeByEmail() {
        // Given
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));
        when(employeeMapper.toEmployeeResponseDTO(employee)).thenReturn(employeeResponseDTO);

        // When
        EmployeeResponseDTO result = employeeService.findEmployeeByEmail(employee.getEmail());

        // Then
        assertThat(result).isEqualTo(employeeResponseDTO);
        verify(employeeRepository).findByEmail(employee.getEmail());
    }

    @Test
    @DisplayName("Find Employee By Email - Not Found Exception")
    void testFindEmployeeByEmail_NotFound() {
        // Given
        when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> employeeService.findEmployeeByEmail(employee.getEmail()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("Update Employee - Success")
    void testUpdateEmployee() {
        // Given
        when(employeeRepository.findById(employee.getIdEmployee())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toEmployeeResponseDTO(employee)).thenReturn(employeeResponseDTO);

        // When
        EmployeeResponseDTO result = employeeService.updateEmployee(employee.getIdEmployee(), employeeRequestDTO);

        // Then
        assertThat(result).isEqualTo(employeeResponseDTO);
        verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("Update Employee - Not Found Exception")
    void testUpdateEmployee_NotFound() {
        // Given
        when(employeeRepository.findById(employee.getIdEmployee())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> employeeService.updateEmployee(employee.getIdEmployee(), employeeRequestDTO))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("Delete Employee By Id - Success")
    void testDeleteEmployeeById() {
        // Given
        when(employeeRepository.findById(employee.getIdEmployee())).thenReturn(Optional.of(employee));

        // When
        employeeService.deleteEmployeeById(employee.getIdEmployee());

        // Then
        verify(employeeRepository).delete(employee);
    }

    @Test
    @DisplayName("Delete Employee By Id - Not Found Exception")
    void testDeleteEmployeeById_NotFound() {
        // Given
        when(employeeRepository.findById(employee.getIdEmployee())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> employeeService.deleteEmployeeById(employee.getIdEmployee()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

}