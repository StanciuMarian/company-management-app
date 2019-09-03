package com.unibuc.bdoo.dto;

import com.unibuc.bdoo.domain.Project;

public class ProjectDto {

    public long id;
    public double budget;
    public String name;
    public long departmentId;

    public ProjectDto() {
    }

    public ProjectDto(Project project) {
        id = project.getId();
        budget = project.getBudget();
        name = project.getName();
        departmentId = project.getDepartment().getId();
    }
}
