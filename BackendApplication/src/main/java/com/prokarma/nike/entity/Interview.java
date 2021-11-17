package com.prokarma.nike.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.Reference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Where(clause = "deleted=false")
public class Interview {
    @Id
    @SequenceGenerator(
            name = "interview_sequence",
            sequenceName = "interview_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "interview_sequence"
    )
    private Integer id;
    private String name;
    private Integer empId;
    private String interviewType;
    private String round;
    private String technologyStack;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String timeSpent;
    private String candidateStatus;
    private String comments;
    private String month;
    private Integer year;
    private Boolean deleted =Boolean.FALSE;


}
