package com.prokarma.nike.repository;

import com.prokarma.nike.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer>
{
    Employee findByPkEmail(String userName);

    @Query("SELECT e FROM employee e ORDER BY e.project DESC")
    List<Employee> sortByProject();

    @Query(
            value = "SELECT * FROM employee e WHERE e.emp_id = :employeeId",
            nativeQuery = true
    )
    Employee findByEmployeeId(@Param("employeeId") Integer employeeId);


    // use transaction and modifying for update


}
