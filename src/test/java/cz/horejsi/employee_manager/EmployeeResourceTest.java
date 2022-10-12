package cz.horejsi.employee_manager;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.horejsi.employee_manager.model.Employee;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
class EmployeeResourceTest {

    @Autowired
    private MockMvc mvc;

    @Nested
    @DisplayName("GET-ALL/ADD")
    class getAllEmployeesTests {

        @Test
        @DisplayName("GET ALL EMPLOYEES")
        void getEmployeesList() throws Exception {
            // given
            String uri = "/employee/all";
            // when
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
            int status = mvcResult.getResponse().getStatus();
            // then
            assertEquals(200, status);
            String content = mvcResult.getResponse().getContentAsString();
            Employee[] employees = mapFromJson(content, Employee[].class);
            assertTrue(employees.length > 0);
        }

        @Test
        @DisplayName("ADD EMPLOYEE")
        void addEmployee() throws Exception {
            // given
            String uri = "/employee/add";
            Employee employee = new Employee();
            employee.setName("Jan");
            employee.setSurname("Kakadus");
            employee.setEmail("test@gmail.com");
            employee.setJobTitle("designer");
            employee.setPhone("723564892");
            employee.setImageUrl("https://bootdey.com/img/Content/avatar/avatar4.png");
            // when
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                    .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(employee))).andReturn();
            int status = mvcResult.getResponse().getStatus();
            // then
            assertEquals(201, status);
            String contentAdd = mvcResult.getResponse().getContentAsString();
            Employee employeeAdded = mapFromJson(contentAdd, Employee.class);
            assertTrue(employeeAdded.equals(employee));
        }
    }


    @Nested
    @DisplayName("FIND/UPDATE/DELETE")
    class AddFindUpdateDeleteTests {

        private Employee employeeInDatabase;

        @BeforeEach
        void getLastEmployees() throws Exception {
            String uri = "/employee/all";

            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

            String content = mvcResult.getResponse().getContentAsString();
            Employee[] employees = mapFromJson(content, Employee[].class);
            this.employeeInDatabase = employees[employees.length - 1];
        }

        @Test
        @DisplayName("FIND EMPLOYEE")
        void findEmployee() throws Exception {
            // given
            Employee employee = this.employeeInDatabase;
            String uri = "/employee/find/" + employee.getId();
            // when
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                    .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
            int status = mvcResult.getResponse().getStatus();
            // then
            assertEquals(200, status);
            String contentFind = mvcResult.getResponse().getContentAsString();
            Employee actual = mapFromJson(contentFind, Employee.class);
            assertTrue(actual.equals(employee));
        }

        @Test
        @DisplayName("UPDATE EMPLOYEE")
        void updateEmployee() throws Exception {
            // given
            Employee employee = this.employeeInDatabase;
            employee.setName("Adam");
            String uri = "/employee/update";
            // when
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(mapToJson(employee))).andReturn();
            int status = mvcResult.getResponse().getStatus();
            // then
            assertEquals(200, status);
            String content = mvcResult.getResponse().getContentAsString();
            Employee actual = mapFromJson(content, Employee.class);
            assertTrue(actual.equals(employee));
        }

        @Test
        @DisplayName("DELETE EMPLOYEE")
        void deleteEmployee() throws Exception {
            //given
            String uri = "/employee/delete/" + this.employeeInDatabase.getId();
            // when
            MvcResult mvcResultDelete = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
            int status = mvcResultDelete.getResponse().getStatus();
            // then
            assertEquals(200, status);
        }
    }


    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

}