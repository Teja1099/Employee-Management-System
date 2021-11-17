package com.prokarma.nike.services;

import com.prokarma.nike.entity.Interview;
import com.prokarma.nike.entity.Leave;
import com.prokarma.nike.helper.ExcelHelper;
import com.prokarma.nike.entity.Employee;
import com.prokarma.nike.repository.EmployeeRepository;
import com.prokarma.nike.repository.InterviewRepository;
import com.prokarma.nike.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    @Autowired
    private ExcelHelper excelHelper;

    @Autowired
    EmployeeRepository repository;

    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    InterviewRepository interviewRepository;


    public void saveEmployees(MultipartFile file) {
        try {
            List<Employee> employees = excelHelper.excelToEmployees(file.getInputStream());
            repository.saveAll(employees);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

   public  void saveLeaves(MultipartFile file) {
        try {
            System.out.println("Req save");
            excelHelper.excelToLeaves(file.getInputStream());



        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
   }

    public  void saveInterviews(MultipartFile file) {
        try {
            System.out.println("Req save");
           excelHelper.excelToInterview(file.getInputStream());

        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }
}