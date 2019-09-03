package com.unibuc.bdoo.rest;

import com.unibuc.bdoo.business.DepartmentBusinessController;
import com.unibuc.bdoo.dto.DepartmentDto;
import com.unibuc.bdoo.dto.EmployeeDto;
import com.unibuc.bdoo.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {

    private DepartmentBusinessController departmentBusinessController;

    @Autowired
    public DepartmentController(DepartmentBusinessController departmentBusinessController) {
        this.departmentBusinessController = departmentBusinessController;
    }

    @GetMapping
    public List<DepartmentDto> getDepartments(@RequestParam(required = false) String name) {
        if(name != null) {
            return departmentBusinessController.getDepartmentsByName(name);
        }
        return departmentBusinessController.getAllDepartments();
    }

    @GetMapping("{id}")
    public DepartmentDto getDepartmentById(@PathVariable long id) {
        return departmentBusinessController.getDepartmentById(id);
    }

    @GetMapping("{id}/projects")
    public List<ProjectDto> getProjectsByDepartment(@PathVariable long id) {
        return departmentBusinessController.getProjectsByDepartment(id);
    }

    @GetMapping("{id}/manager")
    public EmployeeDto getDepartmentManager(@PathVariable long id) {
        return departmentBusinessController.getDepartmentManager(id);
    }

    @PostMapping
    public void createDepartment(@RequestBody DepartmentDto departmentDto) {
        departmentBusinessController.createDepartment(departmentDto);
    }

    @DeleteMapping("{id}")
    public void deleteDepartment(@PathVariable long id) {
        departmentBusinessController.deleteDepartment(id);
    }
}
