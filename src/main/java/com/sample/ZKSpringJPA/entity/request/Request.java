package com.sample.ZKSpringJPA.entity.request;

import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.entity.request.approval.Approval;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.SortedSet;
import java.util.TreeSet;

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

    @Getter @Setter
    @Column(name = "decision_status")
    @Enumerated(EnumType.STRING)
    private DecisionStatus decisionStatus;

    @Getter @Setter
    @OneToMany(mappedBy = "request", fetch = FetchType.EAGER)
    @OrderBy("id")
    private SortedSet<Approval> approvals;
    //endregion

    //region > Programmatic
    public void addApproval(final Approval approval){
        if(this.getApprovals() == null){
            approvals = new TreeSet<>();
        }
        approval.setRequest(this);
        this.getApprovals().add(approval);
        System.out.println(approval.getApprovalType().getName());
    }
    //endregion
}
