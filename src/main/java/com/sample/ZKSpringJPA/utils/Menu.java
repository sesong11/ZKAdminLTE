package com.sample.ZKSpringJPA.utils;

import com.sample.ZKSpringJPA.anotation.Feature;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    @Getter @Setter
    private Feature feature;

    @Getter @Setter
    private String displayName;

    public Menu(){}

    public Menu(Feature feature){
        this.feature = feature;
        displayName = feature.displayName();
    }

    @Getter
    private List<Menu> subMenu;

    public boolean addMenu(final Feature feature, String menuOrder) {
        String[] orders = menuOrder.split("\\.");
        System.out.println(orders.length+": menu order: "+menuOrder);
        if (orders.length == 1) {
            Menu menu = new Menu(feature);
            if (subMenu == null) {
                subMenu = new ArrayList<>();
            }
            subMenu.add(menu);
            System.out.println("Added Menu: "+feature.displayName());
            return true;
        }

        if(feature == null || subMenu ==null) return false;

        String newMenuOrder = feature.menuOrder().replace(feature.menuOrder()+".", "");
        for (Menu menu: subMenu) {
            if(menu.getFeature().menuOrder().startsWith(feature.menuOrder())){
                if(menu.addMenu(feature, newMenuOrder)){
                    return true;
                }
            }
        }
        return false;
    }
}
