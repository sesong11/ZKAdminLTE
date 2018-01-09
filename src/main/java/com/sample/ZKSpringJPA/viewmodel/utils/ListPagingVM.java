package com.sample.ZKSpringJPA.viewmodel.utils;

import com.sample.ZKSpringJPA.utils.StandardFormat;
import lombok.Getter;
import lombok.Setter;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

public abstract class ListPagingVM extends ViewModel {
    @Getter
    @Setter
    private int pageSize = StandardFormat.getDefaultPageSize();

    @Getter
    @Setter
    private int totalSize;

    @Getter
    @Setter
    private int activePage;

    @Getter @Setter
    private String filter;

    @Command
    public void changeActivePage(@BindingParam("index") final int activePage) {
        this.activePage = activePage;
        int offset = activePage * pageSize;
        research(offset, pageSize);
    }

    @Command
    public void changePageSize(@BindingParam("size") final int pageSize) {
        this.pageSize = pageSize;
        research(0, pageSize);
        postNotifyChange(this,"pageSize");
    }

    @Command
    public void filter(@BindingParam("filter") final String filter){
        this.filter = filter;
        research(0, getPageSize());
    }

    public abstract void research(final int offset, final int limit);
}