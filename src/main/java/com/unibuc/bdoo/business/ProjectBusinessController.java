package com.unibuc.bdoo.business;

import com.unibuc.bdoo.ResourceNotFoundException;
import com.unibuc.bdoo.domain.Department;
import com.unibuc.bdoo.domain.Employee;
import com.unibuc.bdoo.domain.Project;
import com.unibuc.bdoo.dto.EmployeeDto;
import com.unibuc.bdoo.dto.ProjectDto;
import com.unibuc.bdoo.repositories.DepartmentRepository;
import com.unibuc.bdoo.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProjectBusinessController {

    private ProjectRepository projectRepository;
    private DepartmentRepository departmentRepository;

    @Autowired
    public ProjectBusinessController(ProjectRepository projectRepository, DepartmentRepository departmentRepository) {
        this.projectRepository = projectRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(ProjectDto::new)
                .collect(toList());
    }

    public List<ProjectDto> getProjectsByName(String name) {
        return projectRepository.findAll().stream()
                .filter(project -> project.getName().equals(name))
                .map(ProjectDto::new)
                .collect(toList());
    }

    public ProjectDto getProjectById(long id) {
        if(id < 0) {
            throw new IllegalArgumentException("You should provide a valid project id");
        }

        return projectRepository.findById(id)
                .map(ProjectDto::new)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public List<EmployeeDto> getEmployeesByProject(long id) {
        if(id < 0) {
            throw new IllegalArgumentException("You should provide a valid project id");
        }

        List<Employee> employees = projectRepository.findById(id)
                .map(Project::getEmployees)
                .orElseThrow(ResourceNotFoundException::new);

        return employees.stream()
                .map(EmployeeDto::new)
                .collect(toList());    
    }

    public void createProject(ProjectDto projectDto) {
        if(projectDto.name.isEmpty() || projectDto.budget <= 100) {
            throw new IllegalArgumentException("You must provide both a name and a valid budget ( > 100)");
        }
        
        if(projectDto.departmentId < 0 && !departmentRepository.existsById(projectDto.departmentId)) {
            throw new IllegalArgumentException("You must provide a valid project");
        }

        Department department = departmentRepository.getOne(projectDto.departmentId);
        projectRepository.save(new Project(projectDto.name, projectDto.budget, department));
    }


    public void deleteProject(long id) {
        if(projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException();
        }
    }


    public void updateProject(long id, ProjectDto projectDto) {
        if(projectDto.name.isEmpty() || projectDto.budget <= 100) {
            throw new IllegalArgumentException("You must provide both a name and a valid budget ( > 100)");
        }

        if(!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }

        if(projectDto.departmentId < 0 && !projectRepository.existsById(projectDto.departmentId)) {
            throw new IllegalArgumentException("You must provide a valid department");
        }

        Department department = departmentRepository.getOne(projectDto.departmentId);

        Project project = projectRepository.getOne(id);
        project.setName(projectDto.name);
        project.setBudget(projectDto.budget);
        project.setDepartment(department);

        projectRepository.save(project);
    }
}
