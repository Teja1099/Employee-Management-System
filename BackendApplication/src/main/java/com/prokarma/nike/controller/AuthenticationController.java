package com.prokarma.nike.controller;

import com.prokarma.nike.config.JWTTokenHelper;
import com.prokarma.nike.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws InvalidKeySpecException, NoSuchAlgorithmException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),authenticationRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Employee employee = (Employee) authentication.getPrincipal();

        String jwtToken = jwtTokenHelper.generateToken(employee.getUsername());

        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(jwtToken);
        Date currentDate = new Date();
        Date expireDate=jwtTokenHelper.getExpirationDate(jwtToken);
        response.setExpTime(expireDate.getTime()-currentDate.getTime());

        List<Authority> authorityList = (List<Authority>) employee.getAuthorities();
        String role = "USER";
        for (Authority authority : authorityList) {
            if(authority.getRoleCode().equals("ADMIN")) {
                role="ADMIN";
            }
        }
        response.setRole(role);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/auth/userinfo")
    public ResponseEntity<?> getUserInfo(Principal employee){
        Employee employeeObj=(Employee) userDetailsService.loadUserByUsername(employee.getName());

        UserInfo userInfo=new UserInfo();
        userInfo.setName(employeeObj.getName());
        userInfo.setEmpId(employeeObj.getEmpId());
        userInfo.setUserName(employeeObj.getUsername());
        userInfo.setRoles(employeeObj.getAuthorities().toArray());

        return ResponseEntity.ok(userInfo);
    }
}
