package com.sample.zkspring.entity.authentication;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Se Song
 *
 */

@Entity
@Table(name="roles", schema = "security")
public class Role implements Serializable {

	/**
	 * Determines if a DeserializeObject file is compatible with this class.
	 *
	 * Maintainers must change this value if and only if the new version
	 * of this class is not compatible with old versions. 
	 */
	private static final long serialVersionUID = -1803757644480803349L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    @Column(unique = true)
    private String name;

    @Getter @Setter
    @ManyToMany(mappedBy = "roles", targetEntity = User.class)
    private Set<User> users;

    @Getter @Setter
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private Set<RolePermission> permissions;

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
		Role other = (Role)obj;
		if(id == null) {
			if(other.id != null)
				return false;
		} else if(!id.equals(other.id))
			return false;
		return true;
	}
	
    public void add(RolePermission rolePermission) {
        if(permissions==null){
            permissions = new HashSet<>();
        }
        permissions.add(rolePermission);
    }

    public void add(List<RolePermission> roleTemplateList) {
        if(roleTemplateList!=null) {
            for (RolePermission rolePermission : roleTemplateList) {
                this.add(rolePermission);
            }
        }
    }

    public void remove(RolePermission rolePermission) {
        if(permissions!=null){
            permissions.remove(rolePermission);
        }
    }

    public void remove(List<RolePermission> roleTemplateList) {
        if(permissions!=null){
            permissions.removeAll(roleTemplateList);
        }
    }
}
