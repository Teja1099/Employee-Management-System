package com.prokarma.nike.services;

import com.prokarma.nike.entity.Employee;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IEmployeeService {
    public List<Employee> getEmployees();
    public Employee getEmployeeByEmpId(@PathVariable("id") Integer empId);
}
