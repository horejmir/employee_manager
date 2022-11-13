package cz.horejsi.employee_manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.horejsi.employee_manager.model.Employee;
import cz.horejsi.employee_manager.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EmployeeResourceTest {

    @Mock
    private EmployeeService employeeService;
    private Employee employee1;
    private Employee employee2;
    private List<Employee> employeeList;

    @InjectMocks
    private EmployeeResource employeeResource;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        employee1 = new Employee(1L, "Jan", "Novak", "606456789",
                "jan@novak.cz", "UI developer", "avatar4.png");
        employee2 = new Employee(2L, "Eva", "Nova", "607454749",
                "eva@gmail.ccom", "SQL developer", "avatar2.png");

        employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);

        mockMvc = MockMvcBuilders.standaloneSetup(employeeResource).build();
    }
    @AfterEach
    void tearDown() {
        employee1 = null;
        employee2 = null;
        employeeList = null;
    }

    @Test
    @DisplayName("CONTROLLER - GET ALL EMPLOYEE")
    void getMappingOfAllEmployee() throws Exception {

        when(employeeService.findAllEmployees()).thenReturn(employeeList);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/all").
                        contentType(MediaType.APPLICATION_JSON).content(asJsonString(employee1))).
                        andExpect(status().isOk()).
                        andDo(MockMvcResultHandlers.print());

        verify(employeeService).findAllEmployees();
        verify(employeeService,times(1)).findAllEmployees();
    }

    @Test
    @DisplayName("CONTROLLER - GET EMPLOYEE BY ID")
    void getMappingOfEmployeeById() throws Exception {

        when(employeeService.findEmployeeById(employee1.getId())).thenReturn(employee1);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/find/" + employee1.getId()).
                        contentType(MediaType.APPLICATION_JSON).
                        content(asJsonString(employee1))).
                        andExpect(status().isOk()).
                        andDo(MockMvcResultHandlers.print());

        verify(employeeService).findEmployeeById(employee1.getId());
        verify(employeeService,times(1)).findEmployeeById(employee1.getId());

    }

    @Test
    @DisplayName("CONTROLLER - POST EMPLOYEE")
    void postMappingOfEmployee() throws Exception {

        when(employeeService.addEmployee(employee1)).thenReturn(employee1);

        mockMvc.perform(post("/employee/add").
                        contentType(MediaType.APPLICATION_JSON).
                        content(asJsonString(employee1))).
                        andExpect(status().isCreated()).
                        andDo(MockMvcResultHandlers.print());

        verify(employeeService).addEmployee(employee1);
        verify(employeeService,times(1)).addEmployee(employee1);
    }

    @Test
    @DisplayName("CONTROLLER - PUT EMPLOYEE")
    void putMappingOfEmployee() throws Exception {

        when(employeeService.updateEmployee(employee1)).thenReturn(employee1);

        mockMvc.perform(put("/employee/update").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(employee1))).
                andExpect(status().isOk()).
                andDo(MockMvcResultHandlers.print());

        verify(employeeService).updateEmployee(employee1);
        verify(employeeService, times(1)).updateEmployee(employee1);

    }

    @Test
    @DisplayName("CONTROLLER - DELETE EMPLOYEE")
    void deleteMappingOfEmployee() throws Exception {

        doNothing().when(employeeService).deleteEmployee(employee1.getId());

        mockMvc.perform(delete("/employee/delete/" + employee1.getId()))
                        .andExpect(status().isOk()).
                        andDo(MockMvcResultHandlers.print());

        verify(employeeService).deleteEmployee(employee1.getId());
        verify(employeeService, times(1)).deleteEmployee(employee1.getId());
    }


    public static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}