package com.prokarma.nike.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

// if possible change it to object type
@Entity(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Where(clause = "deleted=false")
public class Employee implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )
    private Integer id;
    private Integer empId;
    private String name;

    private String password;

    private String pkEmail;
    private String status;
    private String status2;
    private String nikeId;
    private String nikeEmail;
    // try date
    @Temporal(TemporalType.DATE)
    private Date DOB;
    @Temporal(TemporalType.DATE)
    private Date joiningDate;
    // try boolean
    private Long contact;
    private String baseLocation;
    private String role;
    private String project;
    private String projectCode;
    private String designation;
    // try new class or object
    @Embedded
    private EmergencyContact emergencyContact;
    // try boolean
    private String hasH1;
    private String totalExperienceInIT;
    private String totalExperienceInPK;
    private String totalExperienceInNIKE;
    // try collection
    private String primarySkills;
    // try collection
    private String sceondarySkills;
    // try collections
    private String address;
    // try collections
    private String certification;
    private String comments;

    private Boolean deleted =Boolean.FALSE;

    private boolean enabled =Boolean.TRUE;

    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "AUTH_USER_AUTHORITY",
            joinColumns = @JoinColumn(referencedColumnName = "empId"),
            inverseJoinColumns = @JoinColumn(referencedColumnName ="id"))
    private List<Authority> authorities;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "EMPLOYEE_ID_LEAVE_ID",
            joinColumns = @JoinColumn(referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName ="id"))
    private List<Leave> leaves;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "EMPLOYEE_ID_Interview_ID",
            joinColumns = @JoinColumn(referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName ="id"))
    private List<Interview> interviews;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.pkEmail;
    }

    @Override
    public String getPassword() { return this.password; }

    @Override
    public boolean isAccountNonExpired() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.enabled;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
