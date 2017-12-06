package com.sample.ZKSpringJPA.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sample.ZKSpringJPA.entity.User;

public interface UserService {
	List<User> allUser();
	User getUserByUsername(String username);
	User addUser(User user);
//	{
//        Map<String, Object> userMap = null;
//        //logic here to get your user from the database
//        if (username.equals("admin") || username.equals("user")) {
//            userMap = new HashMap<>();
//            userMap.put("username", "admin");
//            userMap.put("password", "password");
//            //if username is admin, role will be admin, else role is user only
//            userMap.put("role", (username.equals("admin")) ? "admin" : "user");
//            //return the usermap
//            return userMap;
//        }
//        //if username is not equal to admin, return null
//        return null;
//    }
}
