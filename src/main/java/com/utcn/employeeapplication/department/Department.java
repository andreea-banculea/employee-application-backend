package com.utcn.employeeapplication.department;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    private Department parent;

    DepartmentDTO convertToDepartmentDTO(){
        return DepartmentDTO.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .parentId(this.parent != null ? this.parent.getId() : null)
                .build();
    }
}
