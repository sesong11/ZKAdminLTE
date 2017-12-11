package com.sample.ZKSpringJPA.entity.authentication;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class RolePermission {
    @Id
    @Getter @Setter
    private long id;

    @Column(nullable = false)
    @Getter @Setter
    private String feature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id")
    @Getter @Setter
    private Role role;

}
