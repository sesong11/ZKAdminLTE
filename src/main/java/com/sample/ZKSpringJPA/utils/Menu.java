package com.sample.ZKSpringJPA.utils;

import com.sample.ZKSpringJPA.anotation.Feature;
import com.sample.ZKSpringJPA.entity.authentication.Role;
import com.sample.ZKSpringJPA.entity.authentication.RolePermission;
import com.sample.ZKSpringJPA.entity.authentication.User;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.Executions;

public class Menu {

    @Getter @Setter
    private Feature feature;

    @Getter @Setter
    private boolean isActive;

    @Getter @Setter
    private String displayName;

    @Getter @Setter
    private String menuIcon;

    public Menu(){}

    public Menu(Feature feature){
        this.feature = feature;
        this.displayName = feature.displayName();
        this.menuIcon = feature.menuIcon();

    }

    public String getHtml(){
        boolean hasSubMenu = (subMenu!=null&&subMenu.size()>0);
        String sclass= (hasSubMenu)?"treeview":"";
        sclass += isActive?" active":"";
        String str = "<li class='"+sclass+"'>";
        str += "<a href='?m="+feature.uuid()+"'>";
        str += "<i class='fa fa-"+menuIcon+"'></i><span>"+displayName+"</span>";
        str+="<span class='pull-right-container'>";
        if(hasSubMenu)str+="<i class='fa fa-angle-left pull-right'></i>";
        str+="</span>";
        str+="</a>";
        if(subMenu!=null && subMenu.size()>0){
            str += "<ul class='treeview-menu'>";
            for(Menu menu: subMenu){
                str += menu.getHtml();
            }
            str += "</ul>";
        }
        str += "</li>";
        return str;
    }

    @Getter
    private List<Menu> subMenu;

    public boolean addMenu(final Feature feature, String menuOrder, final String activeOrder) {
        String[] orders = menuOrder.split("\\.");
        if (orders.length == 1) {
            Menu menu = new Menu(feature);
            if (subMenu == null) {
                subMenu = new ArrayList<>();
            }
            subMenu.add(menu);
            if(activeOrder.startsWith(menu.feature.menuOrder())){
                menu.isActive = true;
            }
            return true;
        }

        if(subMenu ==null) return false;

        for (Menu menu: subMenu) {
            String parentMenu = menu.feature.menuOrder()+"\\.";
            String newMenuOrder = feature.menuOrder().replaceFirst(parentMenu, "");
            if(feature.menuOrder().startsWith(menu.feature.menuOrder())){
                if(menu.addMenu(feature, newMenuOrder, activeOrder)){
                    return true;
                }
            }
        }
        return false;
    }

    public Feature scanMenu(final User user) throws ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        Feature tempFeature = null;
        String activeOrder = "";

        Map<String, Feature> featureMap = FeaturesScanner.getFeatures();
        String param = Executions.getCurrent().getParameter("m");
        if (param != null){
            Feature feature = featureMap.get(param);
            if(feature != null) {
                tempFeature = feature;
                activeOrder = feature.menuOrder();
            }
        }
        List<Feature> list = new ArrayList<>(featureMap.values());
        int i = 0;
        do {
            list = addMenu(list, activeOrder, user);
            i++;
        }while (list.size()>0&&i<3);

        return tempFeature;
    }

    private boolean isAuthenticated(final String feature, final User user) throws ClassNotFoundException{
        if(isAdmin()) return true;
        try{
            for(Role role:user.getRoles()){
                for(RolePermission rolePermission: role.getPermissions()){
                    if(rolePermission.getFeature().equals(feature)){
                        return true;
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for(GrantedAuthority authority:authentication.getAuthorities()){
            if(authority.getAuthority().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

    private List<Feature> addMenu(final List<Feature> list, final String activeOrder, final User user)
            throws ClassNotFoundException {
        List<Feature> failure = new ArrayList<>();
        for (Feature f : list) {
            if(!isAuthenticated(f.uuid(), user)){
                continue;
            }
            if (!this.addMenu(f, f.menuOrder(), activeOrder)) {
                failure.add(f);
            }
        }
        return failure;
    }
}