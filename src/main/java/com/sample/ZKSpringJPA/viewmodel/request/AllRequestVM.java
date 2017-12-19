package com.sample.ZKSpringJPA.viewmodel.request;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.request.Request;
import com.sample.ZKSpringJPA.services.request.RequestService;
import com.sample.ZKSpringJPA.utils.StandardFormat;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;

import javax.swing.*;
import java.util.List;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/request/all-request.zul",
        uuid = "6all-request",
        menuOrder = "3.6",
        displayName = "All Request",
        menuIcon = "list-alt"
)
public class AllRequestVM {
    //region > Inject Service
    @WireVariable
    private RequestService requestService;
    //endregion

    //region > Fields
    @Getter @Setter
    private List<Request> requests;

    @Getter
    private String standardDateTimeFormat = StandardFormat.getStandardDateTimeFormat();
    //endregion

    //region > Constructor
    @Init
    public void init() {
        requests = new ListModelList<Request>(requestService.findAll());
    }
    //endregion

    //region > Command
    @Command
    public void open(@BindingParam("item") final Request request){

    }
    //endregion
}
