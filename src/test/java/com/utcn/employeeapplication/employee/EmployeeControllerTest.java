package com.utcn.employeeapplication.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utcn.employeeapplication.department.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private List<Employee> sampleEmployees;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        sampleEmployees = generateSampleEmployees();
    }

    @Test
    void createEmployee() throws Exception {
        EmployeeDTO employeeDTO = convertToDTO(sampleEmployees.get(0));
        when(employeeService.create(any(EmployeeDTO.class))).thenReturn(sampleEmployees.get(0).convertToEmployeeDTO());

        mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(employeeService, times(1)).create(any(EmployeeDTO.class));
    }

    @Test
    void getAllEmployees() throws Exception {
        List<EmployeeDTO> employeeDTOList = convertToDTOList(sampleEmployees);
        when(employeeService.getAllEmployees()).thenReturn(employeeDTOList);

        mockMvc.perform(get("/api/employee"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(sampleEmployees.size()));

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void getEmployeeById() throws Exception {
        EmployeeDTO employeeDTO = sampleEmployees.get(0).convertToEmployeeDTO();
        when(employeeService.getEmployeeById(anyLong())).thenReturn(employeeDTO);

        mockMvc.perform(get("/api/employee/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(employeeService, times(1)).getEmployeeById(anyLong());
    }

    @Test
    void updateEmployee() throws Exception {
        EmployeeDTO employeeDTO = sampleEmployees.get(0).convertToEmployeeDTO();
        when(employeeService.update(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        mockMvc.perform(put("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employeeDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(employeeService, times(1)).update(any(EmployeeDTO.class));
    }

    @Test
    void deleteEmployee() throws Exception {
        mockMvc.perform(delete("/api/employee/{id}", 1))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).delete(anyLong());
    }

    // Helper method to convert object to JSON string
    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        return employee.convertToEmployeeDTO();
    }

    private List<EmployeeDTO> convertToDTOList(List<Employee> employees) {
        return employees.stream().map(Employee::convertToEmployeeDTO).toList();
    }

    public static List<Employee> generateSampleEmployees() {
        List<Employee> employees = new ArrayList<>();

        // Sample departments
        Department department1 = new Department(1L, "IT Department", "Nr. 1 department", null);
        Department department2 = new Department(2L, "HR Department", "Nr. 1 department", department1);

        // Sample employees
        Employee employee1 = new Employee(1L, "John Doe", department1, null, new ArrayList<>(), "john.doe@example.com", "1234");
        Employee employee2 = new Employee(2L, "Jane Smith", department1, employee1, new ArrayList<>(), "jane.smith@example.com", "1234");
        employee1.getManagedEmployees().add(employee2);
        Employee employee3 = new Employee(3L, "Bob Johnson", department2, null, new ArrayList<>(), "bob.johnson@example.com", "1234");

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);

        return employees;
    }
}
