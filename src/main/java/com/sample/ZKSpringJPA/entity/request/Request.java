package com.sample.ZKSpringJPA.entity.request;

import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.entity.request.approval.Approval;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "request", schema = "req")
public class Request {
    //region > Fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter @Setter
    @Column(name = "request_date", nullable = false)
    @NotNull(message = "You can't leave this empty.")
    private Timestamp requestDate;

    @Getter @Setter
    @JoinColumn(name = "request_by", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "You can't leave this empty.")
    private Employee requestBy;

    @Getter @Setter
    @JoinColumn(name = "request_for", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "You can't leave this empty.")
    private Employee requestFor;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    @NotNull(message = "Priority is required.")
    private RequestPriority priority;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    @NotNull(message = "Request Status is required.")
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
    @Valid
    private SortedSet<Approval> approvals;
    //endregion

    //region > Programmatic
    public void addApproval(final Approval approval){
        if(this.getApprovals() == null){
            approvals = new TreeSet<>();
        }
        approval.setRequest(this);
        this.getApprovals().add(approval);
    }
    //endregion
}
