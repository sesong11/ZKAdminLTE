package com.sample.ZKSpringJPA.entity.dashboard;


import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.Command;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "application", schema = "dashboard")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @Setter @Getter
    private Long id;

    @Column(name = "parent_id")
    @Setter @Getter
    private Long parentId;

    @Column(name = "title")
    @NotNull(message = "You can't leave this empty.")

    @Setter @Getter
    private String title;

    @Column(name = "link")
    @NotNull
    @Setter @Getter
    private String link;

    @Column(name = "description")
    @NotNull
    @Setter @Getter
    private String description;

    @Column(name = "is_enabled")
    @Setter @Getter
    private Boolean enabled;

    @Column(name = "created_at")
    @Setter @Getter
    private Timestamp createdAt;

    @Column(name = "sequence")
    @Setter @Getter
    private Long sequence;

    @Column(name = "image_src")
    @Setter @Getter
    private String imageSrc;



}
