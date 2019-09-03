package com.unibuc.bdoo.business;

import com.unibuc.bdoo.ResourceNotFoundException;
import com.unibuc.bdoo.domain.Department;
import com.unibuc.bdoo.domain.Employee;
import com.unibuc.bdoo.domain.Project;
import com.unibuc.bdoo.dto.EmployeeDto;
import com.unibuc.bdoo.repositories.EmployeeRepository;
import com.unibuc.bdoo.repositories.ProjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class EmployeeBusinessControllerTest {

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private ProjectRepository projectRepository;

    private EmployeeBusinessController employeeBusinessController;

    @Before
    public void beforeEach() {
        employeeBusinessController = new EmployeeBusinessController(employeeRepository, projectRepository);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getEmployeeById_shouldThrow_ifIdIsNegative() {
        long id = -1;
        employeeBusinessController.getEmployeeById(id);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getEmployeeById_shouldThrow_ifNoEmplyeeFound() {
        long id = 0;
        when(employeeRepository.findById(id)).thenReturn(empty());
        employeeBusinessController.getEmployeeById(id);
    }

    @Test
    public void getEmployeeById_shouldReturnAnEmployee() {
        long id = 0;

        Department department = new Department("IT", null);
        Project project = new Project("Project1", 200, department);
        Employee empl = new Employee("Ungureanu", "Cristian", project);
        when(employeeRepository.findById(id)).thenReturn(of(empl));


        EmployeeDto employee = employeeBusinessController.getEmployeeById(id);

        assertEquals("Ungureanu", employee.firstName);
        assertEquals("Cristian", employee.lastName);
    }
}