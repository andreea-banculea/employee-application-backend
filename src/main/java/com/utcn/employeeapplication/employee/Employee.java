package com.utcn.employeeapplication.employee;

import com.utcn.employeeapplication.department.Department;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Employee manager;

    @OneToMany
    private List<Employee> managedEmployees;

    @Email(message = "Not a valid email.")
    private String email;

    private String password;

    public EmployeeDTO convertToEmployeeDTO() {
        return EmployeeDTO.builder()
                .id(this.getId())
                .name(this.name)
                .departmentId(this.department.getId())
                .managerId(manager != null ? this.manager.getId() : null)
                .managedEmployees(this.managedEmployees.stream().map(Employee::getId).toList())
                .email(this.email)
                .password(this.password)
                .build();
    }
}
