package cz.horejsi.employee_manager.repository;

import cz.horejsi.employee_manager.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee(1L, "Jan", "Novak", "606456789",
                "jan@novak.cz", "UI developer", "avatar4.png");
    }

    @AfterEach
    public void tearDown() {
        employeeRepository.deleteAll();
        employee = null;
    }

    @Test
    @DisplayName("REPOSITORY - SAVE")
    public void givenEmployeeToAddShouldReturnAddedEmployee(){
        employeeRepository.save(employee);

        Employee fetchedEmployee = employeeRepository.findById(employee.getId()).get();

        assertEquals(employee.getId(), fetchedEmployee.getId());
    }

    @Test
    @DisplayName("REPOSITORY - FIND ALL")
    public void givenGetAllEmployeeShouldReturnListOfAllEmployees(){
        Employee employee1 = new Employee(2L, "Eva", "Nova", "607454749",
                "eva@gmail.ccom", "SQL developer", "avatar2.png");

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        List<Employee> employeeList = employeeRepository.findAll();

        assertEquals(employee1.getSurname(), employeeList.get(1).getSurname());
    }

    @Test
    @DisplayName("REPOSITORY - FIND BY ID")
    public void givenIdThenShouldReturnEmployeeOfThatId() {
        Employee employee1 = employeeRepository.save(employee);

        Optional<Employee> optional =  employeeRepository.findById(employee1.getId());

        assertEquals(employee1.getId(), optional.get().getId());
        assertEquals(employee1.getName(), optional.get().getName());
    }

    @Test
    @DisplayName("REPOSITORY - DELETE BY ID")
    public void givenIdToDeleteThenShouldDeleteTheEmployee() {
        employeeRepository.save(employee);

        employeeRepository.deleteById(employee.getId());
        Optional optional = employeeRepository.findById(employee.getId());

        assertEquals(Optional.empty(), optional);
    }

}