package com.unibuc.bdoo.business;


import com.unibuc.bdoo.ResourceNotFoundException;
import com.unibuc.bdoo.domain.Employee;
import com.unibuc.bdoo.domain.Project;
import com.unibuc.bdoo.dto.EmployeeDto;
import com.unibuc.bdoo.repositories.EmployeeRepository;
import com.unibuc.bdoo.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class EmployeeBusinessController {

    private EmployeeRepository employeeRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public EmployeeBusinessController(EmployeeRepository employeeRepository, ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    public EmployeeDto getEmployeeById(@PathVariable long id) {

        if(id < 0) {
            throw new IllegalArgumentException("You cannot pass a negative value as an id");
        }

        return employeeRepository.findById(id)
                .map(EmployeeDto::new)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public List<EmployeeDto> getAllEmployess() {
        return mapEmployeesToDto(employeeRepository.findAll());
    }

    public List<EmployeeDto> getAllEmployeesByFirstName(String firstName) {
        return mapEmployeesToDto(employeeRepository.findAllByFirstName(firstName));
    }

    private List<EmployeeDto> mapEmployeesToDto(List<Employee> employees) {
        return employees.stream()
                .map(EmployeeDto::new)
                .collect(toList());
    }

    public void addEmployee(EmployeeDto employee) {
        if(employee.firstName.isEmpty() || employee.lastName.isEmpty()) {
            throw new IllegalArgumentException("You must provide both first name and last name");
        }

        Project project = projectRepository.findById(employee.projectId).orElse(null);
        employeeRepository.save(new Employee(employee.firstName, employee.lastName, project));
    }

    public void deleteEmployee(long id) {
        if(employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    public void updateEmployee(long id, EmployeeDto employeeDto) {
        if(employeeDto.firstName.isEmpty() || employeeDto.lastName.isEmpty()) {
            throw new IllegalArgumentException("You must provide both first name and last name");
        }

        if(!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }

        if(employeeDto.projectId < 0 && !projectRepository.existsById(employeeDto.projectId)) {
            throw new IllegalArgumentException("You must provide a valid project");
        }

        Project project = projectRepository.getOne(employeeDto.projectId);

        Employee employee = employeeRepository.getOne(id);
        employee.setFirstName(employeeDto.firstName);
        employee.setLastName(employeeDto.lastName);
        employee.setProject(project);

        employeeRepository.save(employee);
    }
}
