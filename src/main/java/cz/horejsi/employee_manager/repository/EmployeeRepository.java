package cz.horejsi.employee_manager.repository;

import cz.horejsi.employee_manager.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
