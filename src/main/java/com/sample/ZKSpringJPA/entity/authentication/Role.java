package com.sample.ZKSpringJPA.entity.authentication;

import com.sample.ZKSpringJPA.utils.TableSchemas;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="roles", schema = "security")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private long id;

    @Getter @Setter
    @Column(unique = true)
    private String name;

    @Getter @Setter
    @ManyToMany(mappedBy = "roles", targetEntity = User.class)
    private Set<User> users;

    @Getter @Setter
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private Set<RolePermission> permissions;

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
