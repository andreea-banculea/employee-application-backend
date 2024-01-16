package com.utcn.employeeapplication.employee;

import com.utcn.employeeapplication.department.Department;
import com.utcn.employeeapplication.department.DepartmentRepository;
import com.utcn.employeeapplication.util.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    private final EmployeeSpecification employeeSpecification;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, EmployeeSpecification employeeSpecification) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeSpecification = employeeSpecification;
    }

    @Transactional
    public EmployeeDTO create(EmployeeDTO employeeDTO) throws EntityNotFoundException {
        Department department = departmentRepository.findById(employeeDTO.getDepartmentId()).orElseThrow(() ->
                new EntityNotFoundException("Department not found."));
        Employee manager = employeeRepository.findById(employeeDTO.getManagerId()).orElse(null);

        Employee employee = employeeDTO.convertToEmployee(department, manager, new ArrayList<>());
//        employee.setPassword(passwordEncoder().encode(employee.getPassword()));
        if (manager != null)
            manager.getManagedEmployees().add(employee);
        employeeRepository.save(employee);

        return employee.convertToEmployeeDTO();
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream().map(Employee::convertToEmployeeDTO).toList();
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            return employee.convertToEmployeeDTO();
        }
        return null;
    }

    public List<EmployeeDTO> getAllByFilter(Long departmentId, String role) {
        Specification<Employee> specification = employeeSpecification.getEmployeeByFilter(departmentId, role);
        return employeeRepository.findAll(specification).stream().map(Employee::convertToEmployeeDTO).toList();
    }

    @Transactional
    public EmployeeDTO update(EmployeeDTO updatedEmployeeDTO) throws EntityNotFoundException {
        Employee existingEmployee = employeeRepository.findById(updatedEmployeeDTO.getId()).orElseThrow(() ->
                new EntityNotFoundException("Employee not found."));
        Department updatedDepartment = departmentRepository.findById(updatedEmployeeDTO.getDepartmentId()).orElseThrow(() ->
                new EntityNotFoundException("Department not found."));
        Employee updatedManager = employeeRepository.findById(updatedEmployeeDTO.getManagerId()).orElse(null);
        Employee updatedEmployee = updatedEmployeeDTO.convertToEmployee(updatedDepartment, updatedManager, existingEmployee.getManagedEmployees());

        return updateExistingEmployee(existingEmployee, updatedEmployee);
    }

    private EmployeeDTO updateExistingEmployee(Employee existingEmployee, Employee updatedEmployee) {
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        if (existingEmployee.getManager() != null && updatedEmployee.getManager() != null)
            if (!existingEmployee.getManager().equals(updatedEmployee.getManager())) {
                employeeRepository.findById(existingEmployee.getManager().getId()).ifPresent(manager -> manager.getManagedEmployees().remove(existingEmployee));
                employeeRepository.findById(updatedEmployee.getManager().getId()).ifPresent(manager -> manager.getManagedEmployees().add(updatedEmployee));
                existingEmployee.setManager(updatedEmployee.getManager());
            }
        existingEmployee.setManagedEmployees(updatedEmployee.getManagedEmployees());
        existingEmployee.setEmail(updatedEmployee.getEmail());
//        if (!passwordEncoder().matches(updatedEmployee.getPassword(), existingEmployee.getPassword()))
//            existingEmployee.setPassword(passwordEncoder().encode(updatedEmployee.getPassword()));

        employeeRepository.save(existingEmployee);
        return existingEmployee.convertToEmployeeDTO();
    }


    public void delete(Long id) throws EntityNotFoundException {
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Employee not found"));
        if (employee.getManager() != null)
            employeeRepository.findById(employee.getManager().getId()).ifPresent(manager -> manager.getManagedEmployees().remove(employee));
        if (!employee.getManagedEmployees().isEmpty())
            employee.getManagedEmployees().forEach(managedEmployee -> managedEmployee.setManager(null));
        employeeRepository.delete(employee);
    }

//    private PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(11);
//    }
}
