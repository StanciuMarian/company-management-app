package com.unibuc.bdoo.dto;

import com.unibuc.bdoo.domain.Department;

public class DepartmentDto {

    public long id;
    public String name;
    public long managerId;

    public DepartmentDto() {
    }

    public DepartmentDto(Department department) {
        id = department.getId();
        name = department.getName();
        managerId = department.getManager().getId();
    }
}
