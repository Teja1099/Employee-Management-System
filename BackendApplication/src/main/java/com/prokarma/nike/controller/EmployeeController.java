package com.prokarma.nike.controller;

import com.prokarma.nike.entity.Employee;
import com.prokarma.nike.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @RequestMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeService.getEmployees();

    }
    @RequestMapping("/employee/{id}")
    public Employee getEmployeeByEmpId(@PathVariable("id") Integer empId) {
        return employeeService.getEmployeeByEmpId(empId);

    }
    @PostMapping("/employee")
    public void createEmployee(@RequestBody Employee employee) {

         employeeService.createEmployee(employee);
    }

    @DeleteMapping("/employees/{empId}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Integer empId){

        return employeeService.deleteEmployee(empId);
    }

    @PutMapping("/employee")
    public ResponseEntity<Map<String, Boolean>>  updateEmployee(@RequestBody Employee employee) {

        return employeeService.updateEmployee(employee);
    }

    @DeleteMapping("/interview/{id}")
    public ResponseEntity<?> deleteInterviewById(@PathVariable Integer id) {
        employeeService.deleteInterviewById(id);
        return ResponseEntity.ok("done");
    }

}
