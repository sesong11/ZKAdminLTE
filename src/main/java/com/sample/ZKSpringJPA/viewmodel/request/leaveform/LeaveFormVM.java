package com.sample.ZKSpringJPA.viewmodel.request.leaveform;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.employment.*;
import com.sample.ZKSpringJPA.entity.request.*;
import com.sample.ZKSpringJPA.entity.request.approval.Approval;
import com.sample.ZKSpringJPA.entity.request.approval.ApprovalType;
import com.sample.ZKSpringJPA.entity.request.leaveform.LeaveForm;
import com.sample.ZKSpringJPA.entity.request.leaveform.LeaveType;
import com.sample.ZKSpringJPA.services.employment.EmployeeService;
import com.sample.ZKSpringJPA.services.request.ApprovalService;
import com.sample.ZKSpringJPA.services.request.LeaveFormService;
import com.sample.ZKSpringJPA.services.request.RequestService;
import com.sample.ZKSpringJPA.utils.AppHelper;
import com.sample.ZKSpringJPA.utils.EmailHelper;
import com.sample.ZKSpringJPA.utils.StandardFormat;
import com.sample.ZKSpringJPA.utils.UserCredentialService;
import com.sample.ZKSpringJPA.viewmodel.utils.ListPagingVM;
import com.sample.ZKSpringJPA.viewmodel.utils.ViewModel;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.*;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/request/forms/leave-form.zul",
        uuid = "leave-form",
        menuOrder = "3.0.1",
        displayName = "Leave Request",
        menuIcon = "tag"
)

public class LeaveFormVM extends ViewModel {

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

    ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
    private javax.validation.Validator validator = vf.getValidator();

    private Set<ConstraintViolation<RequestForm>> violations;

    //endregion

    //region > Fields
    @Setter
    @Getter
    private LeaveForm form;

    @Getter @Setter
    private Double totalBalance;

    @Getter @Setter
    private Double existingBalance;

    @Getter @Setter
    private Double usedBalance;

    @Getter
    @Setter
    private Employee requestFor;

    @Getter
    @Setter
    private Approval relief;

    @Getter
    @Setter
    private Approval supervisor;

    @Getter
    @Setter
    private Approval manager;

    @Getter
    @Setter
    private List<LeaveType> leaveTypes = new ListModelList<>(LeaveType.values());

    @Getter
    @Setter
    private List<RequestPriority> requestPriorities = new ListModelList<>(RequestPriority.values());

    @Getter
    private final String standardDateTimeFormat = StandardFormat.getStandardDateTimeFormat();
    @Getter
    private final String email_path = "/view/request/email-template/leave-form.html";

    private String getSubject() {
        return "[REQ #" + getForm().getRequest().getId() + "] " + getForm().getRequest().getFormType().getName() + " (" + getForm().getLeaveType().getName() + ")";
    }
    //endregion

    //region > Constructor
    @Init
    public void init() {
        String sid = Executions.getCurrent().getParameter("id");
        if (sid == null) {
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
            totalBalance = form.getTotalBalance();
            existingBalance = form.getExistingBalance();
            usedBalance = form.getUsedBalance();

            TreeSet<Approval> approvals = requestService.findApproval(form.getRequest().getId());
            for (Approval approval : form.getRequest().getApprovals()) {
                if (approval.getApprovalType() == ApprovalType.RELIEF) {
                    relief = approval;
                }
                if (approval.getApprovalType() == ApprovalType.APPROVE) {
                    supervisor = approval;
                }
                if (approval.getApprovalType() == ApprovalType.AUTHORIZE) {
                    manager = approval;
                }
            }
        }
    }
    //endregion

