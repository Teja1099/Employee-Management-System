package com.prokarma.nike.services;

import com.prokarma.nike.entity.EmergencyContact;
import com.prokarma.nike.entity.Employee;
import com.prokarma.nike.entity.Interview;
import com.prokarma.nike.entity.Leave;
import com.prokarma.nike.repository.EmployeeRepository;
import com.prokarma.nike.repository.InterviewRepository;
import com.prokarma.nike.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<Employee> getEmployees() {
        return employeeRepository.sortByProject();

    }

    @Transactional
    public Employee getEmployeeByEmpId(Integer empId) {
        return employeeRepository.findByEmployeeId(empId);

    }

    @Transactional
    public void createEmployee(Employee employee) {
        System.out.println(employee.toString());
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeRepository.save(employee);
    }

    @Transactional
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(Integer empId){

        Employee employee=employeeRepository.findByEmployeeId(empId);
        employee.setDeleted(true);
        employeeRepository.save(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    @Transactional
    public ResponseEntity<Map<String, Boolean>>  updateEmployee(Employee employee) {

        Employee updatedEmployee = employeeRepository.findByEmployeeId(employee.getEmpId());
        updatedEmployee.setEmpId(employee.getEmpId());
        updatedEmployee.setName(employee.getName());
        updatedEmployee.setPkEmail(employee.getPkEmail());
        updatedEmployee.setPassword(employee.getPassword());
        updatedEmployee.setStatus(employee.getStatus());
        updatedEmployee.setNikeId(employee.getNikeId());
        updatedEmployee.setNikeEmail(employee.getNikeEmail());
        updatedEmployee.setDOB(employee.getDOB());
        updatedEmployee.setJoiningDate(employee.getJoiningDate());
        updatedEmployee.setContact(employee.getContact());
        updatedEmployee.setBaseLocation(employee.getBaseLocation());
        updatedEmployee.setRole(employee.getRole());
        updatedEmployee.setProject(employee.getProject());
        updatedEmployee.setProjectCode(employee.getProjectCode());
        updatedEmployee.setDesignation(employee.getDesignation());
        EmergencyContact emergencyContact =employee.getEmergencyContact();
        System.out.println(emergencyContact.getName()+" xyx"+emergencyContact.getContact());
        updatedEmployee.setHasH1(employee.getHasH1());
        updatedEmployee.setTotalExperienceInIT(employee.getTotalExperienceInIT());
        updatedEmployee.setTotalExperienceInPK(employee.getTotalExperienceInPK());
        updatedEmployee.setTotalExperienceInNIKE(employee.getTotalExperienceInNIKE());
        updatedEmployee.setPrimarySkills(employee.getPrimarySkills());
        updatedEmployee.setSceondarySkills(employee.getSceondarySkills());
        updatedEmployee.setAddress(employee.getAddress());
        updatedEmployee.setCertification(employee.getCertification());
        updatedEmployee.setStatus2(employee.getStatus2());
        updatedEmployee.setComments(employee.getComments());
        employeeRepository.save(updatedEmployee);
        System.out.println(employee.toString());
        Map<String, Boolean> response = new HashMap<>();
        response.put("updated", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    public void deleteInterviewById(Integer id) {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Employee employee = employeeRepository.findByEmployeeId(id);
        Employee temp = employee;
        List<Interview> interviews= employee.getInterviews();
        System.out.println(interviews.toString());
        interviews = interviews.stream().filter(
                interview -> {
                    System.out.println(interview.getDate());
                    try {
                        Date d2 = sdformat.parse("2021-10-21");
                        if(interview.getDate().compareTo(d2)==0){
                            return false;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return true;
                }
                ).collect(Collectors.toList());
        System.out.println(interviews.toString());
        temp.setInterviews(interviews);
        employeeRepository.delete(employee);
        System.out.println(employee.toString());
//        employeeRepository.save(temp);
//        interviewRepository.deleteByEmpId(id);
    }
}
