package com.unibuc.bdoo;

import com.unibuc.bdoo.domain.Department;
import com.unibuc.bdoo.domain.Employee;
import com.unibuc.bdoo.domain.Project;
import com.unibuc.bdoo.repositories.DepartmentRepository;
import com.unibuc.bdoo.repositories.EmployeeRepository;
import com.unibuc.bdoo.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@SpringBootApplication
@EnableSwagger2
@EnableAspectJAutoProxy
public class BdooApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BdooApplication.class, args);
	}


	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Override
	public void run(String... args) throws Exception {
		Employee manager = new Employee("Ungureanu", "Cristian", null);

		Department department = new Department("IT", manager);
		departmentRepository.save(department);

		Project project = new Project("Metrorex App", 200, department);
		projectRepository.save(project);

		employeeRepository.save(new Employee("Stanciu", "Marian", project));
		List<Employee> employees = employeeRepository.findAll();

		for (Employee employee : employees) {
			System.out.println(employee);
		}
	}
}
