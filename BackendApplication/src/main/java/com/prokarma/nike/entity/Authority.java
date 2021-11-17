package com.prokarma.nike.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Table(name = "AUTH_AUTHORITY")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements GrantedAuthority {

	@Id
	@SequenceGenerator(
			name = "authority_sequence",
			sequenceName = "authority_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "authority_sequence"
	)
	private Integer id;

	private Integer empId;
	
	@Column(name = "ROLE_CODE")
	private String roleCode;
	
	@Column(name = "ROLE_DESCRIPTION")
	private String roleDescription;
	
	

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return roleCode;
	}





	
	
}
