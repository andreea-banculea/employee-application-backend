package com.utcn.employeeapplication.employee;


import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeSpecification {

    public Specification<Employee> getEmployeeByFilter(Long departmentId, String role) {
        return (employeeRoot, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (departmentId != null)
                predicates.add(criteriaBuilder.equal(employeeRoot.get("department").get("id"), departmentId));
            if (role != null)
                switch (role) {
                    case "Manager" -> predicates.add(criteriaBuilder.gt(criteriaBuilder.size(employeeRoot.get("managedEmployees")), 0));
                    case "Employee" -> predicates.add(criteriaBuilder.isEmpty(employeeRoot.get("managedEmployees")));
                }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
