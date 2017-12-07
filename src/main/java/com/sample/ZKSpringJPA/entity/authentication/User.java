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
@Table(name = "users")
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
	
	@Column(name = "email")
	@Getter @Setter
	private String email;
	
	@Column(name = "enabled")
	@Getter @Setter
	private boolean enabled;

	public User() {}
	
	public User(User user) {
		this.id = user.id;
		this.username = user.username;
		this.email = user.email;
		this.password = user.password;
		this.enabled = user.enabled;
	}

	@Getter @Setter
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")

	)
	private List<Role> roles;
}
