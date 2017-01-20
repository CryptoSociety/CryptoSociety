package com.codeup.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {

    public static boolean isLoggedIn(){
        boolean isAnonymousUser = SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
        return !isAnonymousUser;
    }

    public static User loggedInUser(){
        if(!isLoggedIn()){
            return null;
        } else {
           return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
    }
}
