package com.utcn.employeeapplication.employee;

import com.utcn.employeeapplication.department.Department;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;

    private String name;

    private Long departmentId;

    private Long managerId;

    private List<Long> managedEmployees;

    @Email(message = "Not a valid email.")
    private String email;

    private String password;

    public Employee convertToEmployee(Department department, Employee manager, List<Employee> managedEmployees) {
        return Employee.builder()
                .id(this.id)
                .name(this.name)
                .department(department)
                .manager(manager)
                .managedEmployees(managedEmployees)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
