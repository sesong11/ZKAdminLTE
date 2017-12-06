package com.sample.ZKSpringJPA.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_id")
	@Getter @Setter
	private Long userId;
	
	@Column(name = "username", unique = true)
	@Getter @Setter
	private String username;
	
	@Column(name = "password")
	@Getter @Setter
	private String password;
	
	@Column(name = "email")
	@Getter @Setter
	private String email;
	
	@Column(name = "enabled")
	@Getter @Setter
	private int enabled;

	public User() {}
	
	public User(User user) {
		this.userId = user.userId;
		this.username = user.username;
		this.email = user.email;
		this.password = user.password;
		this.enabled = user.enabled;
	}
}
