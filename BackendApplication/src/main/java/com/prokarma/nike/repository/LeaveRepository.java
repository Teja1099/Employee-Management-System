package com.prokarma.nike.repository;

import com.prokarma.nike.entity.Employee;
import com.prokarma.nike.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave,Integer> {

    @Query("select l from Leave l where l.month = :month")
    List<Leave> findAllByMonth(@Param("month") String month);


    @Query("select l from Leave l where l.month = :month AND l.year =:year")
    List<Leave> findAllByMonthAndYear(@Param("month") String month,@Param("year") Integer year);

    @Modifying
    @Query(
            value = "update leave  set deleted=1 WHERE emp_id = :employeeId",
            nativeQuery = true
    )
    void deleteByEmployeeId(@Param("employeeId") Integer employeeId);

    @Query("select l from Leave l where l.empId = :id ")
    Leave findByEmpId(Integer id);
}
