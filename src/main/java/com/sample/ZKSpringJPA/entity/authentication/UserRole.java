package com.sample.ZKSpringJPA.entity.authentication;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_roles")
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_role_id")
	@Getter @Setter
	private Long userRoleId;
	
	@Column(name = "user_id")
	@Getter @Setter
	private Long userId;
	
	@Column(name = "role")
	@Getter @Setter
	private String role;
}
