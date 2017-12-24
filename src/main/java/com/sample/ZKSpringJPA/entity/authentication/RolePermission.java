package com.sample.ZKSpringJPA.entity.authentication;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role_permission", schema = "security")
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
