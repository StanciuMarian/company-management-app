package com.unibuc.bdoo.business;

import com.unibuc.bdoo.ResourceNotFoundException;
import com.unibuc.bdoo.domain.Department;
import com.unibuc.bdoo.domain.Employee;
import com.unibuc.bdoo.domain.Project;
import com.unibuc.bdoo.dto.DepartmentDto;
import com.unibuc.bdoo.dto.EmployeeDto;
import com.unibuc.bdoo.dto.ProjectDto;
import com.unibuc.bdoo.repositories.DepartmentRepository;
import com.unibuc.bdoo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class DepartmentBusinessController {

    private DepartmentRepository departmentRepository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public DepartmentBusinessController(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<DepartmentDto> getAllDepartments() {

        return departmentRepository.findAll().stream()
                    .map(DepartmentDto::new)
                    .collect(toList());
    }

    public List<DepartmentDto> getDepartmentsByName(String name) {
        return departmentRepository.findAll().stream()
                    .filter(department -> department.getName().equals(name))
                    .map(DepartmentDto::new)
                    .collect(toList());
    }

    public DepartmentDto getDepartmentById(long id) {
        if(id < 0) {
            throw new IllegalArgumentException("You should provide a valid department id");
        }

        return departmentRepository.findById(id)
                    .map(DepartmentDto::new)
                    .orElseThrow(ResourceNotFoundException::new);
    }

    public List<ProjectDto> getProjectsByDepartment(long id) {
        List<Project> projects = departmentRepository.findById(id)
                    .map(Department::getProjects)
                    .orElseThrow(ResourceNotFoundException::new);
        return projects.stream()
                    .map(ProjectDto::new)
                    .collect(toList());
    }

    public EmployeeDto getDepartmentManager(long id) {
        return departmentRepository.findById(id)
                    .map(Department::getManager)
                    .map(EmployeeDto::new)
                    .orElseThrow(ResourceNotFoundException::new);
    }

    public void createDepartment(DepartmentDto departmentDto) {
        if(departmentDto.name.isEmpty()) {
            throw new IllegalArgumentException("You should provide a valid name");
        }

        if(departmentDto.managerId < 0 && !employeeRepository.existsById(departmentDto.managerId)) {
            throw new IllegalArgumentException("You should provide a valid manager id");
        }

        Employee manager = employeeRepository.getOne(departmentDto.managerId);
        departmentRepository.save(new Department(departmentDto.name, manager));
    }

    public void deleteDepartment(long id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException();
        }
    }
}
