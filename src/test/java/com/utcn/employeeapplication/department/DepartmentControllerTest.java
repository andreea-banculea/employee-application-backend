package com.utcn.employeeapplication.department;

import com.utcn.employeeapplication.department.Department;
import com.utcn.employeeapplication.department.DepartmentController;
import com.utcn.employeeapplication.department.DepartmentDTO;
import com.utcn.employeeapplication.department.DepartmentService;
import com.utcn.employeeapplication.util.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.utcn.employeeapplication.department.DepartmentServiceTest.generateSampleDepartments;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    private List<Department> sampleDepartments;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        sampleDepartments = generateSampleDepartments();
    }

    @Test
    void getAllDepartments() {
        when(departmentService.getAllDepartments()).thenReturn(sampleDepartments);

        ResponseEntity<List<Department>> responseEntity = departmentController.getAllDepartments();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sampleDepartments, responseEntity.getBody());
        verify(departmentService, times(1)).getAllDepartments();
    }

    @Test
    void getDepartmentById() {
        Department sampleDepartment = sampleDepartments.get(0);
        when(departmentService.getDepartmentById(any())).thenReturn(sampleDepartment);

        ResponseEntity<Department> responseEntity = departmentController.getDepartmentById(1L);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sampleDepartment, responseEntity.getBody());
        verify(departmentService, times(1)).getDepartmentById(any());
    }

    @Test
    void createDepartment() {
        DepartmentDTO departmentDTO = sampleDepartments.get(0).convertToDepartmentDTO();
        when(departmentService.create(any())).thenReturn(sampleDepartments.get(0));

        ResponseEntity<Department> responseEntity = departmentController.createDepartment(departmentDTO);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sampleDepartments.get(0), responseEntity.getBody());
        verify(departmentService, times(1)).create(any());
    }

    @Test
    void updateDepartment() throws EntityNotFoundException {
        DepartmentDTO updatedDepartmentDTO =sampleDepartments.get(0).convertToDepartmentDTO();
        Department existingDepartment = sampleDepartments.get(0);
        when(departmentService.update(any(), any())).thenReturn(existingDepartment);

        ResponseEntity<Department> responseEntity = departmentController.updateDepartment(1L, updatedDepartmentDTO);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(existingDepartment, responseEntity.getBody());
        verify(departmentService, times(1)).update(any(), any());
    }

    @Test
    void deleteDepartment() throws EntityNotFoundException {
        assertDoesNotThrow(() -> departmentController.deleteDepartment(1L));

        verify(departmentService, times(1)).delete(any());
    }
}
