package com.sample.ZKSpringJPA.entity.authentication;

import com.sample.ZKSpringJPA.utils.TableSchemas;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private long id;

    @Getter @Setter
    @Column(unique = true)
    private String name;

    @Getter @Setter
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
