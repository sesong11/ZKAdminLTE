package com.sample.zkspring.entity.authentication;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Se Song
 *
 */
@Entity
@Table(name = "role_permission", schema = "security")
public class RolePermission implements Serializable {
	/**
	 * Determines if a DeserializeObject file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version
	 * of this class is not compatible with old versions. 
	 */
	private static final long serialVersionUID = -4004605564715326187L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @Column(nullable = false)
    @Getter @Setter
    private String feature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id")
    @Getter @Setter
    private Role role;
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		RolePermission other = (RolePermission)obj;
		if(id == null) {
			if(other.id != null)
				return false;
		} else if(!id.equals(other.id))
			return false;
		return true;
	}

}
