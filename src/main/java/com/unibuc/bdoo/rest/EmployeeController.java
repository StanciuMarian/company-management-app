package com.unibuc.bdoo.rest;


import com.unibuc.bdoo.business.EmployeeBusinessController;
import com.unibuc.bdoo.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private EmployeeBusinessController employeeBusinessController;

    @Autowired
    public EmployeeController(EmployeeBusinessController employeeBusinessController) {
        this.employeeBusinessController = employeeBusinessController;
    }

    @GetMapping("{id}")
    public EmployeeDto getEmployeeById(@PathVariable long id) {
        return employeeBusinessController.getEmployeeById(id);
    }

    @GetMapping
    public List<EmployeeDto> getAllEmployees(@RequestParam(required = false) String firstName) {
        if(firstName != null) {
            return employeeBusinessController.getAllEmployeesByFirstName(firstName);
        }
        return employeeBusinessController.getAllEmployess();
    }

    @PostMapping
    public void addEmployee(@RequestBody EmployeeDto employee) {
        employeeBusinessController.addEmployee(employee);
    }

    @PutMapping("{id}")
    public void updateEmployee(@PathVariable long id, @RequestBody EmployeeDto employee) {
        employeeBusinessController.updateEmployee(id, employee);
    }

    @DeleteMapping("{id}")
    public void deleteEmployee(@PathVariable long id) {
        employeeBusinessController.deleteEmployee(id);
    }
}
