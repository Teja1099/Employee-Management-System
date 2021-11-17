package com.prokarma.nike;

import com.prokarma.nike.entity.*;
import com.prokarma.nike.repository.EmployeeRepository;
import com.prokarma.nike.repository.InterviewRepository;
import com.prokarma.nike.repository.LeaveRepository;
import com.prokarma.nike.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class BackendApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private InterviewRepository interviewRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@PostConstruct
	@Transactional
	protected void init() {

//		List<Authority> authorityList = new ArrayList<>();
//
//		authorityList.add( createAuthority(106917,"USER","User role"));
//		authorityList.add( createAuthority(106917,"ADMIN","Admin role"));
//
//		Employee employee = new Employee();
//		employee.setEmpId(106917);
//		employee.setPkEmail("spulluri@pkglobal.com");
//		employee.setName("Saiteja");
//		EmergencyContact emergencyContact = new EmergencyContact();
//		emergencyContact.setName("NA");
//		emergencyContact.setContact((long)1234567890);
//		employee.setPassword(passwordEncoder.encode("user@1234"));
//		employee.setEnabled(true);
//		employee.setAuthorities(authorityList);
//
//		employeeRepository.save(employee);

		//delete
//		Interview interview = interviewRepository.findByEmpId(7001);
//		System.out.println(interview.toString());
//		interviewRepository.deleteByEmpId(7001);
//		System.out.println("Hello world");

//		Employee employee = employeeService.getEmployeeByEmpId(7001);
//		List<Interview> interviews = employee.getInterviews();
//		interviews = interviews.stream().filter(interview -> !interview.getMonth().equals("October")).collect(Collectors.toList());
//		System.out.println(interviews);
//		employee.setInterviews(interviews);
//		employeeRepository.save(employee);

	}

	public static Authority createAuthority(Integer empId, String roleCode, String roleDescription) {
		Authority authority=new Authority();
		authority.setEmpId(empId);
		authority.setRoleCode(roleCode);
		authority.setRoleDescription(roleDescription);
		return authority;
	}


}
