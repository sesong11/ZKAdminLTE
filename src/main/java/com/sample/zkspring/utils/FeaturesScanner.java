package com.sample.zkspring.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.String;
import java.util.TreeMap;

import com.sample.zkspring.anotation.Feature;
import com.sample.zkspring.entity.authentication.Role;
import com.sample.zkspring.entity.authentication.RolePermission;
import com.sample.zkspring.entity.authentication.User;
import com.sample.zkspring.services.authentication.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
public class FeaturesScanner {

    @Autowired
    private UserService userService;

    @Getter
    private static final Map<String, Feature> features = new TreeMap<>();

    public void scanFeatures(final User user) throws ClassNotFoundException {
        FeaturesScanner.getFeatures().clear();
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(Feature.class));

        List<Role> roles = new ArrayList<>(userService.queryRoles(user));

        for (BeanDefinition bd : scanner.findCandidateComponents("com.sample.zkspring.viewmodel")){
            String className = bd.getBeanClassName();
            Feature[] fs = Class.forName(className).getAnnotationsByType(Feature.class);
            for (Feature feature: fs) {
                for(Role role: roles){
                    List<RolePermission> permissions = new ArrayList<>(role.getPermissions());
                    for(RolePermission permission: permissions){
                        if(permission.getFeature().equals(feature.uuid()))
                            features.put(feature.uuid(), feature);
                    }
                }
            }
        }
        System.out.println("Feature count: "+features.size());
    }

    public static void scanFeatures() throws ClassNotFoundException {
        FeaturesScanner.getFeatures().clear();
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(Feature.class));

        for (BeanDefinition bd : scanner.findCandidateComponents("com.sample.zkspring.viewmodel")){
            String className = bd.getBeanClassName();
            Feature[] fs = Class.forName(className).getAnnotationsByType(Feature.class);
            for (Feature feature: fs) {
                features.put(feature.uuid(), feature);
            }
        }
    }
}