    //region > Component
    @Command
    public void selectRequestFor() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("employees", employeeService.findPaging(0, StandardFormat.getDefaultPageSize()));
        map.put("totalSize", employeeService.count());
        map.put("receiver", "selectRequestForCallback");
        Window window = (Window) Executions.createComponents(
                "/view/component/employee-selector.zul", null, map);
        window.doModal();
    }

    @GlobalCommand
    public void selectRequestForCallback(@BindingParam("employee") final Employee employee) {
        this.form.getRequest().setRequestFor(employee);
        postNotifyChange(form.getRequest(), "requestFor");
    }

    @GlobalCommand
    public void selectRequestForCallbackFilter(
            @BindingParam("windows") Object window,
            @BindingParam("employees")ListModelList<Employee> employees,
            @BindingParam("pageSize") final int pageSize,
            @BindingParam("activePage") final int activePage,
            @BindingParam("filter") final String filter,
            @BindingParam("filterBy") final String filterBy,
            @BindingParam("filters") final HashMap<String, Object> filters) {

        int offset = activePage * pageSize;
        employees.clear();
        employees.addAll(employeeService.findPaging(offset, pageSize, filter, filterBy, filters));
        postNotifyChange(window, "employees");
        ListPagingVM win = (ListPagingVM) window;
        win.setTotalSize(employeeService.count(filter, filterBy, filters));
        postNotifyChange(window, "totalSize");
    }

    @Command
    public void selectRelief() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("employees", employeeService.findPaging(0, StandardFormat.getDefaultPageSize()));
        map.put("totalSize", employeeService.count());
        map.put("receiver", "selectReliefCallback");
        Window window = (Window) Executions.createComponents(
                "/view/component/employee-selector.zul", null, map);
        window.doModal();
    }

    public Validator getReliefValidator() {
        return new AbstractValidator() {
            public void validate(ValidationContext ctx) {
                if (relief.getApprovePerson() == null) {
                    addInvalidMessage(ctx, "You can't leave this empty.");
                }
            }
        };
    }

    @GlobalCommand
    public void selectReliefCallback(@BindingParam("employee") final Employee employee) {
        this.relief.setApprovePerson(employee);
        postNotifyChange(this,"relief");
    }

    @GlobalCommand
    public void selectReliefCallbackFilter(
            @BindingParam("windows") Object window,
            @BindingParam("employees")ListModelList<Employee> employees,
            @BindingParam("pageSize") final int pageSize,
            @BindingParam("activePage") final int activePage,
            @BindingParam("filter") final String filter,
            @BindingParam("filterBy") final String filterBy,
            @BindingParam("filters") final HashMap<String, Object> filters) {

        int offset = activePage * pageSize;
        employees.clear();
        employees.addAll(employeeService.findPaging(offset, pageSize, filter, filterBy, filters));
        postNotifyChange(window, "employees");
        ListPagingVM win = (ListPagingVM) window;
        win.setTotalSize(employeeService.count(filter, filterBy, filters));
        postNotifyChange(window, "totalSize");
    }

    @Command
    public void selectSupervisor() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("employees", employeeService.findPaging(0, StandardFormat.getDefaultPageSize()));
        map.put("totalSize", employeeService.count());
        map.put("receiver", "selectSupervisorCallback");
        Window window = (Window) Executions.createComponents(
                "/view/component/employee-selector.zul", null, map);
        window.doModal();
    }

    public Validator getSupervisorValidator() {
        return new AbstractValidator() {
            public void validate(ValidationContext ctx) {
                if (supervisor.getApprovePerson() == null) {
                    addInvalidMessage(ctx, "You can't leave this empty.");
                }
            }
        };
    }

    @GlobalCommand
    public void selectSupervisorCallback(@BindingParam("employee") final Employee employee) {
        this.supervisor.setApprovePerson(employee);
        postNotifyChange(this,"supervisor" );
    }

    @GlobalCommand
    public void selectSupervisorCallbackFilter(
            @BindingParam("windows") Object window,
            @BindingParam("employees")ListModelList<Employee> employees,
            @BindingParam("pageSize") final int pageSize,
            @BindingParam("activePage") final int activePage,
            @BindingParam("filter") final String filter,
            @BindingParam("filterBy") final String filterBy,
            @BindingParam("filters") final HashMap<String, Object> filters) {

        int offset = activePage * pageSize;
        employees.clear();
        employees.addAll(employeeService.findPaging(offset, pageSize, filter, filterBy, filters));
        postNotifyChange(window, "employees");
        ListPagingVM win = (ListPagingVM) window;
        win.setTotalSize(employeeService.count(filter, filterBy, filters));
        postNotifyChange(window, "totalSize");
    }

    @Command
    public void selectManager() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("employees", employeeService.findPaging(0, StandardFormat.getDefaultPageSize()));
        map.put("totalSize", employeeService.count());
        map.put("receiver", "selectManagerCallback");
        Window window = (Window) Executions.createComponents(
                "/view/component/employee-selector.zul", null, map);
        window.doModal();
    }

    public Validator getManagerValidator() {
        return new AbstractValidator() {
            public void validate(ValidationContext ctx) {
                if (manager.getApprovePerson() == null) {
                    addInvalidMessage(ctx, "You can't leave this empty.");
                }
            }
        };
    }

    @GlobalCommand
    public void selectManagerCallback(@BindingParam("employee") final Employee employee) {
        this.manager.setApprovePerson(employee);
        postNotifyChange(this, "manager" );
    }

    @GlobalCommand
    public void selectManagerCallbackFilter(
            @BindingParam("windows") Object window,
            @BindingParam("employees")ListModelList<Employee> employees,
            @BindingParam("pageSize") final int pageSize,
            @BindingParam("activePage") final int activePage,
            @BindingParam("filter") final String filter,
            @BindingParam("filterBy") final String filterBy,
            @BindingParam("filters") final HashMap<String, Object> filters) {

        int offset = activePage * pageSize;
        employees.clear();
        employees.addAll(employeeService.findPaging(offset, pageSize, filter, filterBy, filters));
        postNotifyChange(window, "employees");
        ListPagingVM win = (ListPagingVM) window;
        win.setTotalSize(employeeService.count(filter, filterBy, filters));
        postNotifyChange(window, "totalSize");
    }

    @Command
    @NotifyChange({"form", "relief", "supervisor", "manager"})
    public void submit() throws Exception {

        Request request = form.getRequest();
        request.setRequestDate(new Timestamp(new Date().getTime()));
        request.setRequestBy(userCredentialService.getCurrentEmployee());
        request.setStatus(RequestStatus.PENDING);
        request.setDecisionStatus(DecisionStatus.AWAITING);
        violations = validator.validate(form);

        if(violations.size()>0){
            String str = "Information you provide is invalid. Please make sure you input all mandatory (*) fields and try again.";
            Clients.showNotification(str, Clients.NOTIFICATION_TYPE_ERROR, null, "middle_center", 0, false);
            return;
        }
        relief.setId(null);
        supervisor.setId(null);
        manager.setId(null);
        request.getApprovals().clear();
        request = requestService.create(request);
        form.setRequest(request);
        form.setExistingBalance(existingBalance);
        form.setTotalBalance(totalBalance);
        form.setUsedBalance(usedBalance);
        leaveFormService.create(form);
        request.addApproval(relief);
        request.addApproval(supervisor);
        request.addApproval(manager);
        relief = approvalService.create(relief);
        supervisor = approvalService.create(supervisor);
        manager = approvalService.create(manager);

        String str = "Form submitted successfully and waiting for relief to confirm.";
        Clients.showNotification(str, Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 0, false);
        notifyRelief();
    }
    //endregion

    //region > Command
    @Command
    public void countDays(){
        Employee employee = form.getRequest().getRequestFor();
        if(employee!=null) {
            totalBalance = 0;
            existingBalance = 0;
            usedBalance = 0;
            for(EmployeeAllowance e: employee.getEmployeeAllowances()) {
                if(e.getAllowance().getAllowanceType() == form.getLeaveType().getAllowanceType()) {
                    totalBalance += e.getAllowanceBalance();
                }
            }
            Calendar nowCalendar = Calendar.getInstance();
            usedBalance = leaveFormService.countUsed(employee,
										form.getLeaveType().getAllowanceType(),
										nowCalendar.get(Calendar.YEAR));
            existingBalance = totalBalance - usedBalance;
            postNotifyChange(this, "totalBalance");
            postNotifyChange(this, "existingBalance");
            postNotifyChange(this, "usedBalance");
        }

        if(form.getFromDate() == null
                || form.getToDate() == null
                || form.getLeaveType() == null) {
            return;
        }
        double todayDays = form.getLeaveType().getCalculator().calculate(form);
        form.setTotalDays(todayDays);
        postNotifyChange(form, "totalDays");
    }
    //endregion


    //region > Validator
    public Validator getRequestForValidator() {
        return new AbstractValidator() {
            public void validate(ValidationContext ctx) {
                if (form.getRequest().getRequestFor() == null) {
                    addInvalidMessage(ctx, "requestFor", "You can't leave this empty.");
                }
            }
        };
    }
    //endregion

    //region > Notify
    public String getEmailTemplate() throws IOException {
        DateFormat df = new SimpleDateFormat(standardDateTimeFormat);
        HashMap<String, String> keyValues = new HashMap<String, String>();
        //Request Header
        keyValues.put("{Request-Title}", getSubject());

        keyValues.put("{Request-Id}", getForm().getRequest().getId().toString());
        keyValues.put("{Request-For}", getForm().getRequest().getRequestFor().getFullNameWithTitle());
        keyValues.put("{For-Position}", getForm().getRequest().getRequestFor().getDesignation().getName());
        keyValues.put("{Request-By}", getForm().getRequest().getRequestBy().getFullNameWithTitle());
        keyValues.put("{By-Position}", getForm().getRequest().getRequestBy().getDesignation().getName());

        keyValues.put("{Request-Status}", getForm().getRequest().getStatus().getName());
        keyValues.put("{Request-Priority}", getForm().getRequest().getPriority().getName());
        keyValues.put("{Request-Date}", df.format(getForm().getRequest().getRequestDate()));
        keyValues.put("{Reason}", getForm().getReason() == null ? "" : getForm().getReason().toString());
        keyValues.put("{Address}", getForm().getAddress() == null ? "" : getForm().getAddress().toString());

        // Relief
        keyValues.put("{Relief}", getRelief().getApprovePerson().getFullNameWithTitle());
        keyValues.put("{Relief-Designation}", getRelief().getApprovePerson().getDesignation().getName());
        keyValues.put("{Relief-Decision}", (getRelief().getDecisionStatus() == null)
                || (getRelief().getDecisionStatus() == DecisionStatus.AWAITING) ? "" : getRelief().getDecisionStatus().getName());
        keyValues.put("{Confirmed-Date}", getRelief().getApproveDate() == null ? "" : df.format(getRelief().getApproveDate()));
        keyValues.put("{Relief-Comment}", getRelief().getComment() == null ? "" : getRelief().getComment());

        // Superior
        keyValues.put("{Superior}", getSupervisor().getApprovePerson().getFullNameWithTitle());
        keyValues.put("{Superior-Designation}", getSupervisor().getApprovePerson().getDesignation().getName());
        keyValues.put("{Superior-Decision}", (getSupervisor().getDecisionStatus() == null)
                || (getSupervisor().getDecisionStatus() == DecisionStatus.AWAITING) ? "" : getSupervisor().getDecisionStatus().getName());
        keyValues.put("{Approve-Date}", getSupervisor().getApproveDate() == null ? "" : df.format(getSupervisor().getApproveDate()));
        keyValues.put("{Superior-Comment}", getSupervisor().getComment() == null ? "" : getSupervisor().getComment());

        // Manager
        keyValues.put("{Authorizer}", getManager().getApprovePerson().getFullNameWithTitle());
        keyValues.put("{Authorizer-Designation}", getManager().getApprovePerson().getDesignation().getName());
        keyValues.put("{Authorizer-Decision}", (getManager().getDecisionStatus() == null)
                || (getManager().getDecisionStatus() == DecisionStatus.AWAITING) ? "" : getManager().getDecisionStatus().getName());
        keyValues.put("{Authorize-Date}", getManager().getApproveDate() == null ? "" : df.format(getManager().getApproveDate()));
        keyValues.put("{Authorizer-Comment}", getManager().getComment() == null ? "" : getManager().getComment());


        return EmailHelper.replaceContentByPath(EmailHelper.getEmailTemplate(), keyValues);
    }

    @GlobalCommand
    public void notifyRelief() throws Exception {
        DateFormat df = new SimpleDateFormat(standardDateTimeFormat);
        HashMap<String, String> keyValues = new HashMap<String, String>();
        String content = " <p><b>{Requester-Name}</b> would like to request for relief when he/she is on <b>{Kind-of-Leave}</b> with <b>{Number-Days}</b> days from <b>{Start-Date}</b>" +
                " to <b>{End-Date}</b>.</p>";

        //Detail
        keyValues.put("{Relief}", getRelief().getApprovePerson().getFullNameWithTitle());
        keyValues.put("{Requester-Name}", getForm().getRequest().getRequestBy().getFullNameWithTitle());
        keyValues.put("{Kind-of-Leave}", getForm().getLeaveType().getName());
        keyValues.put("{Number-Days}", getForm().getTotalDays().toString());
        keyValues.put("{Start-Date}", df.format(getForm().getFromDate()));
        keyValues.put("{End-Date}", df.format(getForm().getToDate()));

        // Content
        keyValues.put("{Content}", content);

        // Recipient
        keyValues.put("{Recipient}", getRelief().getApprovePerson().getFullName());

        String email = getEmailTemplate().replace("{Detail}", EmailHelper.replaceContentByPath(email_path, keyValues));
        //Link
        keyValues.put("{Link}", AppHelper.APPLICATION_PATH+
                "?m=leave-form&id="+getForm().getRequest().getId());
        email = EmailHelper.replaceContentBySource(email, keyValues);

        List<String> toList = new ArrayList<String>();
        toList.add(getRelief().getApprovePerson().getEmail());

        List<String> ccList = new ArrayList<String>();
        toList.add(EmailHelper.getUsername());

        EmailHelper.sendMail(getSubject(), email, toList, ccList);
    }

    @GlobalCommand
    public void notifyRequester(@BindingParam("decision") DecisionStatus decisionStatus,
                                @BindingParam("approvalType") ApprovalType approvalType) throws Exception {

        HashMap<String, String> keyValues = new HashMap<String, String>();
        String content = "";

        if(DecisionStatus.APPROVED == decisionStatus) {
            if(ApprovalType.AUTHORIZE == approvalType) {
                content = " <p>Your leave request has been authorized by {Authorizer}.</p>";
            }
        } else if(DecisionStatus.REJECTED == decisionStatus) {
            if(ApprovalType.APPROVE == approvalType) {
                content = " <p>Your leave request has been reject by {Recommender} with reason as below: </p>" +
                        "<p>{Reject-Reason}</p>";
                keyValues.put("{Reject-Reason}", getSupervisor().getComment());
            } else if(ApprovalType.AUTHORIZE == approvalType) {
                content = " <p>Your leave request has been reject by {Authorizer} with reason as below: </p>" +
                        "<p>{Reject-Reason}</p>";
                keyValues.put("{Reject-Reason}", getManager().getComment());
            }
        }

        //Detail
        keyValues.put("{Recommender}", getSupervisor().getApprovePerson().getFullNameWithTitle());
        keyValues.put("{Authorizer}", getManager().getApprovePerson().getFullNameWithTitle());

        // Content
        keyValues.put("{Content}", content);

        // Recipient
        keyValues.put("{Recipient}", getRelief().getApprovePerson().getFullName());

        String email = getEmailTemplate().replace("{Detail}", EmailHelper.replaceContentByPath(email_path, keyValues));
        //Link
        keyValues.put("{Link}", AppHelper.APPLICATION_PATH+
                "?m=leave-form&id="+getForm().getRequest().getId());
        email = EmailHelper.replaceContentBySource(email, keyValues);

        List<String> toList = new ArrayList<String>();
        toList.add(getForm().getRequest().getRequestBy().getEmail());

        List<String> ccList = new ArrayList<String>();
        ccList.add(EmailHelper.getUsername());
        EmailHelper.sendMail(getSubject(),email, toList, ccList);

    }

    @GlobalCommand
    public void notifySuperior (@BindingParam("decision")DecisionStatus decisionStatus) throws Exception {
        DateFormat df = new SimpleDateFormat(standardDateTimeFormat);
        HashMap<String, String> keyValues = new HashMap<String, String>();
        String content = " <p><b>{Superior}</b> has been acknowledged for the leave request below: </p>" +
                "<p><b>{Requester-Name}</b> would like to request for <b>{Kind-of-Leave}</b> with <b>{Number-Days}</b> days from <b>{Start-Date}</b>" +
                " to <b>{End-Date}</b>.</p>";

        //Detail
        keyValues.put("{Superior}", getSupervisor().getApprovePerson().getFullNameWithTitle());
        keyValues.put("{Requester-Name}", getForm().getRequest().getRequestBy().getFullName());
        keyValues.put("{Reason}", getForm().getReason());
        keyValues.put("{Kind-of-Leave}", getForm().getLeaveType().getName());
        keyValues.put("{Number-Days}", getForm().getTotalDays().toString());
        keyValues.put("{Start-Date}", df.format(getForm().getFromDate()));
        keyValues.put("{End-Date}",  df.format(getForm().getToDate()));

        // Content
        keyValues.put("{Content}", content);

        // Recipient
        keyValues.put("{Recipient}", getRelief().getApprovePerson().getFullNameWithTitle());

        String email = getEmailTemplate().replace("{Detail}", EmailHelper.replaceContentByPath(email_path, keyValues));
        //Link
        keyValues.put("{Link}", AppHelper.APPLICATION_PATH+
                "?m=leave-form&id="+getForm().getRequest().getId());
        email = EmailHelper.replaceContentBySource(email, keyValues);

        List<String> toList = new ArrayList<String>();
        toList.add(getSupervisor().getApprovePerson().getEmail());

        List<String> ccList = new ArrayList<String>();
        ccList.add(EmailHelper.getUsername());
        EmailHelper.sendMail(getSubject(), email, toList, ccList);
    }
    @GlobalCommand
    public void notifyManager (@BindingParam("decision") DecisionStatus decisionStatus) throws Exception {
        DateFormat df = new SimpleDateFormat(standardDateTimeFormat);
        HashMap<String, String> keyValues = new HashMap<String, String>();
        String content = " <p><b>{Recommender}</b> has been recommended to the leave request below:</p>" +
                " <p><b>{Requester-Name}</b> would like to request for <b>{Kind-of-Leave}</b> with <b>{Number-Days}</b> days from <b>{Start-Date}</b>" +
                " to <b>{End-Date}</b>.</p>";
        //Detail
        keyValues.put("{Relief}", getRelief().getApprovePerson().getFullNameWithTitle());
        keyValues.put("{Requester-Name}", getForm().getRequest().getRequestBy().getFullNameWithTitle());
        keyValues.put("{Kind-of-Leave}", getForm().getLeaveType().getName());
        keyValues.put("{Number-Days}", getForm().getTotalDays().toString());
        keyValues.put("{Start-Date}", df.format(getForm().getFromDate()));
        keyValues.put("{End-Date}", df.format(getForm().getToDate()));

        // Relief
        keyValues.put("{Relief}", getRelief().getApprovePerson().getFullNameWithTitle());
        keyValues.put("{Confirmed-Date}", getForm().getToDate().toString());

        // Superior
        keyValues.put("{Recommender}", getSupervisor().getApprovePerson().getFullNameWithTitle());
        keyValues.put("{Recommend-Date}", getForm().getToDate().toString());

        // Manager
        keyValues.put("{Authorizer}", getManager().getApprovePerson().getFullNameWithTitle());
        keyValues.put("{Authorizer-Date}", getForm().getToDate().toString());

        // Content
        keyValues.put("{Content}", content);

        // Recipient
        keyValues.put("{Recipient}", getRelief().getApprovePerson().getFullNameWithTitle());
        //Link
        keyValues.put("{Link}", AppHelper.APPLICATION_PATH+
                "?m=leave-form&id="+getForm().getRequest().getId());
        String email = getEmailTemplate().replace("{Detail}", EmailHelper.replaceContentByPath(email_path, keyValues));
        email = EmailHelper.replaceContentBySource(email, keyValues);

        List<String> toList = new ArrayList<String>();
        toList.add(getManager().getApprovePerson().getEmail());

        List<String> ccList = new ArrayList<String>();
        ccList.add(EmailHelper.getUsername());
        EmailHelper.sendMail(getSubject(), email, toList, null);
    }
    //endregion
}

