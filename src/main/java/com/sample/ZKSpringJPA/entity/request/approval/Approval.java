package com.sample.ZKSpringJPA.entity.request.approval;

import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.entity.request.DecisionStatus;
import com.sample.ZKSpringJPA.entity.request.Request;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "approval")
public class Approval {
    //region > Fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    @Column(name = "id")
    private Long id;

    @Getter @Setter
    private ApprovalType approvalType;

    @Getter @Setter
    @JoinColumn(name = "approve_person_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee approvePerson;

    @Getter @Setter
    @Column(name = "decision_status")
    @Enumerated(EnumType.STRING)
    private DecisionStatus decisionStatus;

    @Getter @Setter
    @Column(name = "approve_date")
    private Timestamp approveDate;

    @Getter @Setter
    @JoinColumn(name = "request_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Request request;
    //endregion
}
