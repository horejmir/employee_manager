package cz.horejsi.employee_manager.service;

import cz.horejsi.employee_manager.model.Employee;
import cz.horejsi.employee_manager.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Autowired
    @InjectMocks
    private EmployeeService employeeService;
    private Employee employee1;
    private Employee employee2;
    private List<Employee> employeeList;

    @BeforeEach
    public void setUp() {
        employeeList = new ArrayList<>();
        employee1 = new Employee(2L, "Eva", "Nova", "607454749",
                "eva@gmail.ccom", "SQL developer", "avatar2.png");
        employee2 = new Employee(1L, "Jan", "Novak", "606456789",
                "jan@novak.cz", "UI developer", "avatar4.png");
        employeeList.add(employee1);
        employeeList.add(employee2);
    }

    @AfterEach
    public void tearDown() {
        employee1 = null;
        employee2 = null;
        employeeList = null;
    }

    @Test
    @DisplayName("SERVICE - ADD EMPLOYEE")
    void givenEmployeeToAddShouldReturnAddedEmployee() {
        //stubbing
        when(employeeRepository.save(any())).thenReturn(employee1);
        employeeService.addEmployee(employee1);

        verify(employeeRepository,times(1)).save(any());
    }

    @Test
    @DisplayName("SERVICE - FIND EMPLOYEE BY ID")
    public void givenIdThenShouldReturnEmployeeOfThatId() {
        when(employeeRepository.findById(any())).thenReturn(Optional.ofNullable(employee1));

        assertThat(employeeService.findEmployeeById(employee1.getId()).equals(employee1));
    }

    @Test
    @DisplayName("SERVICE - DELETE EMPLOYEE")
    public void givenIdTODeleteThenShouldDeleteTheEmployee(){
        //when(employeeService.deleteEmployee(employee1.getId()).thenReturn(employee1);

        //verify(employeeRepository,times(1)).findAll();
    }

    @Test
    @DisplayName("SERVICE - FIND ALL EMPLOYEE")
    public void GivenGetAllUsersShouldReturnListOfAllUsers(){
        employeeRepository.save(employee1);
        //stubbing mock to return specific data
        when(employeeRepository.findAll()).thenReturn(employeeList);
        List<Employee> employeeList1 = employeeService.findAllEmployees();
        assertEquals(employeeList1,employeeList);

        verify(employeeRepository,times(1)).save(employee1);
        verify(employeeRepository,times(1)).findAll();
    }

}