package com.prokarma.nike.services;


import com.prokarma.nike.entity.Employee;
import com.prokarma.nike.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Employee employee= employeeRepository.findByPkEmail(s);

        if(null==employee) {
            throw new UsernameNotFoundException("Employee not found with userName"+s);
        }
        return employee;
    }
}
