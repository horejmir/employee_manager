package cz.horejsi.employee_manager;

import cz.horejsi.employee_manager.model.Employee;
import cz.horejsi.employee_manager.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The controller for Employee REST endpoints and web UI.
 * <p>This class handles the CRUD operations for Employee, via HTTP actions.</p>
 *
 * <p>This class also serves for interaction with front end employee manager application.</p>
 *
 * @author Miroslav Horejsi
 * @version 1.0.0
 */

@RestController
@RequestMapping("/employee")
@Api(value = "", tags = {"Employee API"}) //swagger custom name of API
@Tag(name = "Employee API", description = "description of rest API...") //swagger custom description of API
public class EmployeeResource {

    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Return list of all employees.
     * @return list of employee objects
     */
    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.findAllEmployees(), HttpStatus.OK);
    }

    /**
     * Return an employee object for given id.
     * @param id
     * @return employee object
     */
    @GetMapping("/find/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(employeeService.findEmployeeById(id), HttpStatus.OK);
    }

    /**
     * Create a new employee object, given the data provided.
     * @param employee a JSON representation of employee object.
     * @return newly created employee object.
     */
    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.addEmployee(employee), HttpStatus.CREATED);
    }

    /**
     * Update existing employee object, given the data provided.
     * Create new employee object if given employee object is not in database (compare by object id).
     * @param employee a JSON representation of employee object.
     * @return updated employee object.
     */
    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.updateEmployee(employee), HttpStatus.OK);
    }

    /**
     * Delete the employee object of given id
     * @param id
     * @return httpStatus code 200 - if employee is deleted
     */

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
