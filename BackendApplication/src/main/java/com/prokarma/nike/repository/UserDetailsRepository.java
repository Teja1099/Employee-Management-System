package com.prokarma.nike.repository;

import com.prokarma.nike.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<Employee,Integer> {

}
