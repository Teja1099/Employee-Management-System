package com.prokarma.nike.repository;

import com.prokarma.nike.entity.Interview;
import com.prokarma.nike.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
@Transactional
public interface InterviewRepository extends JpaRepository<Interview, Integer> {

    @Query(value = "select i from Interview i where i.month = :month ")
    public List<Interview> findByMonthAndYear(@Param("month") String mon);

    @Modifying
    @Query(
            value = "update interview  set deleted=1 WHERE emp_id = :employeeId",
            nativeQuery = true
    )
    void deleteByEmployeeId(@Param("employeeId") Integer employeeId);

    @Query("select i from Interview i where i.empId = :id")
    Interview findByEmpId(@Param("id") Integer id);
//

    @Modifying
    @Query("delete from Interview where empId=:employeeId  and date='2021-10-21' ")
    void deleteByEmpId(@Param("employeeId") Integer employeeId);

}
