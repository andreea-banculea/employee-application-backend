package com.utcn.employeeapplication.employee;

import com.utcn.employeeapplication.util.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeDTO employeeDTO) throws EntityNotFoundException {
        return new ResponseEntity<>(employeeService.create(employeeDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployeesByFilter(@RequestParam(required = false) Long departmentId, @RequestParam(required = false) String role ) {
        return new ResponseEntity<>(employeeService.getAllByFilter(departmentId,role), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<EmployeeDTO> updateEmployee( @RequestBody EmployeeDTO employee) throws EntityNotFoundException {
        return new ResponseEntity<>(employeeService.update(employee), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) throws EntityNotFoundException {
        employeeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
