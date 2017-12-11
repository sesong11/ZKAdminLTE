package com.sample.ZKSpringJPA.utils;

import com.sample.ZKSpringJPA.anotation.Feature;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zsoup.helper.StringUtil;

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
        System.out.println("menu order: "+menuOrder);
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
            System.out.println(menu.feature.menuOrder()+":"+newMenuOrder);
            if(feature.menuOrder().startsWith(menu.feature.menuOrder())){
                if(menu.addMenu(feature, newMenuOrder, activeOrder)){
                    return true;
                }
            }
        }
        return false;
    }

    public String scanMenu() throws ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        String urlParam = "";

        scanner.addIncludeFilter(new AnnotationTypeFilter(Feature.class));

        Map<String, Feature> menus = new TreeMap<>();
        String activeOrder = "";
        for (BeanDefinition bd : scanner.findCandidateComponents("com.sample.ZKSpringJPA.viewmodel")){
            String className = bd.getBeanClassName();
            System.out.println("className: "+className);
            Feature[] features = Class.forName(className).getAnnotationsByType(Feature.class);
            for (Feature feature: features) {
                menus.put(feature.menuOrder(), feature);
                String param = Executions.getCurrent().getParameter("m");
                if (param != null && param.toLowerCase().equals(feature.uuid())) {
                    urlParam = feature.view();
                    activeOrder = feature.menuOrder();
                }
            }
        }
        List<Feature> list = new ArrayList<>(menus.values());
        for(Feature feature: list){
            this.addMenu(feature, feature.menuOrder(), activeOrder);
        }
        if(StringUtil.isBlank(activeOrder)){
            urlParam = "/view/error/404.zul";
        }
        return urlParam;
    }

    public static Map<String, Feature> scanFeatures() throws ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        String urlParam = "";

        scanner.addIncludeFilter(new AnnotationTypeFilter(Feature.class));

        Map<String, Feature> menus = new TreeMap<>();

        for (BeanDefinition bd : scanner.findCandidateComponents("com.sample.ZKSpringJPA.viewmodel")){
            String className = bd.getBeanClassName();
            System.out.println("className: "+className);
            Feature[] features = Class.forName(className).getAnnotationsByType(Feature.class);
            for (Feature feature: features) {
                menus.put(feature.menuOrder(), feature);
            }
        }

        return menus;
    }
}
