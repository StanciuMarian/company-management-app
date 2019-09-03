package com.unibuc.bdoo.dto;

import com.unibuc.bdoo.domain.Employee;
import com.unibuc.bdoo.domain.Project;

import static java.util.Optional.ofNullable;

public class EmployeeDto {

    public long id;
    public String firstName;
    public String lastName;
    public long projectId;

    public EmployeeDto() {
    }

    public EmployeeDto(Employee employee) {
        this.id = employee.getId();
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.projectId = ofNullable(employee.getProject()).map(Project::getId).orElse(-1L);
    }
}
