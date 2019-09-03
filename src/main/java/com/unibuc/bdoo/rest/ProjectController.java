package com.unibuc.bdoo.rest;


import com.unibuc.bdoo.business.ProjectBusinessController;
import com.unibuc.bdoo.dto.EmployeeDto;
import com.unibuc.bdoo.dto.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projects")
public class ProjectController {

    private ProjectBusinessController projectBusinessController;

    @Autowired
    public ProjectController(ProjectBusinessController projectBusinessController) {
        this.projectBusinessController = projectBusinessController;
    }

    @GetMapping
    public List<ProjectDto> getProjects(@RequestParam(required = false) String name) {
        if(name != null) {
            return projectBusinessController.getProjectsByName(name);
        }
        return projectBusinessController.getAllProjects();
    }

    @GetMapping("{id}")
    public ProjectDto getProjectById(@PathVariable long id) {
        return projectBusinessController.getProjectById(id);
    }

    @GetMapping("{id}/employees")
    public List<EmployeeDto> getEmployeesByProject(@PathVariable long id) {
        return projectBusinessController.getEmployeesByProject(id);
    }

    @PostMapping
    public void createProject(@RequestBody ProjectDto projectDto) {
        projectBusinessController.createProject(projectDto);
    }

    @PutMapping("{id}")
    public void updateProject(@PathVariable long id, @RequestBody ProjectDto projectDto) {
        projectBusinessController.updateProject(id, projectDto);
    }

    @DeleteMapping("{id}")
    public void deleteProject(@PathVariable long id) {
        projectBusinessController.deleteProject(id);
    }
}
