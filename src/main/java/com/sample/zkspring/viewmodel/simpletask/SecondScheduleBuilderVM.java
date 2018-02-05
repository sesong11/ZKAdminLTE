package com.sample.zkspring.viewmodel.simpletask;

import com.sample.zkspring.viewmodel.simpletask.builder.SecondScheduleBuilder;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import lombok.Getter;
import lombok.Setter;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class SecondScheduleBuilderVM {
    @Wire("#modalDialog")
    private Window win;

    @Getter @Setter
    private SecondScheduleBuilder secondScheduleBuilder;


    @Command
    public void submit(){

    }


}
