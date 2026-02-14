package edu.employeemanagement.repository;

import edu.employeemanagement.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    /**
     * Busca un empleado por email.
     * Útil para validar que el email sea único antes de crear/actualizar.
     *
     * @param email Email del empleado a buscar
     * @return Optional con el empleado si existe
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Verifica si existe un empleado con el email dado.
     * Más eficiente que findByEmail() si solo necesitas saber si existe.
     *
     * @param email Email a verificar
     * @return true si existe, false si no
     */
    boolean existsByEmail(String email);

    /**
     * Busca empleados por apellido (ignorando mayúsculas/minúsculas).
     *
     * @param lastname Apellido a buscar
     * @return Lista de empleados con ese apellido
     */
    List<Employee> findByLastnameIgnoreCase(String lastname);

    /**
     * Busca empleados cuyo nombre o apellido contenga el término de búsqueda.
     * Útil para implementar búsqueda en Angular.
     * @return Lista de empleados que coincidan
     */
    @Query("SELECT e FROM Employee e WHERE " +
            "LOWER(e.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(e.lastname) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Employee> searchEmployees(@Param("searchTerm") String searchTerm);

}
