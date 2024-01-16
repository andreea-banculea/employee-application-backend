package com.utcn.employeeapplication.department;

import com.utcn.employeeapplication.util.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public Department create(DepartmentDTO departmentDTO) {
        Department parentDepartment = departmentRepository.findById(departmentDTO.getParentId()).orElse(null);
        Department department = departmentDTO.convertToDepartment(parentDepartment);
        return departmentRepository.save(department);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Transactional
    public Department update(Long id, DepartmentDTO updatedDepartmentDTO) throws EntityNotFoundException {
        Department existingDepartment = departmentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Department not found."));
        Department parentDepartment = departmentRepository.findById(updatedDepartmentDTO.getParentId()).orElse(null);
        Department updatedDepartment = updatedDepartmentDTO.convertToDepartment(parentDepartment);

        return updateExistingDepartment(existingDepartment, updatedDepartment);
    }

    private Department updateExistingDepartment(Department existingDepartment, Department updatedDepartment) {
        existingDepartment.setName(updatedDepartment.getName());
        existingDepartment.setDescription(updatedDepartment.getDescription());
        existingDepartment.setParent(updatedDepartment.getParent());
        return departmentRepository.save(existingDepartment);
    }

    public void delete(Long id) throws EntityNotFoundException {
        Department department = departmentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Department not found"));
        departmentRepository.findAllByParent(department).forEach(childDepartment -> childDepartment.setParent(null));
        departmentRepository.delete(department);
    }
}