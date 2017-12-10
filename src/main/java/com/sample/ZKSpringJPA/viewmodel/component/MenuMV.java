package com.sample.ZKSpringJPA.viewmodel.component;

import com.sample.ZKSpringJPA.utils.Menu;

import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

import lombok.Getter;
import lombok.Setter;

public class MenuMV {

    @Getter @Setter
    private Menu menu;

    @Init
    public void init(@ExecutionArgParam("menu") final Menu menu){
        this.menu = menu;
    }
}
