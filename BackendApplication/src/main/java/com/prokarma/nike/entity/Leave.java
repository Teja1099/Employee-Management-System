package com.prokarma.nike.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "UC_pkEmail_empId",
                columnNames = {
                }
        )
)
//@Where(clause = "deleted=false")
public class Leave {

    @Id
    @SequenceGenerator(
            name = "leave_sequence",
            sequenceName = "leave_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "leave_sequence"
    )
    private Integer id;

    @Column(
            nullable = false
    )
    private Integer empId;

    @Column(
            nullable = false
    )
    private String pkEmail;

    @Column(
            nullable = false
    )
    private String name;
    private String projectCode;
    private String month;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String days;
    private Integer year;
    private Boolean deleted =Boolean.FALSE;


}
