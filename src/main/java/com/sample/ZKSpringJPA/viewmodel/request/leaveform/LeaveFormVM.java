package com.sample.ZKSpringJPA.viewmodel.request.leaveform;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.entity.request.*;
import com.sample.ZKSpringJPA.entity.request.approval.Approval;
import com.sample.ZKSpringJPA.entity.request.approval.ApprovalType;
import com.sample.ZKSpringJPA.entity.request.leaveform.LeaveForm;
import com.sample.ZKSpringJPA.entity.request.leaveform.LeaveType;
import com.sample.ZKSpringJPA.services.employment.EmployeeService;
import com.sample.ZKSpringJPA.services.request.ApprovalService;
import com.sample.ZKSpringJPA.services.request.LeaveFormService;
import com.sample.ZKSpringJPA.services.request.RequestService;
import com.sample.ZKSpringJPA.utils.StandardFormat;
import com.sample.ZKSpringJPA.utils.UserCredentialService;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.TreeSet;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/request/forms/leave-form.zul",
        uuid = "leave-form",
        menuOrder = "3.0.1",
        displayName = "Leave Request",
        menuIcon = "tag"
)
public class LeaveFormVM {
    //region > Inject Services
    @WireVariable
    private EmployeeService employeeService;

    @WireVariable
    private RequestService requestService;

    @WireVariable
    private LeaveFormService leaveFormService;

    @WireVariable
    private ApprovalService approvalService;

    @WireVariable
    private UserCredentialService userCredentialService;
    //endregion

    //region > Fields
    @Setter @Getter
    private LeaveForm form;

    @Getter @Setter
    private Employee requestFor;

    @Getter @Setter
    private Approval relief;

    @Getter @Setter
    private Approval supervisor;

    @Getter @Setter
    private Approval manager;

    @Getter @Setter
    private List<LeaveType> leaveTypes = new ListModelList<>(LeaveType.values());

    @Getter @Setter
    private List<RequestPriority> requestPriorities = new ListModelList<>(RequestPriority.values());

    @Getter
    private final String standardDateTimeFormat = StandardFormat.getStandardDateTimeFormat();
    //endregion

    //region > Constructor
    @Init
    public void init(){
        String sid = Executions.getCurrent().getParameter("id");
        if(sid==null) {
            form = new LeaveForm();
            Request request = new Request();
            request.setFormType(FormType.LEAVE_REQUEST);
            form.setRequest(request);
            //person who relief requester job during his/her leave.
            relief = new Approval();
            relief.setSortedIndex(1);
            relief.setId(1L);
            relief.setDecisionStatus(DecisionStatus.AWAITING);
            relief.setApprovalType(ApprovalType.RELIEF);
            request.addApproval(relief);
            //direct supervisor who approve the request
            supervisor = new Approval();
            supervisor.setSortedIndex(2);
            supervisor.setId(2L);
            supervisor.setApprovalType(ApprovalType.APPROVE);
            request.addApproval(supervisor);
            //head department who authorize the request
            manager = new Approval();
            manager.setSortedIndex(3);
            manager.setId(3L);
            manager.setApprovalType(ApprovalType.AUTHORIZE);
            request.addApproval(manager);
        } else {
            Long id = Long.parseLong(sid);
            form = leaveFormService.findByRequestId(id);
            TreeSet<Approval> approvals = requestService.findApproval(form.getRequest().getId());
            for(Approval approval: form.getRequest().getApprovals()){
                if(approval.getApprovalType() == ApprovalType.RELIEF){
                    relief = approval;
                }
                if(approval.getApprovalType() == ApprovalType.APPROVE){
                    supervisor = approval;
                }
                if(approval.getApprovalType() == ApprovalType.AUTHORIZE){
                    manager = approval;
                }
            }
        }
    }
    //endregion

    //region > Component
    @Command
    public void selectRequestFor(){
        final HashMap<String, Object> map = new HashMap<>();
        map.put("employees", employeeService.findAll());
        map.put("receiver", "selectRequestForCallback");
        Window window = (Window) Executions.createComponents(
                "/view/component/employee-selector.zul", null, map);
        window.doModal();
    }
    @GlobalCommand
    public void selectRequestForCallback(@BindingParam("employee") final Employee employee){
        this.form.getRequest().setRequestFor(employee);
        postNotifyChange("form");
    }

    @Command
    public void selectRelief(){
        final HashMap<String, Object> map = new HashMap<>();
        map.put("employees", employeeService.findAll());
        map.put("receiver", "selectReliefCallback");
        Window window = (Window) Executions.createComponents(
                "/view/component/employee-selector.zul", null, map);
        window.doModal();
    }
    @GlobalCommand
    public void selectReliefCallback(@BindingParam("employee") final Employee employee){
        this.relief.setApprovePerson(employee);
        postNotifyChange("relief");
    }

    @Command
    public void selectSupervisor(){
        final HashMap<String, Object> map = new HashMap<>();
        map.put("employees", employeeService.findAll());
        map.put("receiver", "selectSupervisorCallback");
        Window window = (Window) Executions.createComponents(
                "/view/component/employee-selector.zul", null, map);
        window.doModal();
    }
    @GlobalCommand
    public void selectSupervisorCallback(@BindingParam("employee") final Employee employee){
        this.supervisor.setApprovePerson(employee);
        postNotifyChange("supervisor");
    }

    @Command
    public void selectManager(){
        final HashMap<String, Object> map = new HashMap<>();
        map.put("employees", employeeService.findAll());
        map.put("receiver", "selectManagerCallback");
        Window window = (Window) Executions.createComponents(
                "/view/component/employee-selector.zul", null, map);
        window.doModal();
    }
    @GlobalCommand
    public void selectManagerCallback(@BindingParam("employee") final Employee employee){
        this.manager.setApprovePerson(employee);
        postNotifyChange("manager");
    }

    @Command
    @NotifyChange({"form", "relief", "supervisor", "manager"})
    public void submit(){
        Request request = form.getRequest();
        request.setRequestDate(new Timestamp(new Date().getTime()));
        request.setRequestBy(userCredentialService.getCurrentEmployee());
        request.setStatus(RequestStatus.PENDING);
        request.getApprovals().clear();
        request = requestService.create(request);
        form.setRequest(request);
        leaveFormService.create(form);
        request.addApproval(relief);
        request.addApproval(supervisor);
        request.addApproval(manager);
        relief.setId(null);
        relief = approvalService.create(relief);
        supervisor.setId(null);
        supervisor = approvalService.create(supervisor);
        manager.setId(null);
        manager = approvalService.create(manager);
    }
    //endregion

    //region > Programmatic
    private void postNotifyChange(final String property){
        BindUtils.postNotifyChange(null,null,this,property);
    }
    //endregion
}
