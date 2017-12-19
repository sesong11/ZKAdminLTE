package com.sample.ZKSpringJPA.entity.request;

import com.sample.ZKSpringJPA.entity.employment.Employee;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "request")
public class Request {
    //region > Fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter @Setter
    @Column(name = "request_date", nullable = false)
    private Timestamp requestDate;

    @Getter @Setter
    @JoinColumn(name = "request_by", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee requestBy;

    @Getter @Setter
    @JoinColumn(name = "request_for")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee requestFor;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private RequestPriority priority;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private RequestStatus status;

    @Getter @Setter
    @Column(name = "form_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FormType formType;
    //endregion
}
