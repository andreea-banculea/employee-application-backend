package com.utcn.employeeapplication.department;

import com.utcn.employeeapplication.employee.EmployeeDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DepartmentDTO {
    private Long id;

    private String name;

    private String description;

    private Long parentId;

    Department convertToDepartment(Department parent){
        return Department.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .parent(parent)
                .build();
    }
}
