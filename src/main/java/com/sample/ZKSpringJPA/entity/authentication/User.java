package com.sample.ZKSpringJPA.entity.authentication;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.sample.ZKSpringJPA.utils.TableSchemas;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users", schema = "security")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	@Getter @Setter
	private long id;
	
	@Column(name = "username", unique = true)
	@Getter @Setter
	private String username;
	
	@Column(name = "password")
	@Getter @Setter
	private String password;

	@Column(name = "enabled")
	@Getter @Setter
	private boolean enabled;

	public User() {}
	
	public User(User user) {
		this.id = user.id;
		this.username = user.username;
		this.password = user.password;
		this.enabled = user.enabled;
	}

	@Getter @Setter
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private Set<Role> roles;
}
