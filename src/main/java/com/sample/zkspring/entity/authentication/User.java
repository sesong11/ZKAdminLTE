package com.sample.zkspring.entity.authentication;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Se Song
 *
 */
@Entity
@Table(name = "users", schema = "security")
public class User implements Serializable {
	
	/**
	 * Determines if a DeserializeObject file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version
	 * of this class is not compatible with old versions. 
	 */
	private static final long serialVersionUID = 1749962722143606146L;

	/**
	 * Auto generate id and table primary key
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	@Getter @Setter
	private Long id;
	
	/**
	 * User's username identification used by a user to access to the system.
	 * Username is unique identify. 
	 */
	@Column(name = "username", unique = true)
	@Getter @Setter
	private String username;
	
	/**
	 * User's secret word or phrase that must be used to gain admission to the system.
	 */
	@Column(name = "password")
	@Getter @Setter
	private String password;

	/**
	 * User's authority to access to the system, meant user has authority to access to the system while enabled (true).
	 */
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
