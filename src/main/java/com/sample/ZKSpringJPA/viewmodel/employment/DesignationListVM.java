package com.sample.ZKSpringJPA.viewmodel.employment;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.employment.Branch;
import com.sample.ZKSpringJPA.entity.employment.Designation;
import com.sample.ZKSpringJPA.entity.employment.Employee;
import com.sample.ZKSpringJPA.services.employment.BranchService;
import com.sample.ZKSpringJPA.services.employment.DesignationService;

import com.sample.ZKSpringJPA.utils.StandardFormat;
import com.sample.ZKSpringJPA.viewmodel.utils.ListPagingVM;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.ListModelList;

import lombok.Getter;
import lombok.Setter;
import sun.security.krb5.internal.crypto.Des;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
@Feature(
        view = "/view/employment/designationlist.zul",
        uuid = "designationlist",
        menuOrder = "2.3",
        displayName = "Designation List",
        menuIcon = "id-card"
)
public class DesignationListVM extends ListPagingVM {

    //region > Inject Service
    @WireVariable
    private DesignationService designationService;
    //endregion

    //region > Fields
    @Getter @Setter
    private ListModelList<Designation> designations;

    @Getter @Setter
    private Designation designation;
    //endregion

    //region > Constructor
    @Init
    public void init(){
        research(0, getPageSize());
        setTotalSize(designationService.count());
        designation = new Designation();
    }
    //endregion

    //region > Command
    @Command
    @NotifyChange({"designation"})
    public void create(){
        designation = designationService.create(designation);
        designations.add(designation);
    }

    @Command
    @NotifyChange({"designation"})
    public void update(){
        designation = designationService.update(designation);
    }

    @Command
    @NotifyChange({"designation"})
    public void delete(){
        designationService.delete(designation);
        designations.remove(designation);
        designation = new Designation();
    }

    @Command
    @NotifyChange({"designation"})
    public void cancel(){
        designation = new Designation();
    }

    @Command
    @NotifyChange({"designation"})
    public void select(@BindingParam("designation") final Designation designation){
        this.designation = designation;
    }

    //endregion

    //region > Programmatic
    @Override
    public void research(final int offset, final int limit){
        designations = new ListModelList<Designation>(designationService.findPaging(offset, limit));
        postNotifyChange(this,"designations");
    }
    //endregion
}
